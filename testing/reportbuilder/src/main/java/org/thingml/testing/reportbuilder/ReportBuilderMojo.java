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

import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.surefire.log.PluginConsoleLogger;
import org.apache.maven.plugin.surefire.log.api.ConsoleLogger;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.reporting.MavenReportException;
import org.thingml.testing.reportbuilder.parser.Reparser;

@Mojo(name = "generate")
public class ReportBuilderMojo extends AbstractMojo {
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("ThingML report generator");
		ConsoleLogger logger = new PluginConsoleLogger(getLog());
		
		try {
			Reparser parser = new Reparser("target/surefire-reports/", logger);
			ReportBuilder builder = new ReportBuilder(parser.getResults(), logger);
			builder.saveToHTML("target/thingml-testreport.html");
		} catch (IOException | MavenReportException e) {
			logger.error(e);
		}
	}
}
