/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlMetaInformation implements org.sintef.thingml.resource.thingml.IThingmlMetaInformation {
	
	public String getSyntaxName() {
		return "thingml";
	}
	
	public String getURI() {
		return "http://thingml";
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTextScanner createLexer() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlAntlrScanner(new org.sintef.thingml.resource.thingml.mopp.ThingmlLexer());
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTextParser createParser(java.io.InputStream inputStream, String encoding) {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlParser().createInstance(inputStream, encoding);
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTextPrinter createPrinter(java.io.OutputStream outputStream, org.sintef.thingml.resource.thingml.IThingmlTextResource resource) {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlPrinter2(outputStream, resource);
	}
	
	public org.eclipse.emf.ecore.EClass[] getClassesWithSyntax() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxCoverageInformationProvider().getClassesWithSyntax();
	}
	
	public org.eclipse.emf.ecore.EClass[] getStartSymbols() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlSyntaxCoverageInformationProvider().getStartSymbols();
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlReferenceResolverSwitch getReferenceResolverSwitch() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlReferenceResolverSwitch();
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTokenResolverFactory getTokenResolverFactory() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenResolverFactory();
	}
	
	public String getPathToCSDefinition() {
		return "org.sintef.thingml.model/src/main/model/thingml.cs";
	}
	
	public String[] getTokenNames() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlParser(null).getTokenNames();
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTokenStyle getDefaultTokenStyle(String tokenName) {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyleInformationProvider().getDefaultTokenStyle(tokenName);
	}
	
	public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlBracketPair> getBracketPairs() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlBracketInformationProvider().getBracketPairs();
	}
	
	public org.eclipse.emf.ecore.EClass[] getFoldableClasses() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlFoldingInformationProvider().getFoldableClasses();
	}
	
	public org.eclipse.emf.ecore.resource.Resource.Factory createResourceFactory() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory();
	}
	
	public org.sintef.thingml.resource.thingml.mopp.ThingmlNewFileContentProvider getNewFileContentProvider() {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlNewFileContentProvider();
	}
	
	public void registerResourceFactory() {
		org.eclipse.emf.ecore.resource.Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(getSyntaxName(), new org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory());
	}
	
	/**
	 * Returns the key of the option that can be used to register a preprocessor that
	 * is used as a pipe when loading resources. This key is language-specific. To
	 * register one preprocessor for multiple resource types, it must be registered
	 * individually using all keys.
	 */
	public String getInputStreamPreprocessorProviderOptionKey() {
		return getSyntaxName() + "_" + "INPUT_STREAM_PREPROCESSOR_PROVIDER";
	}
	
	/**
	 * Returns the key of the option that can be used to register a post-processors
	 * that are invoked after loading resources. This key is language-specific. To
	 * register one post-processor for multiple resource types, it must be registered
	 * individually using all keys.
	 */
	public String getResourcePostProcessorProviderOptionKey() {
		return getSyntaxName() + "_" + "RESOURCE_POSTPROCESSOR_PROVIDER";
	}
	
}
