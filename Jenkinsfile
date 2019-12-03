#!/usr/local/bin/groovy

import hudson.model.*
import jenkins.model.*
import jenkins.security.*
import jenkins.security.apitoken.*

//environment {
  //      SECRET = vault path: 'secrets', key: 'username', vaultUrl: 'https://my-vault.com:8200', credentialsId: 'my-creds', engineVersion: "2"
//}
//pipeline {
//  agent any
//    stages {
      
      node {
        def tokenFile 
        try {
          stage('Checkout') {
            //steps {
              echo 'Checking out scm...'
              checkout scm
              //getAllEnv()
            //}
          }
          stage('Load') {
            //steps {
            echo 'Loading from external token file...'  
              
            //getDir()
            //}
          }
          stage('Generate') {
            //node {
              //def newToken = generateNewToken()
              echo 'Generating new token file...'
              tokenFile = load pwd() + '/newtoken.groovy'  
              tokenFile.shuffleToken()
              //generateNewToken()
              //sh "echo New Token is $newToken"
           // }
          }
          stage('Deploy to Vault server') {
            //steps {  
              echo 'Deploying to Vault Server...'
              updateVaultToken()  
            //}
          }
          stage('Update config.xml'){
            echo 'Updating config.xml file with new token...'
            updateConfig()
          }
          stage('Restart Jenkins'){
            echo 'Retarting Jenkins...'
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
    env.each
        println it 
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
    }
  }
}   
