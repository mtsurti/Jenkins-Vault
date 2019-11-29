#!/usr/local/bin/groovy

pipeline {
  agent any
  
  stages {
    stage('Build') {
      steps {
        echo 'Building...'
        getEnv()
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
      }
    }
  }
}

def getBuildInfo() {
  echo "Jenkins Job name is ${env.JOB_NAME} and Build # is ${env.BUILD_NUMBER}"
}
def getDir() {
  echo "The working directory is ${pwd}"
}
def getEnv() {
  echo "${env}"
}
