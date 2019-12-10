#! /usr/local/bin/groovy

@NonCPS
def updateAllConfigs(String token) {
      def hudson = hudson.model.Hudson.instance;
      PrintWriter output = new PrintWriter(pwd()+'/config.xml', 'utf-8')
      def prefix
      for (thisJob in hudson.model.Hudson.instance.items) {   
          prefix = thisJob.name.takeWhile { it != '-' }
          if (prefix.toLowerCase().contains("iaas")) {
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
              println "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/" + thisJob.name.toString()            
              sh "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/" + thisJob.name.toString()          
              //thisJob.updateByXml(new InputStream(pwd()+"/config.xml"))
              thisJob.save()
              thisJob.doReload() 
                       
           }
        }
      }                
return this
