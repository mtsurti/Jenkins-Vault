#! /usr/local/bin/groovy

@NonCPS
def updateAllConfigs(String token) {
      def hudson = hudson.model.Hudson.instance;
      PrintWriter output 
      def prefix
      for (aJob in Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)){ //org.jenkinsci.plugins.workflow.job.WorkflowJob)*.fullName)
            println aJob.fullName
      }
      
      //for (thisJob in hudson.model.Hudson.instance.items) {   
      for (thisJob in Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)){
          prefix = thisJob.fullName.takeWhile { it != '-' }
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
              output.close()
              println "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/" + thisJob.fullName            
              sh "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/" + thisJob.fullName          
              //thisJob.updateByXml(new InputStream(pwd()+"/config.xml"))
              thisJob.save()
              thisJob.doReload() 
                       
           }
        }
      }                
return this
