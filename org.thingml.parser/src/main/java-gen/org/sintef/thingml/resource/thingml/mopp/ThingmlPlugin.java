/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

/**
 * A singleton class for the text resource plug-in.
 */
public class ThingmlPlugin extends org.eclipse.core.runtime.Plugin {
	
	public static final String PLUGIN_ID = "org.sintef.thingml.resource.thingml";
	/**
	 * The version of EMFText that was used to generate this plug-in.
	 */
	public static final String EMFTEXT_SDK_VERSION = "1.4.1";
	/**
	 * The ID of the extension point to register default options to be used when
	 * loading resources with this plug-in.
	 */
	public static final String EP_DEFAULT_LOAD_OPTIONS_ID = PLUGIN_ID + ".default_load_options";
	public static final String EP_ADDITIONAL_EXTENSION_PARSER_ID = PLUGIN_ID + ".additional_extension_parser";
	public static final String DEBUG_MODEL_ID = PLUGIN_ID + ".debugModel";
	
	private static ThingmlPlugin plugin;
	
	public ThingmlPlugin() {
		super();
	}
	
	public void start(org.osgi.framework.BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	
	public void stop(org.osgi.framework.BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	public static ThingmlPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Helper method for error logging.
	 * 
	 * @param message the error message to log
	 * @param throwable the exception that describes the error in detail (can be null)
	 * 
	 * @return the status object describing the error
	 */
	public static org.eclipse.core.runtime.IStatus logError(String message, Throwable throwable) {
		return log(org.eclipse.core.runtime.IStatus.ERROR, message, throwable);
	}
	
	/**
	 * Helper method for logging warnings.
	 * 
	 * @param message the warning message to log
	 * @param throwable the exception that describes the warning in detail (can be
	 * null)
	 * 
	 * @return the status object describing the warning
	 */
	public static org.eclipse.core.runtime.IStatus logWarning(String message, Throwable throwable) {
		return log(org.eclipse.core.runtime.IStatus.WARNING, message, throwable);
	}
	
	/**
	 * Helper method for logging.
	 * 
	 * @param type the type of the message to log
	 * @param message the message to log
	 * @param throwable the exception that describes the error in detail (can be null)
	 * 
	 * @return the status object describing the error
	 */
	protected static org.eclipse.core.runtime.IStatus log(int type, String message, Throwable throwable) {
		org.eclipse.core.runtime.IStatus status;
		if (throwable != null) {
			status = new org.eclipse.core.runtime.Status(type, ThingmlPlugin.PLUGIN_ID, 0, message, throwable);
		} else {
			status = new org.eclipse.core.runtime.Status(type, ThingmlPlugin.PLUGIN_ID, message);
		}
		final ThingmlPlugin pluginInstance = ThingmlPlugin.getDefault();
		if (pluginInstance == null) {
			System.err.println(message);
			if (throwable != null) {
				throwable.printStackTrace();
			}
		} else {
			pluginInstance.getLog().log(status);
		}
		return status;
	}
	
}
