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

public enum Result {
	SUCCESS("Success", 0),
	FAILURE("Failure", 2),
	LOADMODELERROR("Load model error", 3),
	CHECKERERROR("Checker error", 4),
	OUTPUTERROR("Output error", 5),
	COMPILATIONERROR("Compilation error",6),
	EXECUTIONERROR("Execution error",7),
	TIMEOUT("Timout",8),
	SKIPPED("Skipped",99),
	UNKNOWN("Unknown",100);
	
	private String asString;
	private int ordering;
	private Result(String asString, int ordering) {
		this.asString = asString;
		this.ordering = ordering;
	}
	
	public Result simplify() {
		switch(this) {
			case SUCCESS:
				return SUCCESS;
			case FAILURE:
			case LOADMODELERROR:
			case CHECKERERROR:
			case OUTPUTERROR:
			case COMPILATIONERROR:
			case EXECUTIONERROR:
			case TIMEOUT:
				return FAILURE;
			case SKIPPED:
				return SKIPPED;
			case UNKNOWN:
			default:
				return UNKNOWN;
		}
	}
	
	@Override
	public String toString() {
		return asString;
	}
	
	public String toJSON(Integer number) {
		return "{\"name\":\""+asString+"\", \"count\":"+number+", \"color\": \""+toColor()+"\"}";
	}
	
	public String toIcon() {
		switch (this.simplify()) {
			case FAILURE:
				return asString.substring(0, 1);
			default:
				return "-";
		}
	}
	
	public String toColor() {
		switch (this.simplify()) {
			case SUCCESS:
				return "#5cb85c";
			case FAILURE:
				return "#d9534f";
			case SKIPPED:
				return "#777777";
			default:
				return "#f0ad4e";
		}
	}
	
	public String toTextColor() {
		switch (this.simplify()) {
			case SUCCESS:
				return "#5cb85c";
			case SKIPPED:
				return "#777777";
			default:
				return "#ffffff";
		}
	}
	
	public static int compare(Result r1, Result r2) {
		return Integer.compare(r1.ordering, r2.ordering);
	}
	
	public static Result fromFailure(String failure) {
		if (failure == null || failure.equals("null"))
			return SUCCESS;
		if (failure.equals("skipped"))
			return SKIPPED;
		if (failure.equals("org.thingml.testing.errors.ThingMLLoadModelError"))
			return LOADMODELERROR;
		if (failure.equals("org.thingml.testing.errors.ThingMLCheckerError"))
			return CHECKERERROR;
		if (failure.equals("org.thingml.testing.errors.ThingMLOutputError"))
			return OUTPUTERROR;
		if (failure.equals("org.thingml.testing.errors.ThingMLCompilationError"))
			return COMPILATIONERROR;
		if (failure.equals("org.thingml.testing.errors.ThingMLExecutionError"))
			return EXECUTIONERROR;
		if (failure.equals("org.thingml.testing.errors.ThingMLTimeoutError"))
			return TIMEOUT;
		
		return UNKNOWN;
	}
}
