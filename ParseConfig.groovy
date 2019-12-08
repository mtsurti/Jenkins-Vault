#! /usr/local/bin/groovy

@NonCPS
def updateAllConfigs(String token) {
      def hudson = hudson.model.Hudson.instance;
      //to get a single job
      //def job = hudson.model.Hudson.instance.getItem('my-job');
      def prefix
      for (thisJob in hudson.model.Hudson.instance.items) {   
          //def prefix = names.substring(0, names.indexOf('-'))
          prefix = thisJob.name.takeWhile { it != '-' }
          if (prefix.toLowerCase().contains("token")) {
              def configXMLFile = thisJob.getConfigFile()
              def file = configXMLFile.getFile()
              file.eachLine { line ->
                    line.toLowerCase().contains("authToken") {
                        println line
                    }
              }
              def rootNode = new XmlParser().parseText(file.getText('UTF-8'))
              //def rootNode = new XmlParser().parse(file)
              /*def iterator = rootNode.iterator()
              
              def currentNode
              while (iterator.hasNext()) {   
                currentNode = iterator.next()
                println "The node is " + currentNode.name() + " -> " + currentNode.text()
                if (currentNode.name().toLowerCase().contains("authToken")) {
                      Node newNode = currentNode
                      newNode.setValue(token)
                      def modifiedNode = currentNode.authToken.replaceNode(newNode)
                      println "Updated new token in " + modifiedNode.name() + " with " + modifiedNode.text()
                  }
              }  */
              
              /*def nodeToModify = rootNode.buildWrappers.findAll { n -> 
              if (n."EnvInjectBuildWrapper".info.propertiesContent) {
                  if (n."EnvInjectBuildWrapper".info.propertiesContent.text().toLowerCase().contains(contain_text.toLowerCase())) {
                        println "this is vbs"
                        n."EnvInjectBuildWrapper".info.propertiesContent.value = n."EnvInjectBuildWrapper".info.propertiesContent.text().replace(oldToken, token)
                        println "[INFO] save changes in config.xml"
                        }
                  }    
              } */     
              
              
              /*file.withWriter { w ->
                  w.write(XmlUtil.serialize(rootNode))
                  //w.write(XmlUtil.serialize(currentNode))
              }
              //println "config file is " + file
              InputStream is = new FileInputStream(file)
              thisJob.updateByXml(new StreamSource(is))
              thisJob.save()
              thisJob.doReload() */
              
              /*println "Finally Config.xml file is: "
              file.eachLine { line ->
                  println line
              }*/
              //job(thisJob.name) {
                  /*println "Job is " + thisJob.name
                  configure {
                        // "it" is a groovy.util.Node
                        //    representing the job's config.xml's root "project" element.
                        // anotherNode is also groovy.util.Node
                        //    obtained with the overloaded "/" operator
                        //    on which we can call "setValue(...)"
                        //def aNode = it
                        def anotherNode = it / 'authToken'
                        anotherNode.setValue(token)

                        // You can chain these steps,
                        //    but must add wrapping parenthesis
                        //    because the "/" has a very low precedence (lower than the ".")
                        (it / 'authToken').setValue(token)        
                  }
              //}*/
          }
        }
      }         
      /*
            import jenkins.model.Jenkins;

            def job_path = 'folder1/folder2/job_name'

            Jenkins j = Jenkins.get()

            def job = j.getItemByFullName(job_path)

            if (job) {
              job.doReload()
            }
      */
return this
