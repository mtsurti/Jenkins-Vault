pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        echo 'Building...'
      }
    }
    stages('Test') {
      steps {
        echo 'Testing...'
      }
    }
    stages('Deploy') {
      steps {
        echo 'Deploying...'
      }
    }
  }
}
