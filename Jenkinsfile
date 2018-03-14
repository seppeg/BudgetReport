pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean testClasses'
                sh './gradlew jar'
            }
        }

        stage('Test') {
            parallel {
                stage('Test camis-connection') {
                    steps {
                        sh './gradlew :camis-connection:test'
                    }
                }
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/test/*.xml'
        }
    }
}