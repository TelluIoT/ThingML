package org.thingml.testing.languages;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.posixmt.PosixMTCompiler;
import org.thingml.testing.framework.ThingMLTest;
import org.thingml.testing.framework.ThingMLTestCase;

public class PosixMTTestCase extends PosixTestCase {
	public PosixMTTestCase() {
		super(new PosixMTCompiler());
	}
	
	protected PosixMTTestCase(ThingMLTest parent, ThingMLCompiler compiler) {
		super(parent, compiler);
	}

	@Override
	protected ThingMLTestCase clone(ThingMLTest parent, ThingMLCompiler compiler) {
		return new PosixMTTestCase(parent, compiler);
	}
}
