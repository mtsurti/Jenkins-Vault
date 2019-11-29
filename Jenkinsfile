#!/usr/local/bin/groovy

def workspace = ""
def externalMethod = ""

pipeline {
  agent any
  
  stages {
    stage('Build') {
      steps {
        sh 'echo Building...'
        sh 'echo ${BRANCH_NAME} ${env.BRANCH_NAME}'
      }
    }
    stage('Test') {
      steps {
        sh 'echo Testing...'
        workspace = pwd() 
        sh 'echo $workspace'
      }
    }
    stage('Deploy') {
      steps {  
        sh 'echo Deploying...'
        externalMethod = load 'file1.groovy' // Call the method we defined in file1. 
        externalMethod.sayHello()
      }
    }
  }
}
