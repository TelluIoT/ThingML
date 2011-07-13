/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlTokenStyleInformationProvider {
	
	public org.sintef.thingml.resource.thingml.IThingmlTokenStyle getDefaultTokenStyle(String tokenName) {
		if ("TEXT".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x00, 0x00, 0x00}, null, false, false, false, false);
		}
		if ("T_MULTICAST".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("T_READONLY".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("SL_COMMENT".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x00, 0xaa, 0x33}, null, false, false, false, false);
		}
		if ("ML_COMMENT".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x00, 0xaa, 0x33}, null, false, false, false, false);
		}
		if ("ANNOTATION".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x00, 0x55, 0xbb}, null, true, false, false, false);
		}
		if ("STRING_LITERAL".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x00, 0x55, 0xbb}, null, false, false, false, false);
		}
		if ("thing".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("datatype".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("enumeration".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("T_ASPECT".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("T_SINGLETON".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("sends".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("receives".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("STRING_EXT".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x00, 0x55, 0xbb}, null, false, false, false, false);
		}
		if ("state".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("composite".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("statechart".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("event".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("guard".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("action".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("on".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("entry".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("exit".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("transition".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("init".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("keeps".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("T_HISTORY".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("port".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("import".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x7F, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("->".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0xCC, 0x80, 0x00}, null, true, false, false, false);
		}
		if ("(".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x44, 0x44, 0x44}, null, true, false, false, false);
		}
		if (")".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x44, 0x44, 0x44}, null, true, false, false, false);
		}
		if ("{".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x44, 0x44, 0x44}, null, true, false, false, false);
		}
		if ("}".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x44, 0x44, 0x44}, null, true, false, false, false);
		}
		if ("[".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x44, 0x44, 0x44}, null, true, false, false, false);
		}
		if ("]".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x44, 0x44, 0x44}, null, true, false, false, false);
		}
		if ("message".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("includes".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("required".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("provided".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("property".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("region".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("internal".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("set".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("configuration".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("instance".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("connector".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("do".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("end".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("if".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("while".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("print".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("error".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("or".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("and".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("not".equals(tokenName)) {
			return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		return null;
	}
	
}
