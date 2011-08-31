/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

/**
 * A factory for ContextDependentURIFragments. Given a feasible reference
 * resolver, this factory returns a matching fragment that used the resolver to
 * resolver proxy objects.
 * 
 * @param <ContainerType> the type of the class containing the reference to be
 * resolved
 * @param <ReferenceType> the type of the reference to be resolved
 */
public class ThingmlContextDependentURIFragmentFactory<ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject>  implements org.sintef.thingml.resource.thingml.IThingmlContextDependentURIFragmentFactory<ContainerType, ReferenceType> {
	
	private final org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<ContainerType, ReferenceType> resolver;
	
	public ThingmlContextDependentURIFragmentFactory(org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<ContainerType, ReferenceType> resolver) {
		this.resolver = resolver;
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlContextDependentURIFragment<?> create(String identifier, ContainerType container, org.eclipse.emf.ecore.EReference reference, int positionInReference, org.eclipse.emf.ecore.EObject proxy) {
		
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlContextDependentURIFragment<ContainerType, ReferenceType>(identifier, container, reference, positionInReference, proxy) {
			public org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<ContainerType, ReferenceType> getResolver() {
				return resolver;
			}
		};
	}
}
