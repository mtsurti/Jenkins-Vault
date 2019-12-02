#!/usr/local/bin/groovy

//environment {
  //      SECRET = vault path: 'secrets', key: 'username', vaultUrl: 'https://my-vault.com:8200', credentialsId: 'my-creds', engineVersion: "2"
//}
//pipeline {
//  agent any
//    stages {
      stage('Build') {
        //steps {
          echo 'Building...'
          getAllEnv()
        //}
      }
      stage('Test') {
        //steps {
          echo 'Testing...'
          //getDir()
        //}
      }
      stage('Rotate') {
        //steps {
          echo 'Rotating Token...'
          rotateToken()
        //}
      }
      stage('Deploy') {
        //steps {  
          echo 'Deploying...'
          getBuildInfo()
          restartJenkins()
        //}
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

def rotateToken() {
    // define the secrets and the env variables
    // engine version can be defined on secret, job, folder or global.
    // the default is engine version 2 unless otherwise specified globally.
    def secrets = [
        [path: 'secret/testing', engineVersion: 1, secretValues: [
            [envVar: 'testing', vaultKey: 'value_one'],
            [envVar: 'testing_again', vaultKey: 'value_two']]],
        [path: 'secret/another_test', engineVersion: 2, secretValues: [
            [vaultKey: 'another_test']]]
    ]

    // optional configuration, if you do not provide this the next higher configuration
    // (e.g. folder or global) will be used
    def configuration = [vaultUrl: 'http://localhost:8200.com',
                            vaultCredentialId: 's.tr4LpYkNnKlWcLiT4xN5v7pk',
                            engineVersion: 2]
    // inside this block your credentials will be available as env variables
    withVault([configuration: configuration, vaultSecrets: secrets]) {
        sh 'echo $testing'
        sh 'echo $testing_again'
        sh 'echo $another_test'
    }
}   

