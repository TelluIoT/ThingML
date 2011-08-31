/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

/**
 * A list of constants that contains the keys for some options that are built into
 * EMFText. Generated resource plug-ins do automatically recognize this options
 * and use them if they are configured properly.
 */
public interface IThingmlOptions {
	
	/**
	 * The key for the option to provide a stream pre-processor.
	 */
	public String INPUT_STREAM_PREPROCESSOR_PROVIDER = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getInputStreamPreprocessorProviderOptionKey();
	
	/**
	 * The key for the option to provide a resource post-processor.
	 */
	public String RESOURCE_POSTPROCESSOR_PROVIDER = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getResourcePostProcessorProviderOptionKey();
	
	/**
	 * The key for the option to specify an expected content type in text resources
	 * and text parsers. A content type is an EClass that specifies the root object of
	 * a text resource. If this option is set, the parser does not use the start
	 * symbols defined in the .cs specification, but use the given EClass as start
	 * symbol instead. Note that the value for this option must be an EClass object
	 * and not the name of the EClass.
	 */
	public final String RESOURCE_CONTENT_TYPE = "RESOURCE_CONTENT_TYPE";
	
	/**
	 * The key for the options to disable marker creation for resource problems. If
	 * this options is set (the value does not matter) when loading resources,
	 * reported problems will not be added as Eclipse workspace markers. This option
	 * is used by the MarkerResolutionGenerator class, which will end up in an
	 * infinite loop if marker are created when loading resources as this creation
	 * triggers the loading of the same resource and so on.
	 */
	public final String DISABLE_CREATING_MARKERS_FOR_PROBLEMS = "DISABLE_CREATING_MARKERS_FOR_PROBLEMS";
	
}
