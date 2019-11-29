pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        echo 'Building...'
        echo '${BRANCH_NAME} ${env.BRANCH_NAME}'
      }
    }
    stage('Test') {
      steps {
        echo 'Testing...'
        def workspace = pwd() 
        echo workspace
      }
    }
    stage('Deploy') {
      steps {  
        echo 'Deploying...'
        def externalMethod = load 'file1.groovy' // Call the method we defined in file1. 
        externalMethod.sayHello()
      }
    }
  }
}
