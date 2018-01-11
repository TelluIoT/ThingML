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
package org.thingml.testing;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runner.Describable;
import org.thingml.testing.framework.ThingMLTest;

public abstract class ThingMLTestProvider implements Describable {
	/* --- Base functions --- */
	
	//public abstract Collection<ThingMLTestCaseCompiler> getCompilers();
	public abstract String[] getCompilers();
	
	//public abstract Collection<ThingMLTestCase> getTestCases();
	public abstract Collection<ThingMLTest> getTests();
	
	/* --- Helper functions --- */
	private static class TestFilter {
		private URI base;
		private List<String> directories = new ArrayList<String>();
		private List<String> files = new ArrayList<String>();
		
		public TestFilter() {
			// Find the base URI of the resources folder
			this.base = new File(this.getClass().getResource("/").getFile()).toURI();
			
			// Check which test files should be included from system properties
			String tests = System.getProperty("tests", "");
			for (String test : tests.split(",")) {
				if (test.isEmpty())
					continue;
				if (test.endsWith("/"))
					directories.add(test);
				else if (test.endsWith(".thingml"))
					files.add(test);
				else
					files.add(test+".thingml");
			}
		}
		
		public boolean checkTest(File test, File dir) {
			// If both filters are empty, include everything
			if (directories.isEmpty() && files.isEmpty())
				return true;
			
			// Check if the file is explicitly included
			for (String file : files)
				if (file.equals(test.getName()))
					return true;
			
			// Check if the directory is explicitly included
			for (String directory : directories)
				if (this.base.relativize(dir.toURI()).toString().contains(directory))
					return true;
			
			return false;
		}
	}
	
	private static final FileFilter directoryFilter = new FileFilter() {
		@Override
		public boolean accept(File dir) {
			return dir.isDirectory();
		}
	};
	private static final FileFilter testfileFilter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return (!file.isDirectory() && file.getName().matches("^test.+\\.thingml$"));
		}
	};
	
	private void addTestFilesRecursively(TestFilter filter, File dir, Collection<File> files) {		
		// Find all the files with names 'test(.+).thingml'
		for (File testFile : dir.listFiles(testfileFilter)) {
			if (filter.checkTest(testFile, dir))
				files.add(testFile);
		}
		
		// Find all the directories to search recursively
		for (File subDir : dir.listFiles(directoryFilter)) {
			addTestFilesRecursively(filter, subDir, files);
		}
	}
	
	protected Collection<File> getTestFilesInResourceDir(String baseDir) {
		File path = new File(this.getClass().getResource(baseDir).getFile());
		
		TestFilter filter = new TestFilter();
		
		Collection<File> files = new ArrayList<File>();
		addTestFilesRecursively(filter, path, files);

		return files;
	}
}
