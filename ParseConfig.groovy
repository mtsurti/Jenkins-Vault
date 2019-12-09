#! /usr/local/bin/groovy
import javax.xml.transform.stream.StreamSource

@NonCPS
def updateAllConfigs(String token) {
      def hudson = hudson.model.Hudson.instance;
      PrintWriter output = new PrintWriter(pwd()+'/config.xml', 'utf-8')
      def prefix
      for (thisJob in hudson.model.Hudson.instance.items) {   
          //def prefix = names.substring(0, names.indexOf('-'))
          prefix = thisJob.name.takeWhile { it != '-' }
          if (prefix.toLowerCase().contains("token")) {
              def configXMLFile = thisJob.getConfigFile()
              def file = configXMLFile.getFile()
              
              file.eachLine { line ->
                    if (line.trim().contains("authToken")) {
                       //output.withWriter('utf-8') { writer ->
                       output.println "  <authToken>" + token + "</authToken>"  
                             //writer.write "  <authToken>" + token + "</authToken>"  
                             println "  <authToken>" + token + "</authToken>"
                        //}
                    }      
                    if (!line.trim().contains("authToken")) {
                        //output.withWriter('utf-8') { writer ->
                        output.println line
                             //writer.write line
                             println line
                        //}
                    }
              }
              output.close()
       
              println "mv " + pwd() + "/config.xml" + " /Users/mohammad/.jenkins/jobs/token-rotator"
              sh "mv " + pwd() + "/config.xml " + " /Users/mohammad/.jenkins/jobs/token-rotator"
              //sh "rm /Users/mohammad/.jenkins/jobs/token-rotator/config.xml"
              //sh "mv /Users/mohammad/.jenkins/jobs/token-rotator/tempconfig.xml /Users/mohammad/.jenkins/jobs/token-rotator/config.xml"
              
              //thisJob.updateByXml(new InputStream(pwd()+"/config.xml"))
              thisJob.save()
              thisJob.doReload() 
                       
           }
        }
      }                
return this
