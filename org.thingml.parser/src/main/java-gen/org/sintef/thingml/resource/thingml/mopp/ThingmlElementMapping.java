/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

/**
 * A basic implementation of the
 * org.sintef.thingml.resource.thingml.IThingmlElementMapping interface.
 * 
 * @param <ReferenceType> the type of the reference that can be mapped to
 */
public class ThingmlElementMapping<ReferenceType> implements org.sintef.thingml.resource.thingml.IThingmlElementMapping<ReferenceType> {
	
	private final ReferenceType target;
	private String identifier;
	private String warning;
	
	public ThingmlElementMapping(String identifier, ReferenceType target, String warning) {
		super();
		this.target = target;
		this.identifier = identifier;
		this.warning = warning;
	}
	
	public ReferenceType getTargetElement() {
		return target;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getWarning() {
		return warning;
	}
	
}
