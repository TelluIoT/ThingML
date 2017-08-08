package org.thingml.testing.languages;

import java.io.File;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.posix.PosixCompiler;
import org.thingml.testing.framework.ThingMLTest;
import org.thingml.testing.framework.ThingMLTestCase;
import org.thingml.testing.helpers.ThingMLInjector;
import org.thingml.testing.utilities.CommandRunner;
import org.thingml.testing.utilities.CommandRunner.Output;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;

public class PosixTestCase extends ThingMLTestCase {
	public PosixTestCase() {
		super(new PosixCompiler());
	}
	
	// Allow sub-classing this class
	protected PosixTestCase(ThingMLCompiler compiler) {
		super(compiler);
	}
	
	protected PosixTestCase(ThingMLTest parent, ThingMLCompiler compiler) {
		super(parent, compiler);
	}

	@Override
	protected ThingMLTestCase clone(ThingMLTest parent, ThingMLCompiler compiler) {
		return new PosixTestCase(parent, compiler);
	}
	
	private void addHeaders(AnnotatedElement element, String... headers) {
		for (String header : headers) {
			PlatformAnnotation annotation = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
			annotation.setName("c_header");
			annotation.setValue(header);
			element.getAnnotations().add(annotation);
		}
	}

	@Override
	protected void populateFileDumper(Thing dumper, ActionBlock function, Property path) throws AssertionError {
		ThingMLInjector.addActions(function,
			"'int fd = open('&DumpPath&', O_APPEND|O_SYNC|O_WRONLY);'",
			"'write(fd, &'&C&', 1);'",
			"'close(fd);'"
		);
		// Add some includes
		addHeaders(dumper,
			"#include <sys/stat.h>",
			"#include <fcntl.h>",
			"#include <unistd.h>"
		);
	}

	@Override
	protected void populateStopExecution(Thing thing, ActionBlock body) throws AssertionError {
		ThingMLInjector.addActions(body,
			"'exit('&Code&');'"
		);
		// Add some includes
		addHeaders(thing,
			"#include <stdlib.h>"
		);
	}

	@Override
	protected Output executePlatformCode(Configuration configuration, File directory) throws AssertionError {
		CommandRunner.executePlatformIndependentCommandIn(directory, "make").check("make");
		return CommandRunner.executePlatformIndependentCommandIn(directory, "./"+configuration.getName());
	}
	

	@Override
	protected void tryRunOnCurrentPlatform() throws AssertionError {
		CommandRunner.executePlatformIndependentCommand("make -v", 10).check("make -v");
		CommandRunner.executePlatformIndependentCommand("gcc -v", 10).check("gcc -v");
	}
}
