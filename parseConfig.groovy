#! /usr/local/bin/groovy

@NonCPS
def updateAllConfigs(String token) {
      def hudson = hudson.model.Hudson.instance;
      PrintWriter output 
      String jobname
      def prefix
      def allJobs = Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)
      /*for (aJob in Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)){ //org.jenkinsci.plugins.workflow.job.WorkflowJob)*.fullName)
            println aJob.fullName
      }*/
      //for (thisJob in hudson.model.Hudson.instance.items) {   
      //for (thisJob in Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)){
      allJobs.each { thisJob ->
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
              
              println "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/" + thisJob.fullName            
              sh "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/" + thisJob.fullName          
              //thisJob.updateByXml(new InputStream(pwd()+"/config.xml"))
              thisJob.save()
              thisJob.doReload()        
           }
        output.close()
        }
      }                
return this
