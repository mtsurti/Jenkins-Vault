#!/usr/local/bin/groovy

pipeline {
  agent any
  
  stages {
    stage('Build') {
      steps {
        echo 'Building...'
        echo "${env}"
      }
    }
    stage('Test') {
      steps {
        echo 'Testing...'
        getDir()
      }
    }
    stage('Deploy') {
      steps {  
        echo 'Deploying...'
        getBuildInfo()
        //externalMethod = load 'file1.groovy' // Call the method we defined in file1. 
        //externalMethod.sayHello()
      }
    }
  }
}

def getBuildInfo() {
  def subject = "Jenkins Job name is ${env.JOB_NAME} and Build # is [${env.BUILD_NUMBER}]"
  println subject
}
def getDir() {
  sh 'pwd'
}
