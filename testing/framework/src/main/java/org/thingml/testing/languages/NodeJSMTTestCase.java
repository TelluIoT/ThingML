package org.thingml.testing.languages;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.javascript.NodeJSMTCompiler;
import org.thingml.testing.framework.ThingMLTest;
import org.thingml.testing.framework.ThingMLTestCase;

public class NodeJSMTTestCase extends NodeJSTestCase {
	public NodeJSMTTestCase() {
		super(new NodeJSMTCompiler());
	}
	
	protected NodeJSMTTestCase(ThingMLTest parent, ThingMLCompiler compiler) {
		super(parent, compiler);
	}

	@Override
	protected ThingMLTestCase clone(ThingMLTest parent, ThingMLCompiler compiler) {
		return new NodeJSMTTestCase(parent, compiler);
	}
}
