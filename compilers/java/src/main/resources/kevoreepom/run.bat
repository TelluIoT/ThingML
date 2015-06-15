cd /d %~dp0
call mvn clean install
start /B java -jar org.kevoree.editor.jar
java -Dnode.name=node0 -Dnode.bootstrap=src/main/kevs/main.kevs  -jar org.kevoree.watchdog.jar 5.1.3