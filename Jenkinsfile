node {
    def mvnHome
    stage('Preparation') {
        git 'https://github.com/tomkre/ping-service.git'
        mvnHome = tool 'M2_HOME'
    }
    stage('Build') {
        withEnv(['MVN_HOME=$mvnHome']) {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
            } else {
                bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
            }
        }
    }
    stage('Results') {
        junit( '**/target/surefire-reports/TEST-*.xml')
        archiveArtifacts 'target/*.jar'
    }
    stage('Build image') {
        sh "'${mvnHome}/bin/mvn'  -Ddocker.image.prefix=665971472586.dkr.ecr.us-east-1.amazonaws.com/ostock -Dproject.artifactId=ping-service -Ddocker.image.version=chapter12 dockerfile:build"
    }
    stage('Push image') {
        docker.withRegistry('https://665971472586.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:ecr-user') {
            sh "docker image push 665971472586.dkr.ecr.us-east-1.amazonaws.com/ostock/ping-service:chapter12"
        }
    }
    stage('Kubernetes deploy') {
        kubernetesDeploy(
            configs: 'ping-service-deployment.yaml',
            kubeConfig: [path: ''],
            kubeconfigId: 'kubeconfig',
            secretName: '',
            ssh: [
                sshCredentialsId: '*',
                sshServer: ''
            ],
            textCredentials: [
                certificateAuthorityData: '',
                clientCertificateData: '',
                clientKeyData: '',
                serverUrl: 'https://'
            ]
        )
    }
}