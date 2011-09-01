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
package org.sintef.thingml.resource.thingml.analysis;

/**
 * A default implementation for token resolvers. Generated token resolvers
 * delegate calls to this class to convert text (i.e., tokens) to Java objects.
 * This default implementation tries to perform this conversion using the
 * EMF-based data type serialization mechanism using
 * org.eclipse.emf.ecore.util.EcoreUtil.createFromString().
 * 
 * In addition, enumeration literals are converted to the respective literal
 * object, if the text (i.e., the token) matches the literal.
 * 
 * For boolean attributes the token is considered to represent <code>true</code>
 * if it matches the name of the attribute. Attributes that have names like
 * <code>isFoo</code> are also interpret as <code>true</code> if the text is
 * <code>foo</code>.
 * 
 * The behavior of this resolving can be customized by either changing the
 * generated token resolver classes or by using custom EMF data type converters.
 */
public class ThingmlDefaultTokenResolver implements org.sintef.thingml.resource.thingml.IThingmlTokenResolver {
	
	private java.util.Map<?, ?> options;
	private boolean escapeKeywords;
	
	/**
	 * This constructor is used by token resolvers that were generated before EMFText
	 * 1.4.0. It does not enable automatic escaping and unescaping of keywords.
	 */
	public ThingmlDefaultTokenResolver() {
		this(false);
	}
	
	/**
	 * This constructor is used by token resolvers that were generated with EMFText
	 * 1.4.0 and later releases. It can optionally enable automatic escaping and
	 * unescaping of keywords.
	 */
	public ThingmlDefaultTokenResolver(boolean escapeKeywords) {
		super();
		this.escapeKeywords = escapeKeywords;
	}
	
	public void resolve(String lexem, org.eclipse.emf.ecore.EStructuralFeature feature, org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result) {
		resolve(lexem, feature, result, null, null, null);
	}
	
	public void resolve(String lexem, org.eclipse.emf.ecore.EStructuralFeature feature, org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result, String suffix, String prefix, String escapeCharacter) {
		// Step 1: unescape keywords if required
		if (escapeKeywords && lexem.startsWith("_")) {
			for (String keyword : org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.INSTANCE.getKeywords()) {
				if (lexem.endsWith(keyword)) {
					String keywordPrefix = lexem.substring(0, lexem.length() - keyword.length());
					if (keywordPrefix.matches("_+")) {
						lexem = lexem.substring(1);
						break;
					}
				}
			}
		}
		
		// Step 2: remove prefix, suffix and unescape escaped suffixes
		// Step 2a: remove prefix
		if (prefix != null) {
			int count = prefix.length();
			lexem = lexem.substring(count);
		}
		// Step 2b: remove suffix
		if (suffix != null) {
			int count = suffix.length();
			lexem = lexem.substring(0, lexem.length() - count );
			// take care of the escape character (may be null)
			// Step 2c: replaced escaped suffixes and escaped escape sequences
			if (escapeCharacter != null) {
				lexem = lexem.replace(escapeCharacter + suffix, suffix);
				lexem = lexem.replace(escapeCharacter + escapeCharacter, escapeCharacter);
			}
		}
		
		// Step 3: convert text to Java object
		if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
			org.eclipse.emf.ecore.EClassifier featureType = feature.getEType();
			if (featureType instanceof org.eclipse.emf.ecore.EEnum) {
				org.eclipse.emf.ecore.EEnumLiteral literal = ((org.eclipse.emf.ecore.EEnum) featureType).getEEnumLiteralByLiteral(lexem);
				if (literal != null) {
					result.setResolvedToken(literal.getInstance());
					return;
				} else {
					result.setErrorMessage("Could not map lexem '" + lexem + "' to enum '" + featureType.getName() + "'.");
					return;
				}
			} else if (featureType instanceof org.eclipse.emf.ecore.EDataType) {
				try {
					result.setResolvedToken(org.eclipse.emf.ecore.util.EcoreUtil.createFromString((org.eclipse.emf.ecore.EDataType) featureType, lexem));
				} catch (Exception e) {
					result.setErrorMessage("Could not convert '" + lexem + "' to '" + featureType.getName() + "'.");
				}
				String typeName = featureType.getInstanceClassName();
				if (typeName.equals("boolean") || java.lang.Boolean.class.getName().equals(typeName)) {
					String featureName = feature.getName();
					boolean featureNameMatchesLexem = featureName.equals(lexem);
					if (featureNameMatchesLexem) {
						result.setResolvedToken(true);
						return;
					}
					if (featureName.length() > 2 && featureName.startsWith("is")) {
						if ((featureName.substring(2, 3).toLowerCase() + featureName.substring(3)).equals(lexem)) {
							result.setResolvedToken(true);
							return;
						}
					}
					if (Boolean.parseBoolean(lexem)) {
						result.setResolvedToken(true);
						return;
					}
				}
			} else {
				assert false;
			}
		} else {
			result.setResolvedToken(lexem);
			return;
		}
	}
	
	public String deResolve(Object value, org.eclipse.emf.ecore.EStructuralFeature feature, org.eclipse.emf.ecore.EObject container) {
		return deResolve(value, feature, container, null, null, null);
	}
	
	public String deResolve(Object value, org.eclipse.emf.ecore.EStructuralFeature feature, org.eclipse.emf.ecore.EObject container, String prefix, String suffix, String escapeCharacter) {
		// Step 1: convert Java object to text
		String result = null;
		if (value != null) {
			result = value.toString();
		}
		
		// Step 2: escape suffixes, add prefix and suffix
		// Step 2a: escaped suffix
		if (suffix != null) {
			// take care of the escape character (may be null)
			if (escapeCharacter != null) {
				result = result.replace(escapeCharacter, escapeCharacter + escapeCharacter);
				result = result.replace(suffix, escapeCharacter + suffix);
			}
			// Step 2b: append suffix
			result += suffix;
		}
		// Step 2c: prepend prefix
		if (prefix != null) {
			result = prefix + result;
		}
		
		// Step 3: escape keywords if required
		if (escapeKeywords) {
			// Escape keywords if required
			for (String keyword : org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider.INSTANCE.getKeywords()) {
				if (result.endsWith(keyword)) {
					String keywordPrefix = result.substring(0, result.length() - keyword.length());
					if (keywordPrefix.matches("_*")) {
						result = "_" + result;
						break;
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * This method can be used to disable automatic escaping and unescaping of tokens
	 * that match keywords of the syntax.
	 */
	public void setEscapeKeywords(boolean escapeKeywords) {
		this.escapeKeywords = escapeKeywords;
	}
	
	public void setOptions(java.util.Map<?, ?> options) {
		this.options = options;
	}
	
	public java.util.Map<?, ?> getOptions() {
		return options;
	}
	
}
