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

import java.io.IOException;
import java.io.Writer;

@Deprecated
public class Element {
	protected String value = null;
	protected boolean enabled = true;
	
	public Element() {}
	public Element(String string) { this.set(string); }
	public Element(Object...objects) { this.set(objects); }
	
	public static Element EMPTY = new Element() {
		@Override
		public void prepare(SourceBuilder builder) {
			// The empty element is never printed
			this.disable();
		}
	};
	
	public Element set() {
		this.value = null;
		return this;
	}
	public Element set(String string) {
		this.value = string;
		return this;
	}
	public Element set(Object...objects) {
		if (objects.length > 0) this.value = "";
		for (Object o : objects) this.value += o.toString();
		return this;
	}
	
	public String get() {
		return this.value;
	}
	
	public boolean isEmpty() {
		return !this.enabled || this.value == null;
	}
	
	public Element enable(boolean enable) { this.enabled = enable; return this; }
	public Element enable() { return this.enable(true); }
	public Element disable() { return this.enable(false); }
	
	public void prepare(SourceBuilder builder) {}
	
	public void write(SourceBuilder builder, Writer writer) throws IOException {
		if (!this.isEmpty()) writer.write(this.value);
	}
}
