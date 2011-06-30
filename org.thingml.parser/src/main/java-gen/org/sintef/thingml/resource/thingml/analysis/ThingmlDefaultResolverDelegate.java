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
package org.sintef.thingml.resource.thingml.analysis;

public class ThingmlDefaultResolverDelegate<ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject> {
	
	private static class StringMatch {
		
		private String exactMatch;
		private String similarMatch;
		
		public StringMatch() {
			super();
		}
		
		public StringMatch(String exactMatch) {
			super();
			this.exactMatch = exactMatch;
		}
		
		public String getExactMatch() {
			return exactMatch;
		}
		
		public void setSimilarMatch(String similarMatch) {
			this.similarMatch = similarMatch;
		}
		
		public String getSimilarMatch() {
			return similarMatch;
		}
		
	}
	
	private static class ReferenceCache implements org.sintef.thingml.resource.thingml.IThingmlReferenceCache, org.eclipse.emf.common.notify.Adapter {
		
		private java.util.Map<org.eclipse.emf.ecore.EClass, java.util.Set<org.eclipse.emf.ecore.EObject>> cache = new java.util.LinkedHashMap<org.eclipse.emf.ecore.EClass, java.util.Set<org.eclipse.emf.ecore.EObject>>();
		private boolean isInitialized;
		private org.eclipse.emf.common.notify.Notifier target;
		
		public org.eclipse.emf.common.notify.Notifier getTarget() {
			return target;
		}
		
		public boolean isAdapterForType(Object arg0) {
			return false;
		}
		
		public void notifyChanged(org.eclipse.emf.common.notify.Notification arg0) {
		}
		
		public void setTarget(org.eclipse.emf.common.notify.Notifier arg0) {
			target = arg0;
		}
		
		public java.util.Set<org.eclipse.emf.ecore.EObject> getObjects(org.eclipse.emf.ecore.EClass type) {
			return cache.get(type);
		}
		
		public void initialize(org.eclipse.emf.ecore.EObject root) {
			if (isInitialized) {
				return;
			}
			put(root);
			java.util.Iterator<org.eclipse.emf.ecore.EObject> it = root.eAllContents();
			while (it.hasNext()) {
				put(it.next());
			}
			isInitialized = true;
		}
		
		private void put(org.eclipse.emf.ecore.EObject object) {
			org.eclipse.emf.ecore.EClass eClass = object.eClass();
			if (!cache.containsKey(eClass)) {
				cache.put(eClass, new java.util.LinkedHashSet<org.eclipse.emf.ecore.EObject>());
			}
			cache.get(eClass).add(object);
		}
		
		public void clear() {
			cache.clear();
			isInitialized = false;
		}
		
	}
	
	public final static String NAME_FEATURE = "name";
	/**
	 * The maximal distance between two identifiers according to the Levenshtein
	 * distance to qualify for a quick fix.
	 */
	public int MAX_DISTANCE = 2;
	
	private boolean enableScoping = true;
	
	/**
	 * This standard implementation searches for objects in the resource, which have
	 * the correct type and a name/id attribute matching the identifier. If no
	 * matching object is found, the identifier is used as URI. If the resource at
	 * this URI has a root element of the correct type, this element is returned.
	 */
	protected void resolve(String identifier, ContainerType container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<ReferenceType> result) {
		try {
			org.eclipse.emf.ecore.EObject root = container;
			if (!enableScoping) {
				root = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.findRootContainer(container);
			}
			while (root != null) {
				boolean continueSearch = tryToResolveIdentifierInObjectTree(identifier, container, root, reference, position, resolveFuzzy, result, !enableScoping);
				if (!continueSearch) {
					return;
				}
				root = root.eContainer();
			}
			boolean continueSearch = tryToResolveIdentifierAsURI(identifier, container, reference, position, resolveFuzzy, result);
			if (continueSearch) {
				java.util.Set<org.eclipse.emf.ecore.EObject> crossReferencedObjectsInOtherResource = findExternalReferences(container);
				for (org.eclipse.emf.ecore.EObject externalObject : crossReferencedObjectsInOtherResource) {
					continueSearch = tryToResolveIdentifierInObjectTree(identifier, container, externalObject, reference, position, resolveFuzzy, result, !enableScoping);
					if (!continueSearch) {
						return;
					}
				}
			}
		} catch (java.lang.RuntimeException rte) {
			// catch exception here to prevent EMF proxy resolution from swallowing it
			rte.printStackTrace();
		}
	}
	
	/**
	 * Returns all EObjects that are referenced by EObjects in the resource that
	 * contains <code>object</code>.
	 */
	private java.util.Set<org.eclipse.emf.ecore.EObject> findExternalReferences(org.eclipse.emf.ecore.EObject object) {
		org.eclipse.emf.ecore.EObject root = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.findRootContainer(object);
		java.util.Set<org.eclipse.emf.ecore.EObject> externalReferences = new java.util.LinkedHashSet<org.eclipse.emf.ecore.EObject>();
		externalReferences.addAll(root.eCrossReferences());
		java.util.Iterator<org.eclipse.emf.ecore.EObject> eAllContents = root.eAllContents();
		while (eAllContents.hasNext()) {
			org.eclipse.emf.ecore.EObject next = eAllContents.next();
			if (next.eResource() != object.eResource()) {
				externalReferences.addAll(next.eCrossReferences());
			}
		}
		return externalReferences;
	}
	
	/**
	 * Searches for objects in the tree of EObjects that is rooted at
	 * <code>root</code>, which have the correct type and a name/id attribute matching
	 * the identifier. This method can be used to quickly implement custom reference
	 * resolvers which require to search in a particular scope for referenced
	 * elements, rather than in the whole resource as done by resolve().
	 */
	protected boolean tryToResolveIdentifierInObjectTree(String identifier, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EObject root, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<ReferenceType> result, boolean checkRootFirst) {
		org.eclipse.emf.ecore.EClass type = reference.getEReferenceType();
		boolean continueSearch;
		if (checkRootFirst) {
			// check whether the root element matches
			continueSearch = checkElement(container, root, reference, position, type, identifier, resolveFuzzy, true, result);
			if (!continueSearch) {
				return false;
			}
		}
		// check the contents
		for (java.util.Iterator<org.eclipse.emf.ecore.EObject> iterator = root.eAllContents(); iterator.hasNext(); ) {
			org.eclipse.emf.ecore.EObject element = iterator.next();
			continueSearch = checkElement(container, element, reference, position, type, identifier, resolveFuzzy, true, result);
			if (!continueSearch) {
				return false;
			}
		}
		// if the root element was already checked, we can return.
		if (checkRootFirst) {
			return true;
		}
		// check whether the root element matches
		continueSearch = checkElement(container, root, reference, position, type, identifier, resolveFuzzy, true, result);
		if (!continueSearch) {
			return false;
		}
		return true;
	}
	
	private boolean tryToResolveIdentifierAsURI(String identifier, ContainerType container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<ReferenceType> result) {
		org.eclipse.emf.ecore.EClass type = reference.getEReferenceType();
		org.eclipse.emf.ecore.resource.Resource resource = container.eResource();
		if (resource != null) {
			org.eclipse.emf.common.util.URI uri = getURI(identifier, resource.getURI());
			if (uri != null) {
				org.eclipse.emf.ecore.EObject element = loadResource(container.eResource().getResourceSet(), uri);
				if (element == null) {
					return true;
				}
				return checkElement(container, element, reference, position, type, identifier, resolveFuzzy, false, result);
			}
		}
		return true;
	}
	
	private boolean checkElement(org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EObject element, org.eclipse.emf.ecore.EReference reference, int position, org.eclipse.emf.ecore.EClass type, String identifier, boolean resolveFuzzy, boolean checkStringWise, org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<ReferenceType> result) {
		if (element.eIsProxy()) {
			return true;
		}
		
		boolean hasCorrectType = hasCorrectType(element, type.getInstanceClass());
		if (!hasCorrectType) {
			return true;
		}
		
		StringMatch match;
		// do not compare string-wise if identifier is a URI
		if (checkStringWise) {
			match = matches(element, identifier, resolveFuzzy);
		} else {
			match = new StringMatch(identifier);
		}
		String exactMatch = match.getExactMatch();
		if (exactMatch == null) {
			String similarMatch = match.getSimilarMatch();
			if (similarMatch != null) {
				org.eclipse.emf.ecore.EObject oldTarget;
				Object value = container.eGet(reference);
				if (value instanceof java.util.List) {
					java.util.List<?> list = (java.util.List<?>) container.eGet(reference);
					oldTarget = (org.eclipse.emf.ecore.EObject) list.get(position);
				} else {
					oldTarget = (org.eclipse.emf.ecore.EObject) container.eGet(reference, false);
				}
				result.addQuickFix(new org.sintef.thingml.resource.thingml.mopp.ThingmlChangeReferenceQuickFix("Replace with " + similarMatch, "IMG_TOOL_FORWARD", container, reference, oldTarget, element));
			}
			return true;
		}
		// we can safely cast 'element' to 'ReferenceType' here, because we've checked the
		// type of 'element' against the type of the reference. unfortunately the compiler
		// does not know that this is sufficient, so we must call cast(), which is not
		// type safe by itself.
		result.addMapping(exactMatch, cast(element));
		if (!resolveFuzzy) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method encapsulates an unchecked cast from EObject to ReferenceType. We
	 * cannot do this cast strictly type safe, because type parameters are erased by
	 * compilation. Thus, an instanceof check cannot be performed at runtime.
	 */
	@SuppressWarnings("unchecked")	
	private ReferenceType cast(org.eclipse.emf.ecore.EObject element) {
		return (ReferenceType) element;
	}
	
	protected String produceDeResolveErrorMessage(org.eclipse.emf.ecore.EObject refObject, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EReference reference, org.sintef.thingml.resource.thingml.IThingmlTextResource resource) {
		String msg = getClass().getSimpleName() + ": " + reference.getEType().getName() + " \"" + refObject.toString() + "\" not de-resolveable";
		return msg;
	}
	
	protected String deResolve(ReferenceType element, ContainerType container, org.eclipse.emf.ecore.EReference reference) {
		return getName(element);
	}
	
	private StringMatch matches(org.eclipse.emf.ecore.EObject element, String identifier, boolean matchFuzzy) {
		// first check for attributes that have set the ID flag to true
		java.util.List<org.eclipse.emf.ecore.EStructuralFeature> features = element.eClass().getEStructuralFeatures();
		for (org.eclipse.emf.ecore.EStructuralFeature feature : features) {
			if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
				org.eclipse.emf.ecore.EAttribute attribute = (org.eclipse.emf.ecore.EAttribute) feature;
				if (attribute.isID()) {
					Object attributeValue = element.eGet(attribute);
					StringMatch match = matches(identifier, attributeValue, matchFuzzy);
					if (match.getExactMatch() != null) {
						return match;
					}
				}
			}
		}
		
		// then check for an attribute that is called 'name'
		org.eclipse.emf.ecore.EStructuralFeature nameAttr = element.eClass().getEStructuralFeature(NAME_FEATURE);
		if (nameAttr instanceof org.eclipse.emf.ecore.EAttribute) {
			Object attributeValue = element.eGet(nameAttr);
			return matches(identifier, attributeValue, matchFuzzy);
		} else {
			// try any other string attribute found
			for (org.eclipse.emf.ecore.EAttribute stringAttribute : element.eClass().getEAllAttributes()) {
				if ("java.lang.String".equals(stringAttribute.getEType().getInstanceClassName())) {
					Object attributeValue = element.eGet(stringAttribute);
					StringMatch match = matches(identifier, attributeValue, matchFuzzy);
					if (match.getExactMatch() != null) {
						return match;
					}
				}
			}
			
			for (org.eclipse.emf.ecore.EOperation o : element.eClass().getEAllOperations()) {
				if (o.getName().toLowerCase().endsWith(NAME_FEATURE) && o.getEParameters().size() == 0 ) {
					String result = (String) org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.invokeOperation(element, o);
					StringMatch match = matches(identifier, result, matchFuzzy);
					if (match.getExactMatch() != null) {
						return match;
					}
				}
			}
		}
		return new StringMatch();
	}
	
	private StringMatch matches(String identifier, Object attributeValue, boolean matchFuzzy) {
		if (attributeValue != null && attributeValue instanceof String) {
			String name = (String) attributeValue;
			if (name.equals(identifier) || matchFuzzy) {
				return new StringMatch(name);
			}
			if (isSimilar(name, identifier)) {
				StringMatch match = new StringMatch();
				match.setSimilarMatch(name);
				return match;
			}
		}
		return new StringMatch();
	}
	
	private String getName(ReferenceType element) {
		String deresolvedReference = null;
		if (element instanceof org.eclipse.emf.ecore.EObject) {
			org.eclipse.emf.ecore.EObject eObjectToDeResolve = (org.eclipse.emf.ecore.EObject) element;
			if (eObjectToDeResolve.eIsProxy()) {
				deresolvedReference = ((org.eclipse.emf.ecore.InternalEObject) eObjectToDeResolve).eProxyURI().fragment();
				if (deresolvedReference != null && deresolvedReference.startsWith(org.sintef.thingml.resource.thingml.IThingmlContextDependentURIFragment.INTERNAL_URI_FRAGMENT_PREFIX)) {
					deresolvedReference = deresolvedReference.substring(org.sintef.thingml.resource.thingml.IThingmlContextDependentURIFragment.INTERNAL_URI_FRAGMENT_PREFIX.length());
					deresolvedReference = deresolvedReference.substring(deresolvedReference.indexOf("_") + 1);
				}
			}
		}
		if (deresolvedReference != null) {
			return deresolvedReference;
		}
		org.eclipse.emf.ecore.EStructuralFeature nameAttr = element.eClass().getEStructuralFeature(NAME_FEATURE);
		if (nameAttr instanceof org.eclipse.emf.ecore.EAttribute) {
			return (String) element.eGet(nameAttr);
		} else {
			// try any other string attribute found
			for (org.eclipse.emf.ecore.EAttribute strAttribute : element.eClass().getEAllAttributes()) {
				if (!strAttribute.isMany() &&				strAttribute.getEType().getInstanceClassName().equals("String")) {
					return (String) element.eGet(strAttribute);
				}
			}
			for (org.eclipse.emf.ecore.EOperation o : element.eClass().getEAllOperations()) {
				if (o.getName().toLowerCase().endsWith(NAME_FEATURE) && o.getEParameters().size() == 0 ) {
					String result = (String) org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.invokeOperation(element, o);
					if (result != null) {
						return result;
					}
				}
			}
		}
		return null;
	}
	
	private boolean hasCorrectType(org.eclipse.emf.ecore.EObject element, Class<?> expectedTypeClass) {
		return expectedTypeClass.isInstance(element);
	}
	
	private org.eclipse.emf.ecore.EObject loadResource(org.eclipse.emf.ecore.resource.ResourceSet resourceSet, org.eclipse.emf.common.util.URI uri) {
		try {
			org.eclipse.emf.ecore.resource.Resource resource = resourceSet.getResource(uri, true);
			org.eclipse.emf.common.util.EList<org.eclipse.emf.ecore.EObject> contents = resource.getContents();
			if (contents.size() > 0) {
				return contents.get(0);
			}
		} catch (java.lang.RuntimeException re) {
			// do nothing here. if no resource can be loaded the uriString is probably not a
			// valid resource URI
		}
		return null;
	}
	
	private org.eclipse.emf.common.util.URI getURI(String identifier, org.eclipse.emf.common.util.URI baseURI) {
		if (identifier == null) {
			return null;
		}
		try {
			org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(identifier);
			if (uri.isRelative()) {
				uri = uri.resolve(baseURI);
			}
			return uri;
		} catch (java.lang.IllegalArgumentException iae) {
			// the identifier string is not a valid URI
			return null;
		}
	}
	
	protected org.sintef.thingml.resource.thingml.IThingmlReferenceCache getCache(org.eclipse.emf.ecore.EObject object) {
		org.eclipse.emf.ecore.EObject root = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.findRootContainer(object);
		java.util.List<org.eclipse.emf.common.notify.Adapter> eAdapters = root.eAdapters();
		for (org.eclipse.emf.common.notify.Adapter adapter : eAdapters) {
			if (adapter instanceof ReferenceCache) {
				ReferenceCache cache = (ReferenceCache) adapter;
				return cache;
			}
		}
		ReferenceCache cache = new ReferenceCache();
		cache.initialize(root);
		root.eAdapters().add(cache);
		return cache;
	}
	
	public void setEnableScoping(boolean enableScoping) {
		this.enableScoping = enableScoping;
	}
	
	public boolean getEnableScoping() {
		return enableScoping;
	}
	
	private boolean isSimilar(String identifier, Object attributeValue) {
		if (attributeValue != null && attributeValue instanceof String) {
			String name = (String) attributeValue;
			if (org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.computeLevenshteinDistance(identifier, name) <= MAX_DISTANCE) {
				return true;
			}
		}
		return false;
	}
	
}
