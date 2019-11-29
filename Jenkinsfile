#!/usr/local/bin/groovy

pipeline {
  agent any
  
  stages {
    stage('Build') {
      steps {
        sh 'echo Building...'
        echo env
      }
    }
    stage('Test') {
      steps {
        sh 'echo Testing...'
        getDir()
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
def getDir() {
  sh 'pwd'
}
