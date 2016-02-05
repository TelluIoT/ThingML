/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlUIMetaInformation extends org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation {
	
	public org.sintef.thingml.resource.thingml.IThingmlHoverTextProvider getHoverTextProvider() {
		return new org.sintef.thingml.resource.thingml.ui.ThingmlHoverTextProvider();
	}
	
	public org.sintef.thingml.resource.thingml.ui.ThingmlImageProvider getImageProvider() {
		return org.sintef.thingml.resource.thingml.ui.ThingmlImageProvider.INSTANCE;
	}
	
	public org.sintef.thingml.resource.thingml.ui.ThingmlColorManager createColorManager() {
		return new org.sintef.thingml.resource.thingml.ui.ThingmlColorManager();
	}
	
	/**
	 * @deprecated this method is only provided to preserve API compatibility. Use
	 * createTokenScanner(org.sintef.thingml.resource.thingml.IThingmlTextResource,
	 * org.sintef.thingml.resource.thingml.ui.ThingmlColorManager) instead.
	 */
	public org.sintef.thingml.resource.thingml.ui.ThingmlTokenScanner createTokenScanner(org.sintef.thingml.resource.thingml.ui.ThingmlColorManager colorManager) {
		return createTokenScanner(null, colorManager);
	}
	
	public org.sintef.thingml.resource.thingml.ui.ThingmlTokenScanner createTokenScanner(org.sintef.thingml.resource.thingml.IThingmlTextResource resource, org.sintef.thingml.resource.thingml.ui.ThingmlColorManager colorManager) {
		return new org.sintef.thingml.resource.thingml.ui.ThingmlTokenScanner(resource, colorManager);
	}
	
	public org.sintef.thingml.resource.thingml.ui.ThingmlCodeCompletionHelper createCodeCompletionHelper() {
		return new org.sintef.thingml.resource.thingml.ui.ThingmlCodeCompletionHelper();
	}
	
}
