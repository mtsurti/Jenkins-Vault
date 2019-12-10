#! /usr/local/bin/groovy
import javax.xml.transform.stream.*
      
@NonCPS
def updateAllConfigs(String token) {
      def hudson = hudson.model.Hudson.instance;
      PrintWriter output 
      InputStream stream 
      String jobname
      def prefix
      def allJobs = Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)

      //for (thisJob in hudson.model.Hudson.instance.items) {   
      //for (thisJob in Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)){
      allJobs.each { thisJob ->
          println thisJob.fullName
          prefix = thisJob.fullName.takeWhile { it != '-' }
          println prefix
          if (prefix.toLowerCase().contains("iaas")) {
              output = new PrintWriter(pwd()+'/config.xml', 'utf-8')
              
              def configXMLFile = thisJob.getConfigFile()
              def file = configXMLFile.getFile()
              
              file.eachLine { line ->
                    if (line.trim().contains("authToken")) {
                       output.println "  <authToken>" + token + "</authToken>"  
                    }      
                    if (!line.trim().contains("authToken")) {
                        output.println line
                    }
              }
              println "About to reload " + thisJob.fullName
              output.close()
              thisJob.updateByXml(new FileInputStream(new File(pwd()+'/config.xml')))
              thisJob.save()
              thisJob.doReload()  
              println "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/" + thisJob.fullName            
              sh "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/" + thisJob.fullName          
              //thisJob.updateByXml(new FileInputStream(new File(pwd()+'/config.xml')))
                   
           }
        }
      }                
return this
