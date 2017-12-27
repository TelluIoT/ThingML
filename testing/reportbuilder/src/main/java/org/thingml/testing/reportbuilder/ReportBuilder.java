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
package org.thingml.testing.reportbuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.maven.plugin.surefire.log.api.ConsoleLogger;
import org.apache.maven.surefire.shade.org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.thingml.testing.reportbuilder.parser.Suite;
import org.thingml.testing.reportbuilder.parser.Test;

public class ReportBuilder {
	private List<Suite> results;
	private ConsoleLogger log;
	
	public ReportBuilder(List<Suite> parsed, ConsoleLogger logger) {
		results = parsed;
		log = logger;
	}
	
	public void saveToHTML(String path) throws IOException {
		Document document = DocumentHelper.createDocument();
		document.addDocType("html", null, null);
		Element html = document.addElement("html");
		Element head = html.addElement("head");
		Element body = html.addElement("body");
		
		buildHead(html, head);
		buildDocument(head, body);
		addModal(body);
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setExpandEmptyElements(true);
		
		FileWriter file = new FileWriter(path);
		HTMLWriter writer = new HTMLWriter(file, format);
		writer.write(document);
		writer.close();
	}
	
	public void buildHead(Element html, Element head) {
		// Add Bootstrap
		html.addAttribute("lang", "en");
		head.addElement("meta").addAttribute("charset", "utf-8");
		head.addElement("meta").addAttribute("http-equiv", "X-UA-Compatible").addAttribute("content", "IE=edge");
		head.addElement("meta").addAttribute("name", "viewport").addAttribute("content", "width=device-width, initial-scale=1");
		head.addElement("link").addAttribute("rel", "stylesheet").addAttribute("href", "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css");
		
		// Add ChartJS
		head.addElement("script").addAttribute("src", "https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js");
		
		// Add jQuery
		head.addElement("script").addAttribute("src", "https://code.jquery.com/jquery-3.2.1.min.js");
		head.addElement("script").addAttribute("src", "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js");
		
		
		// Import custom scripts
		addJavaScipt(head, "createCharts.js");
		addJavaScipt(head, "showModal.js");
		addJavaScipt(head, "filtering.js");
		
		// Import custom styles
		addStylesheet(head, "header.css");
		addStylesheet(head, "table.css");
	}
	
	private void buildDocument(Element head, Element body) {
		// Set title
		head.addElement("title").addText("ThingML :: Testing :: Report");
		
		// Add containers
		Element top = body.addElement("div").addAttribute("class", "jumbotron");
		Element main = body.addElement("div").addAttribute("class", "container-fluid");
		
		buildTestsSummary(top);
		buildSuites(main, results);
	}
	
	private void buildTestsSummary(Element parent) {
		Element container = parent.addElement("div").addAttribute("class", "container-fluid");
		
		Element topRow = container.addElement("div").addAttribute("class", "row");
		topRow.addElement("div").addAttribute("class", "col-sm-6")
			.addElement("h1").addText("Summary");
		topRow.addElement("div").addAttribute("class", "col-sm-6 text-right")
			.addElement("h3").addText(Suite.countTests(results)+" tests in total");
		
		Element summaryRow = container.addElement("div").addAttribute("class", "row");
		// Add suite summaries
		for (Suite suite : results) {
			addSummaryChart(summaryRow.addElement("div").addAttribute("class", "col-sm-1"), suite.getName(), suite.getSuiteSummary().simplify());
		}
		// Add compiler summaries
		for (Entry<String,Summary> compilerSummary : Suite.getCompilerSummaries(results).entrySet()) {
			addSummaryChart(summaryRow.addElement("div").addAttribute("class", "col-sm-1"), compilerSummary.getKey(), compilerSummary.getValue().simplify());
		}
		
		// Add filterings
		Element filterRow = container.addElement("div").addAttribute("class", "row");
		filterRow.addElement("div").addAttribute("class", "col-sm-10");
		
		filterRow.addElement("div").addAttribute("class", "col-sm-2")
		         .addElement("button").addAttribute("id", "thingml-only-failures").addAttribute("type", "button").addAttribute("class", "btn btn-default")
		         .addText("Show failures only");
	}
	
	private void buildSuites(Element parent, List<Suite> suites) {
		for (Suite suite : suites) {
			String hasFailure = suite.hasFailure() ? " has-failure" : "";
			Element div = parent.addElement("div").addAttribute("class", "thingml-suite"+hasFailure);
			
			if (!suite.getTests().isEmpty()) {
				// Add title
				div.addElement("h1").addText(suite.getName())
					 			    .addAttribute("style", "margin-top: 100px");
				
				// Add tests
				if (!suite.getTests().isEmpty())
					buildTests(div, suite.getTests());
			}
			
			// Add children suits
			buildSuites(parent, suite.getSuites());
		}
	}
	
	private void buildTests(Element parent, List<Test> tests) {
		Element table = parent.addElement("table").addAttribute("class", "table table-hover");
		
		// Make colgroup so we can style columns
		Element colgroup = table.addElement("colgroup");
		colgroup.addElement("col");
		colgroup.addElement("col").addAttribute("span", ""+tests.size());
		
		
		// Sort tests
		List<Test> sortedTests = Test.sortTests(tests);
		
		// Build first row
		Element topRow = table.addElement("thead").addElement("tr");
		topRow.addElement("th"); // empty first cell
		for (Test test : sortedTests) {
			String testHasFailure = test.hasFailure() ? " has-failure" : "";
			topRow.addElement("th").addAttribute("class", "thingml-test"+testHasFailure).addText(test.getName());
		}
		
		// Get all the compilers
		List<String> compilers = Test.getSortedCompilers(tests);
		
		Element tBody = table.addElement("tbody");
		for (String compiler : compilers) {
			Element row = tBody.addElement("tr");
			row.addElement("td").addText(compiler);
			
			for (Test test : sortedTests) {
				TestCaseResult res = test.getTestCaseFullResult(compiler);
				String testHasFailure = test.hasFailure() ? " has-failure" : "";
				String testCaseHasFailure = res.getResult().simplify() == Result.FAILURE ? " has-failure" : "";
				
				row.addElement("td").addAttribute("class", "thingml-test"+testHasFailure)
			       .addElement("div").addAttribute("style","background-color:"+res.getResult().toColor()+";color:"+res.getResult().toTextColor())
			   	   .addAttribute("title", res.getResult().toString())
			   	   .addText(res.getResult().toIcon())
			   	   .addAttribute("class", "thingml-testcase-result"+testCaseHasFailure)
			   	   .addAttribute("data-result-message", res.getMessage())
			   	   .addAttribute("data-result-title", res.getTitle())
			   	   .addAttribute("data-result-error", res.getResult().toString());
			}
		}
	}
	
	/* --- Helpers --- */
	private void addSummaryChart(Element parent, String name, Summary summary) {
		parent.addElement("canvas").addAttribute("class", "thingml-summary")
			.addAttribute("data-summary", "{ \"text\": \""+name+"\", \"results\": "+summary.toJSON()+"}");
	}
	
	private void addModal(Element body) {
		Element content = body.addElement("div").addAttribute("class", "modal fade").addAttribute("id", "thingml-message-modal").addAttribute("tabindex", "-1").addAttribute("role", "dialog")
				              .addElement("div").addAttribute("class", "modal-dialog modal-lg").addAttribute("role", "document")
				              .addElement("div").addAttribute("class", "modal-content");
		
		Element header = content.addElement("div").addAttribute("class", "modal-header");
		header.addElement("button").addAttribute("type", "button").addAttribute("class","close").addAttribute("data-dismiss", "modal").addAttribute("aria-label", "Close")
			  .addElement("span").addAttribute("aria-hidden", "true").addText("x");
		header.addElement("h4").addAttribute("class","modal-title").addText("Title");
		
		content.addElement("div").addAttribute("class", "modal-body")
		       .addElement("pre").addAttribute("class", "thingml-message panel-danger bg-danger text-danger");
	}
	
	private void addJavaScipt(Element head, String file) {
		head.addElement("script").addAttribute("type","text/javascript").addText(readIncludedFile(file, log));
	}
	
	private void addStylesheet(Element head, String file) {
		head.addElement("style").addText(readIncludedFile(file, log));
	}
	
	/* --- Static helpers --- */
	private static String readIncludedFile(String file, ConsoleLogger log) {
		try {
			return IOUtils.toString(ReportBuilder.class.getResourceAsStream("/"+file));
		} catch (Exception e) {
			log.error("Couldn't read '"+file+"'", e);
			return "";
		}
	}
}
