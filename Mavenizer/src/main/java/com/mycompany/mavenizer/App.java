package com.mycompany.mavenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App {

     static String jarsFolder = "C:\\home\\eclipses\\eclipse3.7\\plugins";
     static String jarVersion = "3.7.0";
     static String groupID = "org.eclipse.maven";
     static String repositoryId = "thingml";
     static String repositoryUrl = "http://thingml.org:8081/artifactory/ext-release-local";
     static int startID = 496;

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(16);
        
        File jarsFolderFile = new File(jarsFolder);
        int id = 0;
        for (String file : jarsFolderFile.list()) {
            if (file.endsWith(".jar")) {
                id++;
                if (id < startID) continue;
                StringBuffer command = new StringBuffer("C:\\home\\opt\\apache-maven-3.0.3\\bin\\mvn.bat deploy:deploy-file ");
                command.append(" -Dfile=" + jarsFolder + "\\" + file);
                command.append(" -DgroupId=" + groupID);
                command.append(" -DartifactId=" + file.substring(0,file.indexOf("_")));
                command.append(" -Dversion=" + jarVersion);
                command.append(" -DrepositoryId=" + repositoryId);
                command.append(" -Dpackaging=jar");
                command.append(" -DgeneratePom=true");
                command.append(" -Durl=" + repositoryUrl);
                System.out.println(command);
                
                es.submit( new RunCommand( command.toString(), id) );
             
                
                
            } else {
                System.out.println("ignoring " + file);
            }
        }
        
        
    }
   
}

class RunCommand implements Runnable {

    public String cmd;
    public int id;

    public RunCommand(String cmd, int id) {
        this.cmd = cmd;
        this.id = id;
    }

    public void run() {

        Process p;
        try {
            System.out.println("!!!!! START ID = " + id);
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader r1 = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String x;
            while ((x = r1.readLine()) != null) {
                System.out.println(x);
            }
            r1.close();
            p.waitFor(); // wait for the end of the command
            System.out.println("!!!!! DONE ID = " + id);

        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("!!!!! ERROR ID = " + id);
        }

    }

}