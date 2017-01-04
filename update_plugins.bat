call mvn clean install
cd org.thingml.editor.standaloneApp
call mvn -f pom_eclipse.xml clean install
rm ../org.thingml.eclipse.ui/lib/*.*
cp target/org.thingml.editor.standaloneApp-*-jar-with-dependencies.jar ../org.thingml.eclipse.ui/lib/thingml.jar