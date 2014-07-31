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
package org.thingml.tester;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

@RunWith(JUnit4.class)
public class testEmptyTransitionTest extends TestCase {
	
	private static boolean setUpIsNotDone = true;
	private static boolean CTried = false;
	private static boolean ScalaTried = false;
	private static boolean JavaTried = false;
	private static boolean successC = true;
	private static boolean successScala = true;
	private static boolean successJava = true;
	private static String messageC = "";
	private static String messageScala = "";
	private static String messageJava = "";
	@Before
	public void init(){
		if (setUpIsNotDone)
		try{
			setUpIsNotDone = false;
			ProcessBuilder pb = new ProcessBuilder("python","execute.py","testEmptyTransition");
			pb.directory(new File("src/test/resources"));
			pb.redirectErrorStream(true);
			Process proc = pb.start();
			System.out.println("Process started !");
			String line;
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			proc.destroy();
			in.close();
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}
@Test
	public void testC(){
		try{
			CTried = true;
			System.out.println(System.getProperty("user.dir"));
			BufferedReader dump = new BufferedReader(new InputStreamReader(new FileInputStream("target/dump/testEmptyTransition.dump")));
			BufferedReader dumpC = new BufferedReader(new InputStreamReader(new FileInputStream("target/dump/testEmptyTransitionC.dump")));
			String regex;
			String input;
			String output;
			String outputC;
			while ((regex = dump.readLine()) != null){
				input = dump.readLine();
				output = dump.readLine();
				outputC = dumpC.readLine();
				Pattern pattern = 
				Pattern.compile(output);
				Matcher matcher = 
				pattern.matcher(outputC);
				boolean success = matcher.matches();
				if(!success){
					successC=false;
					if(outputC == "ErrorAtCompilation")
						messageC = "Error at compilation";
					else
						messageC = outputC+" does not match "+output+" for input "+input+" ("+regex+")";
				}
				assertTrue("C compiler error: "+outputC+" does not match "+output+" for input "+input+" ("+regex+")",success);
			}
		}catch(Exception e){
		successC=false;
		messageC = "NoDumpFound";
		fail("Error: " + e.getMessage());}
	}
@Test
	public void testScala(){
		try{
			ScalaTried = true;
			System.out.println(System.getProperty("user.dir"));
			BufferedReader dump = new BufferedReader(new InputStreamReader(new FileInputStream("target/dump/testEmptyTransition.dump")));
			BufferedReader dumpScala = new BufferedReader(new InputStreamReader(new FileInputStream("target/dump/testEmptyTransitionScala.dump")));
			String regex;
			String input;
			String output;
			String outputScala;
			while ((regex = dump.readLine()) != null){
				input = dump.readLine();
				output = dump.readLine();
				outputScala = dumpScala.readLine();
				Pattern pattern = 
				Pattern.compile(output);
				Matcher matcher = 
				pattern.matcher(outputScala);
				boolean success = matcher.matches();
				if(!success){
					successScala=false;
					if(outputScala == "ErrorAtCompilation")
						messageScala = "Error at compilation";
					else
						messageScala = outputScala+" does not match "+output+" for input "+input+" ("+regex+")";
				}
				assertTrue("Scala compiler error: "+outputScala+" does not match "+output+" for input "+input+" ("+regex+")",success);
			}
			dump.close();
			dumpScala.close();
		}catch(Exception e){
		successScala=false;
		messageScala = "NoDumpFound";
		fail("Error: " + e.getMessage());}
	}
@Test
	public void testJava(){
		try{
			JavaTried = true;
			System.out.println(System.getProperty("user.dir"));
			BufferedReader dump = new BufferedReader(new InputStreamReader(new FileInputStream("target/dump/testEmptyTransition.dump")));
			BufferedReader dumpJava = new BufferedReader(new InputStreamReader(new FileInputStream("target/dump/testEmptyTransitionJava.dump")));
			String regex;
			String input;
			String output;
			String outputJava;
			while ((regex = dump.readLine()) != null){
				input = dump.readLine();
				output = dump.readLine();
				outputJava = dumpJava.readLine();
				Pattern pattern = 
				Pattern.compile(output);
				Matcher matcher = 
				pattern.matcher(outputJava);
				boolean success = matcher.matches();
				if(!success){
					successJava=false;
					if(outputJava == "ErrorAtCompilation")
						messageJava = "Error at compilation";
					else
						messageJava = outputJava+" does not match "+output+" for input "+input+" ("+regex+")";
				}
				assertTrue("Java compiler error: "+outputJava+" does not match "+output+" for input "+input+" ("+regex+")",success);
			}
			dump.close();
			dumpJava.close();
		}catch(Exception e){
		successJava=false;
		messageJava = "NoDumpFound";
		fail("Error: " + e.getMessage());}
	}
	@After
	public void dump(){
		if(true && CTried && ScalaTried && JavaTried && true)
		try{
			PrintWriter result = new PrintWriter(new BufferedWriter(new FileWriter("src/test/resources/results.html", true)));
			result.write("<tr><th></th><th></th><th></th></tr>\n");
			if (successC){
				result.write("<tr class=\"green\">\n");
				result.write("<th>testEmptyTransition</th><th>C</th><th>Success</th>\n");
			}else{
				result.write("<tr class=\"red\">\n");
				result.write("<th>testEmptyTransition</th><th>C</th><th>"+messageC+"</th>\n");
			}
			result.write("</tr>\n");
			if (successScala){
				result.write("<tr class=\"green\">\n");
				result.write("<th>testEmptyTransition</th><th>Scala</th><th>Success</th>\n");
			}else{
				result.write("<tr class=\"red\">\n");
				result.write("<th>testEmptyTransition</th><th>Scala</th><th>"+messageScala+"</th>\n");
			}
			result.write("</tr>\n");
			if (successJava){
				result.write("<tr class=\"green\">\n");
				result.write("<th>testEmptyTransition</th><th>Java</th><th>Success</th>\n");
			}else{
				result.write("<tr class=\"red\">\n");
				result.write("<th>testEmptyTransition</th><th>Java</th><th>"+messageJava+"</th>\n");
			}
			result.write("</tr>\n");
			result.close();
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}
}