#!/usr/local/bin/groovy
import java.io.InputStream;
import java.io.FileInputStream
import java.io.File;
import javax.xml.transform.stream.StreamSource

      node {     
        def tokenGenerator
        def newToken
        
        try {
          stage('Checkout') {
              echo 'Checking out scm...'
                checkout([
                      $class: 'GitSCM', 
                      branches: [[name: '*/master']], 
                      doGenerateSubmoduleConfigurations: false,
                      extensions: [],
                      userRemoteConfigs: [[
                            credentialsId: 'd1ea6eb0-c66a-4926-817d-597635de0af7',
                            url: 'https://github.com/mtsurti/Jenkins-Vault.git']]])              
              workspace = pwd() 
          }
          stage('Load') {
            echo 'Loading from external token file...'  
            tokenGenerator = load pwd() + '/newtoken.groovy'  
          }
          stage('Generate') {
              echo 'Generating new token file...'
              newToken = tokenGenerator.shuffleToken()
              println newToken
              println workspace
          }
          stage('Update Token') {
            /*echo 'Updating token...'
            sh "rm " + pwd() + "/current.token"
            sh "echo " + newToken + "> " + pwd() + "/current.token"*/
          }
          stage('Update SCM') {
            /*echo 'Updating repo with new token...'
            sh "git add " + pwd() + "/current.token"
            sh "git commit -am \'Updated token!\'"
            sh "git push origin HEAD:master" */
          }
          stage('Update config.xml'){
            echo 'Updating config.xml file with new token...'
            //updateConfig()
          }
          stage('Reload config'){
                reloadConfig()
          }      
          stage('Restart Jenkins'){
            echo 'Restarting Jenkins...'
            restartJenkins()
          }
          stage('Deploy to Vault server') {
              echo 'Deploying to Vault Server...'
              //updateVaultToken()  
          }
        } 
        catch (e) {
            throw e
        }
      }
    def restartJenkins() {
        //sh 'sudo launchctl unload /Library/LaunchDaemons/org.jenkins-ci.plist'
        //sh 'sudo launchctl load /Library/LaunchDaemons/org.jenkins-ci.plist'
      sh 'wget http://localhost:8080/restart'
    }
    def updateConfig() {
        configure {
        // "it" is a groovy.util.Node
        //    representing the job's config.xml's root "project" element.
        // anotherNode is also groovy.util.Node
        //    obtained with the overloaded "/" operator
        //    on which we can call "setValue(...)"
        def aNode = it
        def anotherNode = aNode / 'blockBuildWhenDownstreamBuilding'
        anotherNode.setValue('true')

        // You can chain these steps,
        //    but must add wrapping parenthesis
        //    because the "/" has a very low precedence (lower than the ".")
        (it / 'blockBuildWhenUpstreamBuilding').setValue('true')
        }
    }
    def rotateToken() {
        // define the secrets and the env variables
        // engine version can be defined on secret, job, folder or global.
        // the default is engine version 2 unless otherwise specified globally.
        def secrets = [
            [path: 'secret/testing', engineVersion: 2, secretValues: [
                [envVar: 'testing', vaultKey: 'value_one'],
                [envVar: 'testing_again', vaultKey: 'value_two']]],
            [path: 'secret/another_test', engineVersion: 2, secretValues: [
                [vaultKey: 'another_test']]]
        ]

        // optional configuration, if you do not provide this the next higher configuration
        // (e.g. folder or global) will be used
        def configuration = [vaultUrl: 'http://localhost:8200',
                                vaultCredentialId: 'VAULT_TOKEN',
                                engineVersion: 2,
                                timeout: 60]
        // inside this block your credentials will be available as env variables
        withVault([configuration: configuration, vaultSecrets: secrets]) {
            sh 'echo $testing'
            sh 'echo $testing_again'
            sh 'echo $another_test'
            sh 'echo TOKEN=$VAULT_TOKEN'
            sh 'echo ADDR=$VAULT_ADDR'
        }
    //withCredentials([[$class: 'VaultTokenCredentialBinding', addrVariable: 'VAULT_ADDR', credentialsId: 'VAULT_TOKEN', vaultAddr: 'http://localhost:8200']]) {
            // values will be masked
        //   sh 'echo TOKEN=$VAULT_TOKEN'
        //   sh 'echo ADDR=$VAULT_ADDR'
    // }
    }
  def updateVaultToken() {
    withCredentials([string(credentialsId: 'role', variable: 'ROLE_ID'),string(credentialsId: 'VAULTTOKEN', variable: 'VAULT_TOKEN')]) {
        sh '''
          set +x
          //export VAULT_ADDR=https://$(hostname):8200
          export VAULT_ADDR=https://localhost:8200
          export VAULT_SKIP_VERIFY=true
          export SECRET_ID=$(./vault write -field=secret_id -f auth/approle/role/vault-token-rotation/secret-id)
          export VAULT_TOKEN=$(./vault write -field=token auth/approle/login role_id=${ROLE_ID} secret_id=${SECRET_ID})
        '''   
    }
  }     
  def reloadConfig() {
      
      def hudson = hudson.model.Hudson.instance;

      //to get a single job
      //def job = hudson.model.Hudson.instance.getItem('my-job');

      for(job in hudson.model.Hudson.instance.items) {   
          println job.name
          /*if (job.name == "my-job") {

              def configXMLFile = job.getConfigFile();
              def file = configXMLFile.getFile();

              InputStream is = new FileInputStream(file);

              job.updateByXml(new StreamSource(is));
              job.save();         
          }    */  
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
  }      
