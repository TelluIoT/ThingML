```bash
mvn clean install

#This part should be executed on server
cd org.thingml.editor.standaloneApp
mvn -f pom_eclipse.xml clean install
cp target/org.thingml.editor.standaloneApp-0.6.0-SNAPSHOT-jar-with-dependencies.jar ../org.thingml.eclipse.ui/lib/
#

cd ../org.thingml.eclipse
mvn -f pom_eclipse.xml clean install
# commit and push changes in GitHub
```
