pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/mariem49/timesheet_devops_mariem.git'
        GIT_BRANCH = 'main'
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        PROJECT_DIR = 'TP-Projet-2025'
        MAVEN_HOME = '/usr/share/maven'

        // Configuration Docker Hub
        DOCKER_HUB_CREDENTIALS = 'dockerhub-credentials'
        DOCKER_IMAGE_NAME = 'mariem49/timesheet-app'
        DOCKER_IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('1. Récupération du code depuis Git') {
            steps {
                echo 'Clonage du repository Git...'
                git branch: "${GIT_BRANCH}",
                    credentialsId: 'github-credentials',
                    url: "${GIT_REPO}"
                echo 'Code récupéré avec succès'
            }
        }

        stage('2. Nettoyage et Compilation') {
            steps {
                echo 'Nettoyage et compilation du projet Maven...'
                dir("${PROJECT_DIR}") {
                    sh 'mvn clean compile -DskipTests'
                }
                echo 'Nettoyage et compilation terminés'
            }
        }

        stage('3. Analyse SonarQube') {
            steps {
                echo 'Analyse de la qualité du code avec SonarQube...'
                dir("${PROJECT_DIR}") {
                    withSonarQubeEnv('SonarQube-Server') {
                        withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                            sh """
                                mvn sonar:sonar \
                                  -Dsonar.projectKey=timesheet-devops-mariem \
                                  -Dsonar.projectName='Timesheet DevOps Mariem' \
                                  -Dsonar.host.url=${SONAR_HOST_URL} \
                                  -Dsonar.token=\${SONAR_TOKEN}
                            """
                        }
                    }
                }
                echo 'Analyse SonarQube terminée'
                echo "Consultez les résultats sur: ${SONAR_HOST_URL}/dashboard?id=timesheet-devops-mariem"
            }
        }

        stage('4. Génération du fichier JAR') {
            steps {
                echo 'Génération du fichier JAR...'
                dir("${PROJECT_DIR}") {
                    sh 'mvn package -DskipTests'
                }
                echo 'Fichier JAR généré avec succès'
            }
        }

        stage('5. Docker Build & Push') {
            steps {
                echo 'Construction et push de l\'image Docker...'
                dir("${PROJECT_DIR}") {
                    script {
                        // Construction de l'image
                        sh """
                            docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} .
                            docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${DOCKER_IMAGE_NAME}:latest
                        """

                        // Push sur Docker Hub
                        withCredentials([usernamePassword(
                            credentialsId: "${DOCKER_HUB_CREDENTIALS}",
                            usernameVariable: 'DOCKER_USER',
                            passwordVariable: 'DOCKER_PASS'
                        )]) {
                            sh """
                                echo \${DOCKER_PASS} | docker login -u \${DOCKER_USER} --password-stdin
                                docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
                                docker push ${DOCKER_IMAGE_NAME}:latest
                                docker logout
                            """
                        }
                    }
                }
                echo 'Image Docker construite et poussée sur Docker Hub avec succès'
            }
        }

        stage('6. Deploy MySQL & Spring Boot on K8s') {
            steps {
                echo '📦 Déploiement MySQL & Spring Boot sur Kubernetes...'
                script {
                    // Déploiement MySQL
                    echo '🗄️ Déploiement de MySQL...'
                    sh """
                        kubectl apply -f k8s/mysql-deployment.yaml
                        echo 'Attente du démarrage de MySQL...'
                        kubectl wait --for=condition=ready pod -l app=mysql -n devops --timeout=600s
                    """
                    echo '✅ MySQL déployé avec succès'

                    // Déploiement Spring Boot
                    echo '🚀 Déploiement de Spring Boot...'
                    sh """
                        kubectl apply -f k8s/spring-deployment.yaml
                        kubectl set image deployment/spring-app spring-app=${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} -n devops
                        kubectl rollout status deployment/spring-app -n devops --timeout=300s
                    """
                    echo '✅ Spring Boot déployé avec succès'
                }
                echo '🎉 Déploiement Kubernetes terminé avec succès'
            }
        }

        stage('7. Vérification du déploiement') {
            steps {
                echo 'Vérification du déploiement...'
                script {
                    sh """
                        echo '=== PODS ==='
                        kubectl get pods -n devops

                        echo '=== SERVICES ==='
                        kubectl get svc -n devops

                        echo '=== URL ACCES ==='
                        echo 'Application accessible sur: http://\$(minikube ip):30080'
                    """
                }
                echo 'Vérification terminée'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline exécuté avec succès !'
            echo "Image Docker: ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
            echo "Application déployée sur Kubernetes dans le namespace 'devops'"
        }
        failure {
            echo '❌ Le pipeline a échoué. Vérifiez les logs ci-dessus.'
        }
        always {
            echo 'Nettoyage des images Docker locales...'
            sh """
                docker rmi ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} || true
                docker rmi ${DOCKER_IMAGE_NAME}:latest || true
            """
        }
    }
}