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
package org.thingml.xtext.validation;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;

public class Checker {
	private List<Issue> issues;
	
	private final Comparator<Issue> issueComparator = Comparator
		// Sort by URI first (nulls first)
		.comparing(Issue::getUriToProblem, Comparator.nullsFirst(Comparator.comparing(URI::toFileString, Comparator.nullsFirst(Comparator.naturalOrder()))))
		// Then sort by line number (nulls first)
		.thenComparing(Issue::getLineNumber, Comparator.nullsFirst(Comparator.naturalOrder()))
	;
	
	public Checker() {
		issues = new LinkedList<Issue>();
		// TODO: Add a constructor that accepts compiler-specific validators as well
	}
    
    private static synchronized List<Issue> validate(List<Issue> out, EObject o) {
    	out.clear();
    	final Resource r = o.eResource();
    	if (r instanceof XtextResource) {
    		IResourceValidator validator = ((XtextResource)r).getResourceServiceProvider().getResourceValidator();
    		List<Issue> issues = validator.validate(r, CheckMode.ALL, CancelIndicator.NullImpl);
    		out.addAll(issues);    	
    	}
    	return out;
    }
    
    public boolean validateModel(ThingMLModel model) {
    	validate(this.issues, model);
    	return !this.hasErrors();
    }
    
    public boolean validateConfiguration(Configuration cfg) {
    	validate(this.issues, cfg);
    	return !this.hasErrors();
    }
    
    /* -- Helpers -- */
    private List<Issue> getIssueBySeverity(Severity severity) {
    	List<Issue> errors = new LinkedList<Issue>();
    	for (Issue issue : this.issues)
    		if (issue.getSeverity() == severity)
    			errors.add(issue);
    	return errors;
    }
    
    public List<Issue> getErrors() {
    	final List<Issue> sorted = getIssueBySeverity(Severity.ERROR);
    	sorted.sort(issueComparator);
    	return sorted;    	
    }
    
    public List<Issue> getWarnings() {
    	final List<Issue> sorted = getIssueBySeverity(Severity.WARNING);
    	sorted.sort(issueComparator);
    	return sorted;   
    }
    
    public List<Issue> getInfos() {
    	final List<Issue> sorted = getIssueBySeverity(Severity.INFO);
    	sorted.sort(issueComparator);
    	return sorted;   
    }
    
    public boolean hasErrors() {
    	for (Issue issue : this.issues)
    		if (issue.getSeverity() == Severity.ERROR)
    			return true;
    	return false;
    }

    
    /* -- Printing -- */
}
