#!/usr/local/bin/groovy

pipeline {
  agent any
  
  stages {
    stage('Build') {
      steps {
        sh 'echo Building...'
      }
    }
    stage('Test') {
      steps {
        sh 'echo Testing...'
        //workspace = pwd() 
        //sh 'echo $workspace'
      }
    }
    stage('Deploy') {
      steps {  
        sh 'echo Deploying...'
        getBuildInfo()
        //externalMethod = load 'file1.groovy' // Call the method we defined in file1. 
        //externalMethod.sayHello()
      }
    }
  }
}

def getBuildInfo() {
  def subject = "${env.JOB_NAME} [${env.BUILD_NUMBER}]"
  println subject
}
