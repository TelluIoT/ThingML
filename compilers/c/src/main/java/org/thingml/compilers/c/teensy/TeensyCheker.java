package org.thingml.compilers.c.teensy;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CChecker;
import org.thingml.xtext.thingML.Configuration;

public class TeensyCheker extends CChecker{

	public TeensyCheker(String compiler, Context ctx) {
		super(compiler, ctx);
	}

	@Override
	public void do_generic_check(Configuration cfg) {
		// TODO Auto-generated method stub
		super.do_generic_check(cfg);
	}
	
	@Override
    public void do_check(Configuration cfg) {


        super.do_generic_check(cfg);

    }
			
}
