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
        
        // Timeout configuration
        DOCKER_LOGIN_TIMEOUT = '300'  // 5 minutes
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

                    echo '💾 Archivage du JAR généré...'
                    archiveArtifacts artifacts: 'target/*.jar',
                                     fingerprint: true,
                                     allowEmptyArchive: false
                    echo '📊 JAR archivé avec succès'
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
                        echo '🐳 Construction de l\'image Docker...'
                        sh """
                            docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} .
                            docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${DOCKER_IMAGE_NAME}:latest
                        """
                        echo '✅ Image Docker construite avec succès'

                        // Push sur Docker Hub avec retry
                        echo '📤 Push de l\'image sur Docker Hub...'
                        withCredentials([usernamePassword(
                            credentialsId: "${DOCKER_HUB_CREDENTIALS}",
                            usernameVariable: 'DOCKER_USER',
                            passwordVariable: 'DOCKER_PASS'
                        )]) {
                            retry(3) {
                                timeout(time: 10, unit: 'MINUTES') {
                                    sh """
                                        echo "Tentative de connexion à Docker Hub..."
                                        echo \${DOCKER_PASS} | docker login -u \${DOCKER_USER} --password-stdin
                                        
                                        echo "Push de l'image avec tag ${DOCKER_IMAGE_TAG}..."
                                        docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
                                        
                                        echo "Push de l'image avec tag latest..."
                                        docker push ${DOCKER_IMAGE_NAME}:latest
                                        
                                        docker logout
                                        echo "✅ Push terminé avec succès"
                                    """
                                }
                            }
                        }
                    }
                }
                echo '✅ Image Docker construite et poussée sur Docker Hub avec succès'
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

        stage('7. Vérification & Notification') {
            steps {
                echo '🔍 Vérification du déploiement...'
                script {
                    // Vérification du déploiement
                    sh """
                        echo '=== PODS ==='
                        kubectl get pods -n devops

                        echo '=== SERVICES ==='
                        kubectl get svc -n devops

                        echo '=== URL ACCES ==='
                        KUBE_IP=\$(kubectl get nodes -o jsonpath='{.items[0].status.addresses[?(@.type=="InternalIP")].address}')
                        echo "Application accessible sur: http://\${KUBE_IP}:30080"
                    """

                    echo '✅ Vérification terminée'

                    // Récupérer les informations de déploiement
                    def deploymentStatus = sh(
                        script: 'kubectl get pods -n devops | grep -c Running || echo "0"',
                        returnStdout: true
                    ).trim()

                    def kubeIP = sh(
                        script: 'kubectl get nodes -o jsonpath=\'{.items[0].status.addresses[?(@.type=="InternalIP")].address}\'',
                        returnStdout: true
                    ).trim()

                    // Tentative d'envoi de l'email de succès (non bloquant)
                    try {
                        echo '📧 Tentative d\'envoi de l\'email de notification...'
                        timeout(time: 2, unit: 'MINUTES') {
                            emailext(
                                subject: "✅ Déploiement Réussi - ${JOB_NAME} #${BUILD_NUMBER}",
                                body: """
                                    <html>
                                    <body style="font-family: Arial, sans-serif;">
                                        <h2 style="color: #28a745;">🎉 Déploiement Kubernetes Réussi!</h2>

                                        <h3>📋 Détails du Build:</h3>
                                        <ul>
                                            <li><strong>Projet:</strong> ${JOB_NAME}</li>
                                            <li><strong>Build Number:</strong> #${BUILD_NUMBER}</li>
                                            <li><strong>Image Docker:</strong> ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}</li>
                                            <li><strong>Statut:</strong> <span style="color: #28a745; font-weight: bold;">SUCCESS ✅</span></li>
                                        </ul>

                                        <h3>☸️ Déploiement Kubernetes:</h3>
                                        <ul>
                                            <li><strong>Namespace:</strong> devops</li>
                                            <li><strong>Pods en cours d'exécution:</strong> ${deploymentStatus}</li>
                                            <li><strong>URL d'accès:</strong> <a href="http://${kubeIP}:30080">http://${kubeIP}:30080</a></li>
                                        </ul>

                                        <h3>🔍 Analyse SonarQube:</h3>
                                        <p><a href="${SONAR_HOST_URL}/dashboard?id=timesheet-devops-mariem">📊 Voir les résultats SonarQube</a></p>

                                        <h3>🐳 Image Docker Hub:</h3>
                                        <p><a href="https://hub.docker.com/r/${DOCKER_IMAGE_NAME}">🔗 Voir sur Docker Hub</a></p>

                                        <hr style="border: 1px solid #e9ecef;">
                                        <p style="color: #6c757d; font-size: 12px;">
                                            ⏰ Build terminé à: ${new Date()}<br>
                                            📝 <a href="${BUILD_URL}">Voir les logs complets</a>
                                        </p>
                                    </body>
                                    </html>
                                """,
                                to: 'pgtxsi@gmail.com',
                                mimeType: 'text/html'
                            )
                        }
                        echo '✅ Email de notification envoyé avec succès!'
                    } catch (Exception e) {
                        echo "⚠️ Impossible d'envoyer l'email: ${e.message}"
                        echo "Le déploiement a réussi malgré l'échec de l'email"
                    }
                }
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
            script {
                try {
                    timeout(time: 2, unit: 'MINUTES') {
                        emailext(
                            subject: "❌ Échec du Déploiement - Build #${BUILD_NUMBER}",
                            body: """
                                <html>
                                <body style="font-family: Arial, sans-serif;">
                                    <h2 style="color: #dc3545;">❌ Échec du Déploiement!</h2>

                                    <h3>Détails du Build:</h3>
                                    <ul>
                                        <li><strong>Projet:</strong> ${JOB_NAME}</li>
                                        <li><strong>Build Number:</strong> #${BUILD_NUMBER}</li>
                                        <li><strong>Statut:</strong> <span style="color: #dc3545;">FAILURE</span></li>
                                    </ul>

                                    <p><strong>Action requise:</strong> Veuillez vérifier les logs pour identifier le problème.</p>

                                    <hr>
                                    <p style="color: #6c757d; font-size: 12px;">
                                        Build échoué à: ${new Date()}<br>
                                        <a href="${BUILD_URL}console">Voir les logs complets</a>
                                    </p>
                                </body>
                                </html>
                            """,
                            to: 'pgtxsi@gmail.com',
                            mimeType: 'text/html'
                        )
                    }
                } catch (Exception e) {
                    echo "⚠️ Impossible d'envoyer l'email d'échec: ${e.message}"
                }
            }
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
