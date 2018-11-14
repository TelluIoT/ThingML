properties( [ buildDiscarder(logRotator(numToKeepStr: '20'))])

timeout(60) {
	node {
	   stage('Downloading changes') { // for display purposes
		  //git "https://github.com/SINTEF-9012/ThingML.git"
		  checkout scm
	   }
	   stage('Building Compilers') {
		  // Run the maven build
		  sh "mvn -Dmaven.test.failure.ignore clean install"
	   }
	   stage('Building Xtext plugins') {
		  sh("cd language/ && mvn -Dmaven.test.failure.ignore -pl !thingml.ui.tests clean install && cd ..")
	   }
	   stage('Testing') {
		  sh("cd testing/ && mvn -Dmaven.test.failure.ignore test thingmlreport:generate && cd ..")
	   }
	   stage('Publishing HTML Report') {
		  publishHTML (target: [
			  allowMissing: false,
			  alwaysLinkToLastBuild: false,
			  keepAll: true,
			  reportDir: 'testing/target/',
			  reportFiles: 'thingml-testreport.html',
			  reportName: "ThingML Test Report"
			])      
	   }
	}
}
