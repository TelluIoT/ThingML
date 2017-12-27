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
package org.thingml.testing.reportbuilder.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.surefire.log.api.ConsoleLogger;
import org.apache.maven.plugins.surefire.report.ReportTestCase;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;
import org.apache.maven.reporting.MavenReportException;

public class Reparser {
	private ConsoleLogger log;
	private List<ReportTestSuite> original;
	private List<Suite> results;
	
	public Reparser(String testResultsPath, ConsoleLogger logger) throws IOException, MavenReportException {
		log = logger;
		
		List<File> dir = new ArrayList<File>();
		dir.add(new File(testResultsPath));
		
		if (!SurefireReportParser.hasReportFiles(dir.get(0)))
			throw new IOException("No test report files found");
		
		SurefireReportParser parser = new SurefireReportParser(dir, Locale.getDefault(), log);
		original = parser.parseXMLReportFiles();
		
		results = new ArrayList<Suite>();
		doReparsing();
	}
	
	private void doReparsing() {
		results.clear();
		
		Pattern p = Pattern.compile("^(\\S+)\\s+\\[(.+)\\]$");
		Pattern ps = Pattern.compile("^(.+) \\[(.+)(\\..+)*\\]$");
		
		for (ReportTestSuite suite : original) {
			// Add this suite to the tree
			Matcher ms = ps.matcher(suite.getFullClassName());
			if (!ms.matches()) continue;
			
			String[] cSuites = ms.group(2).split("\\.");
			Collection<Suite> nSuites = results;
			Suite nSuite = null;
				
			for (String cSuite : cSuites) {
				nSuite = nSuites.stream().filter((s) -> s.getName().equals(cSuite)).findFirst().orElse(null);
				if (nSuite == null) {
					nSuite = new Suite(cSuite);
					nSuites.add(nSuite);
				}
				nSuites = nSuite.getSuites();
			}
			// Add original
			nSuite.setOriginal(suite);
			
			// Add all tests
			for (ReportTestCase test : suite.getTestCases()) {
				Matcher m = p.matcher(test.getName().trim());
				String testName;
				String testCaseName;
				if (m.matches()) {
					testName = m.group(2);
					testCaseName = m.group(1);
				} else {
					testName = test.getName().trim();
					testCaseName = null;
				}
				
				// Check if this test is already added
				Test nTest = nSuite.getTests().stream().filter((t) -> t.getName().equals(testName)).findFirst().orElse(null);
				if (nTest == null) {
					nTest = new Test(testName);
					nSuite.getTests().add(nTest);
				}
				// Add original or testcase
				if (testCaseName == null)
					nTest.setOriginal(test);
				else {
					TestCase nTestCase = new TestCase(nTest, testCaseName);
					nTestCase.setOriginal(test);
					nTest.getTestCases().add(nTestCase);
				}
			}
		}
	}
	
	public List<Suite> getResults() {
		return results;
	}
}
