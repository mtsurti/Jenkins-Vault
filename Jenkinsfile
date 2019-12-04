#!/usr/local/bin/groovy

      node {
        def tokenGenerator
        def newToken
        def workspace 
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
              //getAllEnv()
              workspace = env.WORKSPACE 
          }
          stage('Load') {
            echo 'Loading from external token file...'  
            //getDir()
          }
          stage('Generate') {
              echo 'Generating new token file...'
              tokenGenerator = load pwd() + '/newtoken.groovy'  
              newToken = tokenGenerator.shuffleToken()
              println newToken
              //generateNewToken()
          }
          stage('Update Token') {
            //sh 'echo $newToken > $workspace/current.token'
            sh 'echo $newToken > $workspace/tmp.token'
            sh 'rm $workspace/current.token && mv $workspace/tmp.token $workspace/current.token'
            //sh 'echo version := 1.0.${env.BUILD_ID} >> build.sbt'
          }
          stage('Update SCM') {
            echo 'Updating repo with new token...'
            sh 'git add $workspace/current.token'
            sh 'git commit -am "Updated token!'
            sh "git push origin master"
          }
          stage('Deploy to Vault server') {
              echo 'Deploying to Vault Server...'
              //updateVaultToken()  
          }
          stage('Update config.xml'){
            echo 'Updating config.xml file with new token...'
            //updateConfig()
          }
          stage('Restart Jenkins'){
            echo 'Retarting Jenkins...'
            restartJenkins()
          }
        } 
        catch (e) {
            throw e
        }
      }
    def getBuildInfo() {
        echo "Jenkins Job name is ${env.JOB_NAME} and Build # is ${env.BUILD_NUMBER}"
    }
    def getDir() {
        echo "The working directory is ${pwd}"
    }
    def readExternalFile() {
        def env = System.getenv()
        env.each {
            println it
        }
    }

    def getAllEnv() {
        def fields = env.getEnvironment()
            fields.each {
                key, value -> println("${key} = ${value}");
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
  /*def updateVaultToken() {
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
  }*/
   
