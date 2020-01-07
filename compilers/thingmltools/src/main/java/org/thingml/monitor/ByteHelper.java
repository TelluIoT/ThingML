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
package org.thingml.monitor;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.ThingMLFactory;

public class ByteHelper {

	private static byte thingID = 0;
	private static byte functionID = 0;
	private static byte messageID = 0;
	
	public static byte thingID() {return thingID++;} 
	public static byte functionID() {return functionID++;}
	public static byte messageID() {return messageID++;}
	
	public static void reset() {
		thingID = 0;
		functionID = 0;
		messageID = 0;
	}
	
}
