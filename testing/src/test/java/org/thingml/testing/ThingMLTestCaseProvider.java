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
import java.util.ArrayList;
import java.util.Collection;

import org.junit.runner.Describable;

public abstract class ThingMLTestCaseProvider implements Describable {
	/* --- Base functions --- */
	
	public abstract Collection<ThingMLTestCaseCompiler> getCompilers();
	
	public abstract Collection<ThingMLTestCase> getTestCases();
	
	
	/* --- Helper functions --- */
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
	
	private void addTestFilesRecursively(File dir, Collection<File> files) {		
		// Find all the files with names 'test(.+).thingml'
		for (File testFile : dir.listFiles(testfileFilter)) {
			files.add(testFile);
		}
		
		// Find all the directories to search recursively
		for (File subDir : dir.listFiles(directoryFilter)) {
			addTestFilesRecursively(subDir, files);
		}
	}
	
	protected Collection<File> getTestFilesInResourceDir(String baseDir) {
		File path = new File(this.getClass().getResource(baseDir).getFile());
		
		Collection<File> files = new ArrayList<File>();
		addTestFilesRecursively(path, files);
		
		return files;
	}
}
