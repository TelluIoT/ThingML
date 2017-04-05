node {
   stage('Downloading changes') { // for display purposes
      //git "https://github.com/SINTEF-9012/ThingML.git"
      checkout scm
   }
   stage('Building Compilers') {
      // Run the maven build
      sh "mvn -Dmaven.test.failure.ignore clean install"
   }
   stage('Building Xtext plugings') {
	sh("cd language/ && mvn  -Dmaven.test.failure.ignore clean install && cd ..")
   }
   stage('Building TestJar') {
      sh("cd testJar/ && mvn  -Dmaven.test.failure.ignore clean install && cd ..")
   }
   stage('Testing') {
      sh "./testframework/test.py"
   }
   stage('Publishing HTML Report') {
      publishHTML (target: [
          allowMissing: false,
          alwaysLinkToLastBuild: false,
          keepAll: true,
          reportDir: 'htmlreports',
          reportFiles: 'index.html',
          reportName: "Test Execution Report"
        ])      
   }
}
