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
package org.thingml.tests;

import java.io.*;

public class TestsGeneration {

    public static void main(String[] args) {
        try {
            org.apache.commons.io.FileUtils.deleteDirectory(new File((new File(System.getProperty("user.dir"))).getParentFile(), "org.thingml.cmd/tmp"));
            BufferedWriter result = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("results.html")));
            result.write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "	<head>\n" +
                    "		<meta charset=\"utf-8\" />\n" +
                    "		<title>ThingML tests results</title>\n" +
                    "		<style>\n" +
                    "		table\n" +
                    "		{\n" +
                    "			border-collapse: collapse;\n" +
                    "		}\n" +
                    "		td, th \n" +
                    "		{\n" +
                    "			border: 1px solid black;\n" +
                    "		}\n" +
                    "		.green\n" +
                    "		{\n" +
                    "			background: lightgreen\n" +
                    "		}\n" +
                    "		.red\n" +
                    "		{\n" +
                    "			background: red\n" +
                    "		}\n" +
                    "		</style>\n" +
                    "	</head>\n" +
                    "	<body>\n" +
                    "		<Table>\n" +
                    "	<tr>\n" +
                    "		<th>Test name</th>\n" +
                    "		<th>Compiler</th>\n" +
                    "		<th>Result</th>\n" +
                    "	</tr>\n");
            result.close();
            BufferedWriter stats = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("stats.html")));
            stats.write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "	<head>\n" +
                    "		<meta charset=\"utf-8\" />\n" +
                    "		<title>ThingML tests stats</title>\n" +
                    "		<style>\n" +
                    "		table\n" +
                    "		{\n" +
                    "			border-collapse: collapse;\n" +
                    "		}\n" +
                    "		td, th \n" +
                    "		{\n" +
                    "			border: 1px solid black;\n" +
                    "		}\n" +
                    "		.green\n" +
                    "		{\n" +
                    "			background: lightgreen\n" +
                    "		}\n" +
                    "		.red\n" +
                    "		{\n" +
                    "			background: red\n" +
                    "		}\n" +
                    "		</style>\n" +
                    "	</head>\n" +
                    "	<body>\n" +
                    "		<Table>\n" +
                    "	<tr>\n" +
                    "		<th>Compiler</th>\n" +
                    "		<th>Test</th>\n" +
                    "		<th>CPU</th>\n" +
                    "		<th>Memory</th>\n" +
                    "		<th>Binary size</th>\n" +
                    "		<th>Performed transitions</th>\n" +
                    "		<th>Execution time</th>\n" +
                    "	</tr>\n");
            stats.close();

            ProcessBuilder pb = new ProcessBuilder("python", "genTests.py");
            pb.directory(new File("src/main/thingml/tests/Tester"));
            pb.redirectErrorStream(true);
            Process proc = pb.start();
            System.out.println("Process started !");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = in.readLine();

            while (  line != null){
                System.out.println(line);
                line = in.readLine();
            }
            proc.destroy();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}