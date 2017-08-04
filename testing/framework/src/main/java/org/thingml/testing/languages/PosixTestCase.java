package org.thingml.testing.languages;

import java.io.File;
import java.util.Collection;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.posix.PosixCompiler;
import org.thingml.compilers.java.JavaCompiler;
import org.thingml.testing.framework.ThingMLTest;
import org.thingml.testing.framework.ThingMLTestCase;
import org.thingml.testing.helpers.ThingMLInjector;
import org.thingml.testing.utilities.CommandRunner;
import org.thingml.testing.utilities.CommandRunner.Output;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;

public class PosixTestCase extends ThingMLTestCase {
	public PosixTestCase() {
		super(new PosixCompiler());
	}
	
	protected PosixTestCase(ThingMLTest parent, ThingMLCompiler compiler) {
		super(parent, compiler);
	}

	@Override
	protected ThingMLTestCase clone(ThingMLTest parent, ThingMLCompiler compiler) {
		return new PosixTestCase(parent, compiler);
	}

	@Override
	protected void populateFileDumper(Thing dumper, ActionBlock function, Property path) throws AssertionError {
		ThingMLInjector.addActions(function,
			"'int fd = open('&DumpPath&', O_APPEND|O_SYNC|O_WRONLY);'",
			"'write(fd, &'&C&', 1);'",
			"'close(fd);'"
		);
	}

	@Override
	protected void populateStopExecution(Collection<ActionBlock> bodies) throws AssertionError {
		for (ActionBlock body : bodies) {
			ThingMLInjector.addActions(body,
				"'exit('&Code&');'"
			);
		}

	}

	@Override
	protected Output executePlatformCode(Configuration configuration, File directory) throws AssertionError {
		CommandRunner.executePlatformIndependentCommand("make").check("make");
		return CommandRunner.executePlatformIndependentCommand("./"+configuration.getName());
	}
	

	@Override
	protected void tryRunOnCurrentPlatform() throws AssertionError {
		CommandRunner.executePlatformIndependentCommand("make -v", 10).check("make -v");
		CommandRunner.executePlatformIndependentCommand("gcc -v", 10).check("gcc -v");
	}
}
