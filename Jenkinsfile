pipeline {
	agent any
	tools{
		gradle '7.1.1'
		jdk("openjdk11")
	}

	stages {
		stage('Ready') {
			steps {
				sh "echo 'Ready'"
				sh "java -version"
			}
		}
		stage('Spring Boot Clean & Build') {
			steps {
				sh "chmod +x gradlew;"
				sh "./gradlew clean;"
				sh "./gradlew build;"
			}
		}
		stage('Docker build & push & container run') {
			steps {
                // ci-instance에 접근해서 docker build / push
				sh '''ssh -T ubuntu@$(/sbin/ip rout | awk '/default/ { print $3 }') <<EOF
					cd /home/ubuntu/jenkins-home/workspace/sandbox-pipe
					docker build -t dlckdgk4858/imgo:dev .
					docker login -u dlckdgk4858 -p zskyui1212@
					docker push dlckdgk4858/imgo:dev
					exit
					EOF'''
                // imgo-dev 서버에 접근해서 docker pull / run
				sh '''ssh -T ubuntu@10.0.1.73 <<EOF
					docker login -u dlckdgk4858 -p zskyui1212@
					docker pull dlckdgk4858/imgo:dev
					docker run -p 8080:8080 -d dlckdgk4858/imgo:dev
					exit
					EOF'''
			}
		}
	}
}
