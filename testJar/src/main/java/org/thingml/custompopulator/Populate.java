/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.custompopulator;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sintef
 */
public class Populate {
    
    public static void main(String[] args) {
        final File workingDir = new File(System.getProperty("user.dir"));
        final File testFolder = new File(workingDir.getPath() + "/src/main/resources/customTests/Network");
        
        //Targeted languages
        Lang posix = new Lang("Posix", "posix", "./#TARGET##TYPE##SP#Cfg/#TARGET##TYPE##SP#Cfg");
        Lang posixmt = new Lang("PosixMT", "posixmt", "./#TARGET##TYPE##SP#Cfg/#TARGET##TYPE##SP#Cfg");
        Lang nodejs = new Lang("NodeJS", "nodejs", "nodejs #TARGET##TYPE##SP#Cfg/main.js");
        Lang java = new Lang("Java", "java", "mvn -q -f #TARGET##TYPE##SP#Cfg/pom.xml exec:java");
        
        List<Lang> langs = new ArrayList<>();
        langs.add(posix);
        langs.add(posixmt);
        langs.add(nodejs);
        langs.add(java);
        
        //Protocol to be tested
        Protocol serial = new Protocol("Serial", false, false, "timeout 8 socat -x -v PTY,link=modem0 PTY,link=modem1&\nsleep 1\n");
        Protocol mQTT = new Protocol("MQTT", false, true);
        Protocol websocket = new Protocol("Websocket", true, true);
        Protocol mQTTbin = new Protocol("MQTT-binary", false, true);
        
        List<Protocol> prots = new ArrayList<>();
        prots.add(serial);
        prots.add(mQTT);
        prots.add(mQTTbin);
        prots.add(websocket);
        
        //Type to be tested.
        Type tEmpty = new Type("Empty", "I m1 m2 End");
        Type tBool = new Type("Bool", "I mBool mHBool End");
        Type tChar = new Type("Char", "I mChar mHChar End");
        Type tInt = new Type("Int", "I mInt8 mInt8 mInt8 mInt16 mInt16 mInt16 mInt32 mInt32 mInt32 mHInt End");
        Type tFloat = new Type("Float", "I mFloat mHFloat End");
        Type tUint = new Type("UInt", "I mUInt8 mUInt8 mUInt8 mUInt16 mUInt16 mUInt16 mUInt32 mUInt32 mUInt32 mHUInt End");
        
        List<Type> types = new ArrayList<>();
        types.add(tEmpty);
        types.add(tBool);
        types.add(tChar);
        types.add(tInt);
        types.add(tFloat);
        types.add(tUint);
        
        for(Protocol p : prots) {
            for(Type t : types) {
                for(int i = 0; i < langs.size(); i++) {
                    generateFiles(testFolder, t, langs.get(i), langs.get(i), p);
                    for(int j = 0; j < langs.size(); j++) {
                        if(i != j) generateFiles(testFolder, t, langs.get(i), langs.get(j), p);
                    }
                    
                    /*if(i != 0) {
                        generateFiles(testFolder, t, langs.get(i), langs.get(i-1), p);
                        generateFiles(testFolder, t, langs.get(i-1), langs.get(i), p);
                    }*/
                }
            }
        }
    }
    
    public static void generateFiles(File workingDir, Type t, Lang langCli, Lang langSrv, Protocol p) {
        File dir = new File(workingDir, p.dirName);
        if(!dir.exists() || !dir.isDirectory())
            dir.mkdir();
        
        File prop = new File(dir, "test" + t.type + langCli.lang + langSrv.lang + ".properties");
        writeFile(prop, getProperties(t, langCli, langSrv,  p));
        
        File script = new File(dir, "test" + t.type + langCli.lang + langSrv.lang + ".sh");
        writeFile(script, getScript(t, langCli, langSrv,  p));
    }
    
    public static void writeFile(File f, String str) {
        try {
            PrintWriter w = new PrintWriter(f);
            w.print(str);
            w.close();
        } catch (Exception ex) {
            System.err.println("Problem writing into " + f.getPath());
            ex.printStackTrace();
        }
    }
    
    public static String getProperties(Type t, Lang langCli, Lang langSrv, Protocol p) {
        String propTemplate = "#Depandancies List\n" +
        "depList=Cli, Srv\n" +
        "\n" +
        "#Test Config\n" +
        "run=test" + t.type + langCli.lang + langSrv.lang + ".sh\n" +
        "log=test" + t.type + langCli.lang + langSrv.lang + ".log\n" +
        "oracle=" + t.oracle + "\n" +
        "dump=cliStdo.log\n" +
        (p.mono ? "runMono=true\n" : "") +
        "\n" +
        "#Cli\n" +
        "Cli_src=Client" + t.type + (p.sp ? "SP" : "") +"Cfg.thingml\n" +
        "Cli_compiler=" + langCli.comp + "\n" +
        "\n" +
        "#Srv\n" +
        "Srv_src=Server" + t.type + "Cfg.thingml\n" +
        "Srv_compiler=" + langSrv.comp + "";
        return propTemplate;
    }
    
    public static String getScript(Type t, Lang langCli, Lang langSrv, Protocol p) {
        String scriptTemplate = "#!/bin/bash\n" +
        "\n" +
        "rm cliStdo.log &> /dev/null\n" +
        "\n" +
        p.preExec +
        "timeout -s SIGINT 6 " + langSrv.getExec("Server", t.type, "") + " > srvStdo.log 2> srvStdr.log&\n" +
        "sleep 3\n" +
        "timeout -s SIGINT 4 " + langCli.getExec("Client", t.type, (p.sp ? "SP" : "")) + " > cliStdo.log 2> cliStdr.log&\n" +
        "\n" +
        "sleep 5\n" +
        "\n" +
        "#printf \"Cli stdo:\\n\\n\"\n" +
        "cat cliStdo.log\n" +
        "#printf \"\\nCli stdr:\\n\\n\"\n" +
        ">&2 cat cliStdr.log\n" +
        "\n" +
        "#printf \"\\n\\nSrv stdo:\\n\\n\"\n" +
        ">&2 cat srvStdo.log\n" +
        "#printf \"\\nSrv stdr:\\n\\n\"\n" +
        ">&2 cat srvStdr.log";
        return scriptTemplate;
    }
}
