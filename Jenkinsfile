pipeline {
    agent any
    tools{
        maven 'maven-3.8.7'
    }
    stages {
        stage('Build') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/jayasith/authentication-microservice.git']])
                bat 'mvn clean install'
            }
        }
        stage('Build Docker Image') {
            steps {
                script{
                    bat 'docker build -t jayasith98/authentication-microservice .'
                }
            }
        }
        stage('Push Image to Docker Hub') {
            steps {
		        script{
                    withCredentials([string(credentialsId: 'docker-hub-id', variable: 'docker-credentials')]) {
                        bat 'echo %docker-credentials%| docker login -u jayasith98 --password-stdin'
                	}
                	bat 'docker push jayasith98/authentication-microservice'
	        	}
            }
        }
        stage('Push Image to Docker Hub') {
            steps {
		        script{
                    withKubeConfig(caCertificate: '', clusterName: '', contextName: '', credentialsId: 'K8s', namespace: '', restrictKubeConfigAccess: false, serverUrl: '') {
                        bat 'kubectl apply -f ks8/deployment.yml'
                        bat 'kubectl apply -f ks8/service.yml'
                    }

	        	}
            }
        }
    }

}