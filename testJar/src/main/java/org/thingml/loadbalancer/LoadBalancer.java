/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.loadbalancer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.thingml.testjar.Command;
import org.thingml.testjar.TestHelper;

/**
 *
 * @author sintef
 */
public class LoadBalancer {
    public static void main(String[] args) throws ExecutionException {
        final File workingDir = new File(System.getProperty("user.dir"));
        File tmpDir = new File(workingDir, "tmp");
        final File testCfgDir = new File(tmpDir, "thingml");
        final File codeDir = new File(tmpDir, "genCode");
        final File logDir = new File(tmpDir, "log");
        File compilerJar;
        if(args.length > 0) {
            compilerJar = new File(workingDir, args[0]);
        } else {
            compilerJar = new File(workingDir, "../compilers/registry/target/compilers.registry-0.7.0-SNAPSHOT-jar-with-dependencies.jar");
        }
        
        File testJar = new File(workingDir, "target/testJar-0.7.0-SNAPSHOT-jar-with-dependencies.jar");
        
        tmpDir.delete();
        tmpDir = new File(workingDir, "tmp");

        final File testFolder = new File(workingDir.getPath() + "/src/main/resources/tests");
        String testPattern = "test(.+)\\.thingml";
        
        Set<Command> tasks = new HashSet<>();
        List<Future<String>> results = new ArrayList<Future<String>>();
        
        System.out.println("****************************************");
        System.out.println("*              Test Begin              *");
        System.out.println("****************************************");
        
        System.out.println("");
        
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Tmp Directory = " + tmpDir);
        System.out.println("Compiler = " + compilerJar);
        System.out.println("");
        
        System.out.println("****************************************");
        System.out.println("*         Properties Reading           *");
        System.out.println("****************************************");
        
        System.out.println("");
        Properties prop = new Properties();
	InputStream input = null;
        
        String languageList = null, useBlackList = null, testList = null;
        
	try {

            input = new FileInputStream(new File(workingDir, "config.properties"));

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            languageList = prop.getProperty("languageList");
            useBlackList = prop.getProperty("useBlackList");
            testList = prop.getProperty("testList");
            System.out.println("languageList:" + languageList);
            System.out.println("useBlackList:" + useBlackList);
            System.out.println("testList:" + testList);
        
            Properties loadBalancerProp = new Properties();
            InputStream lbInput = null;

            String nodeList = null;
            int totalWeight = 0;

            lbInput = new FileInputStream(new File(workingDir, "loadBalancer.properties"));

            // load a properties file
            loadBalancerProp.load(lbInput);

            // get the property value and print it out
            nodeList = loadBalancerProp.getProperty("nodeList");

            HashMap<String, CloudNode> nl = new HashMap<>();
            if(testList != null) {
                for(String nstr : nodeList.split(",")) {
                    String nodeName = nstr.trim();
                    CloudNode n = new CloudNode(nodeName);
                    n.ip = loadBalancerProp.getProperty(nodeName + "_ip");
                    n.weight = Integer.parseInt(loadBalancerProp.getProperty(nodeName + "_weight"));
                    n.port = Integer.parseInt(loadBalancerProp.getProperty(nodeName + "_port"));
                    totalWeight += n.weight;
                    System.out.println("nodeList item: <" + n.name + ", " + n.ip + ", " + n.port + ", " + n.weight + ">");
                    nl.put(nodeName, n);
                }
            }
        
            Set<String> tl = new HashSet<>();
            if(testList != null) {
                for(String tstr : testList.split(",")) {
                    System.out.println("testList item: <" + tstr.trim() + ">");
                    tl.add(tstr.trim());
                }
            }

            //Test Sources Selection

        Set<File> testFiles;
        if(useBlackList != null) {
            if(useBlackList.compareToIgnoreCase("false") == 0) {
                testFiles = TestHelper.whiteListFiles(testFolder, tl);
            } else if (useBlackList.compareToIgnoreCase("true") == 0) {
                testFiles = TestHelper.blackListFiles(testFolder, tl);
            } else {
                testFiles = TestHelper.listTestFiles(testFolder, testPattern);
            }
        } else {
            testFiles = TestHelper.listTestFiles(testFolder, testPattern);
        }
            
            //Load balancing
            
            CloudNode worker[] = new CloudNode[totalWeight];
            int index = 0;
            for (CloudNode n : nl.values()) {
                for(int i = 0; i < n.weight; i++) {
                    worker[index] = n;
                    index++;
                }
            }
            index = 0;
            for(File t : testFiles) {
                worker[index].tests.add(t);
                index = (index + 1) % totalWeight;
            }
            for (CloudNode n : nl.values()) {
                n.languages.add(languageList);
                n.makeTestDir(workingDir, compilerJar, testJar, testFolder);
            }
            System.out.println("Master Node IP: " + loadBalancerProp.getProperty("masternode_ip"));
            System.out.println("Master Node Port: " + loadBalancerProp.getProperty("masternode_port"));
            generateDispatchScript(workingDir, loadBalancerProp.getProperty("masternode_ip"), 
                    loadBalancerProp.getProperty("masternode_port"), 
                    nl.values());

	} catch (IOException ex) {
		ex.printStackTrace();
	}
    }
    
    public static void generateDispatchScript(File workingDir, String masterIP, String masterPort, Collection<CloudNode> nodeList) {
        String dispatchScript = TestHelper.getTemplateByID("loadBalancer/dispachTest.sh");
        dispatchScript = dispatchScript.replace("#IP_MASTER", masterIP);
        dispatchScript = dispatchScript.replace("#PORT_MASTER", masterPort);
        
        String tasks ="";
        for(CloudNode n : nodeList) {
            tasks += "(work " + n.name + " " + n.ip + " " + n.port + ")&\n";
        }
        
        dispatchScript = dispatchScript.replace("#DISPATCH", tasks);
        File dispatchScriptFile = new File(workingDir, "dispatch.sh");
            try {
                PrintWriter w = new PrintWriter(dispatchScriptFile);
                w.print(dispatchScript);
                w.close();
            } catch (Exception ex) {
                System.err.println("Problem writing log");
                ex.printStackTrace();
            }
    }
}