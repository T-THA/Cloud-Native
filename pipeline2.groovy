pipeline {
    agent none
    stages {
        stage('Clone Code') {
            agent {
                label 'slave'
            }
            steps {
                echo "1.Clone From Gitee"
                //xxx needs to be replaced
                sh 'curl "http://p.nju.edu.cn/portal_io/login?' +
                        'username=' + '211250234' +
                        '&' +
                        'password=' + 'xxxxxxxx' + '"'
                git url: 'https://gitee.com/irisalt/cloud-native.git', branch: 'main'
            }
        }
    }
}

node('slave') {
    container('jnlp-kubectl') {
        stage('Clone & Change YAML') {
            echo "2. Clone YAML to Slave and Change YAML"
            //xxx needs to be replaced
            sh 'curl "http://p.nju.edu.cn/portal_io/login?' +
                    'username=' + '211250234' +
                    '&' +
                    'password=' + 'xxxxxxxxxxx' + '"'
            git url: 'https://gitee.com/irisalt/cloud-native.git', branch: 'main'
        }

        stage ('Deploy') {
            echo "3. Deploy to K8s"
            //xxx needs to be replaced
            sh 'curl "http://p.nju.edu.cn/portal_io/login?' +
                    'username=' + '211250234' +
                    '&' +
                    'password=' + 'xxxxxxxxxx' + '"'
            sh 'docker login harbor.edu.cn ' +
                    '-u ' + 'nju17' +
                    ' -p ' + 'nju172023'
            sh 'docker pull harbor.edu.cn/nju17/cloud-native:9'
            // sh 'kubectl delete deployment cloud-native -n nju17'
            sh 'kubectl apply -f deployment.yaml -n nju17'
        }

        stage('Monitor') {
            echo "4. Start Monitor"
            sh 'kubectl apply -f monitor.yaml -n monitoring'
        }
    }
}