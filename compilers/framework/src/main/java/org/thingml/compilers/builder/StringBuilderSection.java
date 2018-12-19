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
package org.thingml.compilers.builder;

@Deprecated
public class StringBuilderSection extends Section {
	StringBuilder stringbuilder = new StringBuilder();

	protected StringBuilderSection(Section parent, String name) {
		super(parent, name);
		this.lines();
	}
	
	public StringBuilder stringbuilder() { return this.stringbuilder; }
	
	@Override
	public void prepare(SourceBuilder builder) {
		this.children.clear();
		// Add all the lines of the StringBuilder as children of this section
		int currentLeadingWs = Integer.MAX_VALUE;
		Section currentSection = this;
		for (String line : this.stringbuilder.toString().split("\\r?\\n")) {
			String trimmed = line.trim();
			int leadingWs = line.indexOf(trimmed);
			// Fix indentation
			if (leadingWs > currentLeadingWs) {
				// Add new level of indentation
				currentSection = currentSection.section("indent").lines().indent();
			} else if (leadingWs < currentLeadingWs) {
				// Go up one level (not above self)
				if (currentSection != this)
					currentSection = currentSection.parent;
			}
			// Add current lime (trimmed version)
			currentSection.append(trimmed);
			currentLeadingWs = leadingWs;
		}
		super.prepare(builder);
	}

}
