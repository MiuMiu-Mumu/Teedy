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
                // 只生成报告，不重复运行测试
                sh 'mvn surefire-report:report-only -pl docs-core'
                // 调试：查看实际生成了哪些文件
                sh 'ls -R docs-core/target/ || true'
            }
            post {
                always {
                    publishHTML([
                        allowMissing: true,          // 改为 true，目录不存在也不失败
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'docs-core/target/site',
                        reportFiles: 'surefire-report.html',
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
            archiveArtifacts artifacts: '**/target/site/*.html', fingerprint: true
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