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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Summary extends EnumMap<Result, Integer> {
	private static final long serialVersionUID = 1L;

	public Summary() {
		super(Result.class);
	}
	
	public Summary increment(Result result, Integer number) {
		if (!containsKey(result)) put(result, number);
		else put(result, get(result)+number);
		return this;
	}
	
	public Summary increment(Result result) {
		return increment(result, 1);
	}
	
	public Summary merge(Summary summary) {
		for (Entry<Result, Integer> entry : summary.entrySet())
			increment(entry.getKey(), entry.getValue());
		return this;
	}
	
	public Summary merge(Collection<Summary> summaries) {
		for (Summary summary : summaries)
			merge(summary);
		return this;
	}
	
	public Summary join(Result into, Result... others) {
		for (Result other : others) {
			if (containsKey(other)) {
				increment(into, get(other));
				remove(other);
			}
		}
		return this;
	}
	
	public Summary simplify() {
		Summary result = new Summary();
		for (Entry<Result,Integer> entry : this.entrySet())
			result.increment(entry.getKey().simplify(), entry.getValue());
		return result;
	}
	
	public String toJSON() {
		List<String> entries = new ArrayList<String>();
		for (Entry<Result,Integer> result : sorted(this)) {
			entries.add(result.getKey().toJSON(result.getValue()));
		}		
		return "["+String.join(", ", entries)+"]";
	}
	
	public List<Entry<Result,Integer>> sorted() {
		return sorted(this);
	}
	
	public static Map<String,Summary> mergeCompilerSummaries(Map<String,Summary> into, Map<String,Summary> other) {
		for (Entry<String,Summary> compiler : other.entrySet()) {
			if (into.containsKey(compiler.getKey())) into.put(compiler.getKey(), into.get(compiler.getKey()).merge(compiler.getValue()));
			else into.put(compiler.getKey(), compiler.getValue());
		}
		return into;
	}
	
	@SafeVarargs
	public static Map<String,Summary> mergeCompilerSummaries(Map<String,Summary> into, Map<String,Summary>... others) {
		for (Map<String,Summary> other : others)
			mergeCompilerSummaries(into, other);
		return into;
	}
	
	public static List<Entry<Result,Integer>> sorted(Summary summary) {
		List<Entry<Result,Integer>> sorted = new ArrayList<Entry<Result,Integer>>();
		sorted.addAll(summary.entrySet());
		sorted.sort(new Comparator<Entry<Result,Integer>>() {
			@Override
			public int compare(Entry<Result, Integer> o1, Entry<Result, Integer> o2) {
				return Result.compare(o1.getKey(), o2.getKey());
			}
		});
		return sorted;
	}
}
