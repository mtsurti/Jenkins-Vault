pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        echo '${BRANCH_NAME} ${env.BRANCH_NAME}'
      }
    }
    stage('Test') {
        def workspace = pwd()
        echo workspace     
    }
    stage('Deploy') {
        def externalMethod = load("hello.groovy")
         // Call the method we defined in file1.
        externalMethod.sayHello()
    }
  }
}