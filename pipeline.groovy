pipeline {
    agent none
    stages {
        stage('Clone Code') {
            agent {
                label 'master'
            }
            steps {
                echo "1.Clone From Gitee"
                //xxx needs to be replaced
                sh 'curl "http://p.nju.edu.cn/portal_io/login?' +
                        'username=' + '211250xxx' +
                        '&' +
                        'password=' + 'xxxxxxxx' + '"'
                git url: 'xxx'
            }
        }

        stage('Maven Build') {
            agent {
                docker {
                    image 'maven:latest'
                    args ' -v /root/.m2:/root/.m2'
                }
            }
            steps {
                echo "2. Using Maven to Build"
                sh 'mvn -B clean package'
            }
        }

        stage('Build Image') {
            agent {
                label 'master'
            }
            steps {
                echo "3. Build Image"
                sh 'docker build -t cloud-native:${BUILD_ID} .'
                sh 'docker tag cloud-native:${BUILD_ID} harbor.edu.cn/nju17/cloud-native:${BUILD_ID}'
            }
        }

        stage('Push Image') {
            agent {
                label 'master'
            }
            steps {
                echo "4. Push Docker Image"
                sh 'docker login harbor.edu.cn ' +
                        '-u ' + 'nju17' +
                        ' -p ' + 'nju172023'
                sh 'docker push harbor.edu.cn/nju17/cloud-native:${BUILD_ID}'
            }
        }
    }
}

node('slave') {
    container('jnlp-kubectl') {
        stage('Clone & Change YAML') {
            echo "5. Clone YAML to Slave and Change YAML"
            //xxx needs to be replaced
            sh 'curl "http://p.nju.edu.cn/portal_io/login?' +
                    'username=' + '211250xxx' +
                    '&' +
                    'password=' + 'xxxxxxxx' + '"'
            git url: 'xxx'
            sh 'sed -i "s#{VERSION}#${BUILD_ID}#g" deployment.yaml'
        }

        stage ('Deploy') {
            echo "6. Deploy to K8s"
            //xxx needs to be replaced
            sh 'curl "http://p.nju.edu.cn/portal_io/login?' +
                    'username=' + '211250xxx' +
                    '&' +
                    'password=' + 'xxxxxxxx' + '"'
            sh 'docker pull harbor.edu.cn/nju17/cloud-native:${BUILD_ID}'
            sh 'kubectl apply -f deployment.yaml -n nju17'
        }

        stage('Monitor') {
            echo "7. Start Monitor"
            sh 'kubectl apply -f monitor.yaml -n monitor'
        }
    }
}