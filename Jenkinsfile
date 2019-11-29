#!/usr/local/bin/groovy

pipeline {
  agent any
  
  try {
    stages {
      stage('Build') {
        steps {
          echo 'Building...'
          getAllEnv()
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
  catch (err) {
      error "Something went wrong"
  }
  finally {
    restartJenkins()
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
  echo 'sh ${env.HUDSON_URL}/softRestart' 
}
