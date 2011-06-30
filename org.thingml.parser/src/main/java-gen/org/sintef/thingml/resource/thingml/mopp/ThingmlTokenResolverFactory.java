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
/**
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

/**
 * The ThingmlTokenResolverFactory class provides access to all generated token
 * resolvers. By giving the name of a defined token, the corresponding resolve can
 * be obtained. Despite the fact that this class is called TokenResolverFactory is
 * does NOT create new token resolvers whenever a client calls methods to obtain a
 * resolver. Rather, this class maintains a map of all resolvers and creates each
 * resolver at most once.
 */
public class ThingmlTokenResolverFactory implements org.sintef.thingml.resource.thingml.IThingmlTokenResolverFactory {
	
	private java.util.Map<String, org.sintef.thingml.resource.thingml.IThingmlTokenResolver> tokenName2TokenResolver;
	private java.util.Map<String, org.sintef.thingml.resource.thingml.IThingmlTokenResolver> featureName2CollectInTokenResolver;
	private static org.sintef.thingml.resource.thingml.IThingmlTokenResolver defaultResolver = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultTokenResolver();
	
	public ThingmlTokenResolverFactory() {
		tokenName2TokenResolver = new java.util.LinkedHashMap<String, org.sintef.thingml.resource.thingml.IThingmlTokenResolver>();
		featureName2CollectInTokenResolver = new java.util.LinkedHashMap<String, org.sintef.thingml.resource.thingml.IThingmlTokenResolver>();
		registerTokenResolver("ANNOTATION", new org.sintef.thingml.resource.thingml.analysis.ThingmlANNOTATIONTokenResolver());
		registerTokenResolver("BOOLEAN_LITERAL", new org.sintef.thingml.resource.thingml.analysis.ThingmlBOOLEAN_LITERALTokenResolver());
		registerTokenResolver("INTEGER_LITERAL", new org.sintef.thingml.resource.thingml.analysis.ThingmlINTEGER_LITERALTokenResolver());
		registerTokenResolver("STRING_LITERAL", new org.sintef.thingml.resource.thingml.analysis.ThingmlSTRING_LITERALTokenResolver());
		registerTokenResolver("STRING_EXT", new org.sintef.thingml.resource.thingml.analysis.ThingmlSTRING_EXTTokenResolver());
		registerTokenResolver("T_READONLY", new org.sintef.thingml.resource.thingml.analysis.ThingmlT_READONLYTokenResolver());
		registerTokenResolver("T_ASPECT", new org.sintef.thingml.resource.thingml.analysis.ThingmlT_ASPECTTokenResolver());
		registerTokenResolver("T_HISTORY", new org.sintef.thingml.resource.thingml.analysis.ThingmlT_HISTORYTokenResolver());
		registerTokenResolver("TEXT", new org.sintef.thingml.resource.thingml.analysis.ThingmlTEXTTokenResolver());
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTokenResolver createTokenResolver(String tokenName) {
		return internalCreateResolver(tokenName2TokenResolver, tokenName);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTokenResolver createCollectInTokenResolver(String featureName) {
		return internalCreateResolver(featureName2CollectInTokenResolver, featureName);
	}
	
	protected boolean registerTokenResolver(String tokenName, org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver){
		return internalRegisterTokenResolver(tokenName2TokenResolver, tokenName, resolver);
	}
	
	protected boolean registerCollectInTokenResolver(String featureName, org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver){
		return internalRegisterTokenResolver(featureName2CollectInTokenResolver, featureName, resolver);
	}
	
	protected org.sintef.thingml.resource.thingml.IThingmlTokenResolver deRegisterTokenResolver(String tokenName){
		return tokenName2TokenResolver.remove(tokenName);
	}
	
	private org.sintef.thingml.resource.thingml.IThingmlTokenResolver internalCreateResolver(java.util.Map<String, org.sintef.thingml.resource.thingml.IThingmlTokenResolver> resolverMap, String key) {
		if (resolverMap.containsKey(key)){
			return resolverMap.get(key);
		} else {
			return defaultResolver;
		}
	}
	
	private boolean internalRegisterTokenResolver(java.util.Map<String, org.sintef.thingml.resource.thingml.IThingmlTokenResolver> resolverMap, String key, org.sintef.thingml.resource.thingml.IThingmlTokenResolver resolver) {
		if (!resolverMap.containsKey(key)) {
			resolverMap.put(key,resolver);
			return true;
		}
		return false;
	}
	
}
