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
import java.io.PrintStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Deprecated
public class Section extends Element {
	protected Section parent;
	protected String name;
	protected LinkedList<Element> children = new LinkedList<Element>();
	
	protected boolean lines = false;
	protected String before = null;
	protected String after = null;
	protected int surroundMin = 0;
	protected String delimiter = null;
	protected boolean comment = false;
	protected int indent = 0;
	
	protected Section(Section parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	public static Section Orphan(String name) {
		return new Section(null, name);
	}
	
	public void cloneInto(Section other) {
		other.parent = this.parent;
		other.name = this.name;
		other.children.addAll(this.children);
		other.lines = this.lines;
		other.before = this.before;
		other.after = this.after;
		other.surroundMin = this.surroundMin;
		other.delimiter = this.delimiter;
		other.comment = this.comment;
		other.indent = this.indent;
		if (this.parent != null) this.parent.replace(this, other);
	}
	
	public Section lines(boolean lines) { this.lines = lines; return this; }
	public Section lines() { return this.lines(true); }
	
	public Section surroundWith(String before, String after, int surroundMin) {
		this.before = before;
		this.after = after;
		this.surroundMin = surroundMin;
		return this;
	}
	public Section surroundWith(String before, String after) {
		return this.surroundWith(before, after, 0);
	}
	
	public Section joinWith(String delimiter) { this.delimiter = delimiter; return this; }
	
	public Section comment(boolean comment) { this.comment = comment; return this; }
	public Section comment() { return this.comment(true); }
	
	public Section indent(int add) { this.indent = add; return this; }
	public Section indent() { return this.indent(1); }
	
	public int size() { return this.children.size(); }
	
	@Override
	public boolean isEmpty() {
		return (super.isEmpty() && this.children.isEmpty() && !(this.surroundMin <= 0 && (this.before != null || this.after != null))) || !this.enabled;
	}
	
	public Section clear() { this.children.clear(); this.set(); return this; }
	
	public Element replace(Element replace, Element with) {
		int index = this.children.indexOf(replace);
		if (index >= 0) {
			this.children.set(index, with);
			if (replace instanceof Section) ((Section)replace).parent = null;
			this.perhapsOwn(with);
		}
		return with;
	}
	
	protected void perhapsOwn(Element child) {
		if (child instanceof Section) {
			Section section = (Section)child;
			if (section.parent != null && section.parent != this) section.parent.children.remove(section);
			section.parent = this;
		}
	}
	
	/* -- Methods to add elements -- */
	public Section before(Element before) { parent.addBefore(before, this); return this; };
	public Section before(String before) { return this.before(new Element(before)); }
	public Section before(Object...before) { return this.before(new Element(before)); }
	
	public Section after(Element after) { parent.addAfter(after, this); return this; };
	public Section after(String after) { return this.after(new Element(after)); }
	public Section after(Object...after) { return this.after(new Element(after)); }
	
	public Section prepend(Element prepend) { this.perhapsOwn(prepend); this.children.addFirst(prepend); return this; };
	public Section prepend(String prepend) { return this.prepend(new Element(prepend)); }
	public Section prepend(Object...prepend) { return this.prepend(new Element(prepend)); }
	
	public Section append(Element append) { this.perhapsOwn(append); this.children.addLast(append); return this; };
	public Section append(String append) { return this.append(new Element(append)); }
	public Section append(Object...append) { return this.append(new Element(append)); }
	
	public Section prependSection(String name) {
		Section newSection = new Section(this, name);
		this.prepend(newSection);
		return newSection;
	}
	public Section appendSection(String name) {
		Section newSection = new Section(this, name);
		this.append(newSection);
		return newSection;
	}
	public Section section(String name) { return this.appendSection(name); }
	
	public Section addBefore(Element add, Element before) {
		int index = this.children.indexOf(before);
		if (index >= 0) {
			this.children.add(index, add);
			this.perhapsOwn(add);
		}
		return this;
	}
	public Section addAfter(Element add, Element after) {
		int index = this.children.indexOf(after);
		if (index >= 0) {
			this.children.add(index+1, add);
			this.perhapsOwn(add);
		}
		return this;
	}
	
	/* --- Some specialised helpers --- */
	public Section comment(String comment) {
		this.appendSection("comment").comment().append(comment);
		return this;
	}
	
	public StringBuilderSection stringbuilderSection(String name) {
		StringBuilderSection newSection = new StringBuilderSection(this, name);
		this.append(newSection);
		return newSection;
	}
	public StringBuilder stringbuilder(String name) {
		return this.stringbuilderSection(name).stringbuilder();
	}
	
	/* --- Tree traversal helpers --- */
	public List<Section> findAll(String ...path) {
		List<Section> result = new LinkedList<Section>();
		if (path.length > 0) {
			Pattern current = Pattern.compile(path[0]);
			String[] next = Arrays.copyOfRange(path, 1, path.length);
			for (Element el : children) {
				if (el instanceof Section) {
					Section child = (Section)el;
					if (current.matcher(child.name).matches()) {
						if (next.length == 0)
							result.add(child);
						else
							result.addAll(child.findAll(next));
					}
				}
			}
		}
		return result;
	}
	public Section find(String ...path) {
		List<Section> all = findAll(path);
		if (all.isEmpty()) return null;
		else return all.get(0);
	}
	
	private void printTree(PrintStream out, String indent) {
		out.print(indent);
		out.print("> ");
		out.println(name);
		for (Element el : children)
			if (el instanceof Section)
				((Section)el).printTree(out, indent+"\t");
	}
	public void printTree(PrintStream out) {
		out.println("------------------------------------ SourceBuilder Section Tree ------------------------------------");
		printTree(out, "");
		out.println("------------------------------------ SourceBuilder Section Tree ------------------------------------");
	}
	public void printTree() {
		printTree(System.out);
	}
	
	/* --- The actual writing --- */
	protected void writeLineBefore(SourceBuilder builder, Writer writer) throws IOException {
		for (int i = 0; i < builder.currentTabs; ++i) writer.write(builder.TAB);
		if (this.comment) writer.write(builder.COMMENT_PREPEND);
	}
	protected void writeLineAfter(SourceBuilder builder, Writer writer) throws IOException {
		if (this.comment) writer.write(builder.COMMENT_APPEND);
		writer.write(builder.NEWLINE);	
	}
	
	@Override
	public void prepare(SourceBuilder builder) {
		for (Element element : this.children) {
			element.prepare(builder);
			if (element instanceof Section) {
				Section section = (Section)element;
				if (!this.lines) section.lines(false);
			}
		}
	}
	
	@Override
	public void write(SourceBuilder builder, Writer writer) throws IOException {
		if (this.isEmpty()) return;
		
		builder.currentTabs += this.indent;
		
		int numElems = this.value != null ? 1 : this.children.size();
		if (this.lines) {
			if (!this.comment && this.before != null && numElems >= this.surroundMin) {
				writeLineBefore(builder, writer);
				writer.write(this.before);
				writeLineAfter(builder, writer);
			}
			
			if (this.value == null) {
				boolean first = true;
				for (Element element : this.children) {
					if (element.value != null) {
						writeLineBefore(builder, writer);
						element.write(builder, writer);
						if (!first && this.delimiter != null) writer.write(this.delimiter);
						first = false;
						writeLineAfter(builder, writer);
					} else if (element instanceof Section) {
						Section subsection = (Section)element;
						if (!subsection.isEmpty()) {
							if (subsection.lines) {
								subsection.write(builder, writer);
							} else {
								writeLineBefore(builder, writer);
								subsection.write(builder, writer);
								writeLineAfter(builder, writer);
							}
							if (!first && this.delimiter != null) writer.write(this.delimiter);
							first = false;
						}
					}
				}
			} else {
				writeLineBefore(builder, writer);
				writer.write(this.value);
				writeLineAfter(builder, writer);
			}
			
			if (!this.comment && this.after != null && numElems >= this.surroundMin) {
				writeLineBefore(builder, writer);
				writer.write(this.after);
				writeLineAfter(builder, writer);
			}
		} else {
			if (this.comment) {
				writer.write(builder.COMMENT_PREPEND);
				if (this.value == null) {
					for (Element element : this.children)
						element.write(builder, writer);
				} else {
					writer.write(this.value);
				}
				writer.write(builder.COMMENT_APPEND);
			} else {
				if (this.before != null && numElems >= this.surroundMin)
					writer.write(this.before);
				if (this.value == null) {
					boolean first = true;
					for (Element element : this.children) {
						if (!element.isEmpty()) {
							if (!first && this.delimiter != null) writer.write(this.delimiter);
							first = false;
							element.write(builder, writer);
						}
					}
				} else {
					writer.write(this.value);
				}
				if (this.after != null && numElems >= this.surroundMin)
					writer.write(this.after);
			}
		}
		
		builder.currentTabs -= this.indent;
	}
}
