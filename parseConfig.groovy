#! /usr/local/bin/groovy
import javax.xml.transform.stream.*
      
@NonCPS
def updateAllConfigs(String token) {
      def hudson = hudson.model.Hudson.instance;
      PrintWriter output 
      def configXMLFile 
      def file
      String jobname
      def prefix
      def allJobs = Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)

      // iterating through all the jobs
      allJobs.each { thisJob ->
          prefix = thisJob.fullName.takeWhile { it != '-' }
          // filtering by iaas pipelines        
          if (prefix.toLowerCase().contains("iaas")) {
              println thisJob.fullName + " pipeline getting auth token refreshed..."
              output = new PrintWriter(pwd()+'/config.xml', 'utf-8')
              
              configXMLFile = thisJob.getConfigFile()
              file = configXMLFile.getFile()
              
              file.eachLine { line ->
                    if (line.trim().contains("authToken")) {
                       output.println "  <authToken>" + token + "</authToken>"  
                    }      
                    if (!line.trim().contains("authToken")) {
                        output.println line
                    }
              }
              output.close()
              thisJob.updateByXml(new StreamSource(new FileInputStream(new File(pwd()+'/config.xml'))))
              thisJob.save()
              thisJob.doReload()  
           }
        }
      }                
return this
