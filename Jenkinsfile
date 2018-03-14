pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                env.JAVA_HOME="${tool 'JDK9'}"
                env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
                sh 'java -version'
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