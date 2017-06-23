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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MultipleTest {
	
	private Integer n;
	
	public MultipleTest(String name, Integer n) {
		this.n = n;
	}

	@Test
	public void test() {
		assertTrue("Number is 4", this.n == 4);
	}
	
	@Parameters(name="{0}")
	public static Collection<Object[]> data() {
		Collection<Object[]> tests = new ArrayList<Object[]>();
		
		// Add the numbers we want to test
		tests.add(new Object[]{ "Number 1", 1 });
		tests.add(new Object[]{ "Number 2", 2 });
		tests.add(new Object[]{ "Number 3", 3 });
		tests.add(new Object[]{ "Number 4", 4 });
		tests.add(new Object[]{ "Number 5", 5 });
		tests.add(new Object[]{ "Number 6", "Hello" });
		
		return tests;
	}

}
