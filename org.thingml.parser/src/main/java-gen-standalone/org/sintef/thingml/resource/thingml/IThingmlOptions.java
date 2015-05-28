/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

/**
 * A list of constants that contains the keys for some options that are built into
 * EMFText. Generated resource plug-ins do automatically recognize these options
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
	 * The key for the option to provide additional reference resolvers. The value for
	 * this option must be a map that used EReferences as keys and either single
	 * reference resolvers or collections of resolvers as values. By setting this
	 * option one can customize the resolving of references at run-time.
	 */
	public String ADDITIONAL_REFERENCE_RESOLVERS = "ADDITIONAL_REFERENCE_RESOLVERS";
	
	/**
	 * The key for the option to specify an expected content type in text resources
	 * and text parsers. A content type is an EClass that specifies the root object of
	 * a text resource. If this option is set, the parser does not use the start
	 * symbols defined in the .cs specification, but uses the given EClass as start
	 * symbol instead. Note that the value for this option must be an EClass object
	 * and not the name of the EClass.
	 */
	public final String RESOURCE_CONTENT_TYPE = "RESOURCE_CONTENT_TYPE";
	
	/**
	 * The key for the option to disable marker creation for resource problems. If
	 * this option is set to <code>true</code> when loading resources, reported
	 * problems will not be added as Eclipse workspace markers. This option is used by
	 * the MarkerResolutionGenerator class, which will end up in an infinite loop if
	 * markers are created when loading resources as this creation triggers the
	 * loading of the same resource and so on.
	 */
	public final String DISABLE_CREATING_MARKERS_FOR_PROBLEMS = "DISABLE_CREATING_MARKERS_FOR_PROBLEMS";
	
	/**
	 * The key for the option to disable the location map that maps EObjects to the
	 * position of their textual representations. If this option is set to
	 * <code>true</code>, the memory footprint of large models is reduced. Disabling
	 * the location map, however, disables functionality that relies on it (e.g.
	 * navigation in the text editor).
	 */
	public final String DISABLE_LOCATION_MAP = "DISABLE_LOCATION_MAP";
	
	/**
	 * The key for the option to disable the recording of layout information. If this
	 * option is set to <code>true</code>, the memory footprint of large models is
	 * reduced. When layout information recording is disabled, a new layout is
	 * computed during printing and the original layout is not preserved.
	 */
	public final String DISABLE_LAYOUT_INFORMATION_RECORDING = "DISABLE_LAYOUT_INFORMATION_RECORDING";
	
	/**
	 * The key for the option to set the encoding to use when loading or saving
	 * resources. This is equivalent to the same option specified in class
	 * <code>org.eclipse.emf.ecore.xmi.XMLResource</code>.
	 * 
	 * @see org.eclipse.emf.ecore.xmi.XMLResource
	 */
	public final String OPTION_ENCODING = "ENCODING";
	
}
