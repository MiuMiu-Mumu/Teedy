pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/MiuMiu-Mumu/Teedy.git'
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('PMD Code Check') {
            steps {
                sh 'mvn pmd:pmd'
            }
            post {
                always {
                    recordIssues(
                                    tools: [pmdParser(pattern: '**/target/pmd.xml')],
                                    enabledForFailure: true
                                )
                }
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test -Dmaven.test.failure.ignore=true'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Generate Test Report') {
            steps {
                sh 'mvn surefire-report:report-only -pl docs-core'
            }
            post {
                always {
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'docs-core/target/reports',      // 改这里
                        reportFiles: 'surefire.html',               // 改这里（不是 surefire-report.html）
                        reportName: 'Surefire Test Report'
                    ])
                }
            }
        }

        stage('Generate JavaDoc') {
            steps {
                sh 'mvn javadoc:javadoc javadoc:jar -Ddoclint=none'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            archiveArtifacts artifacts: '**/target/reports/*.html', fingerprint: true
            archiveArtifacts artifacts: '**/target/*-javadoc.jar', fingerprint: true
        }
        success {
            echo '构建成功！'
        }
        failure {
            echo '构建失败！'
        }
    }
}