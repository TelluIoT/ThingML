mvn clean install
cd org.thingml.editor.standaloneApp
mvn -f pom_eclipse.xml clean install
cp target/org.thingml.editor.standaloneApp-0.7.0-SNAPSHOT-jar-with-dependencies.jar ../org.thingml.eclipse.ui/lib/