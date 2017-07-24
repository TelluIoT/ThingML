package org.thingml.compilers.c.teensy;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CThingImplCompiler;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

public class TeensyCompiler extends OpaqueThingMLCompiler{

	public TeensyCompiler() {
		super(new CThingActionCompilerTeensy(), new CThingApiCompilerTeensy(), new CCfgMainGenerator(),
                new CfgBuildCompiler(), new CThingImplCompiler());
		this.checker = new TeensyCheker(this.getID(), this.ctx);
	}

	@Override
	public void do_call_compiler(Configuration cfg, String... options) {
		CCompilerContext ctx = new CCompilerContextTeensy(this);
        processDebug(cfg);
        ctx.setCurrentConfiguration(cfg);
        //ctx.setOutputDirectory(new File(ctx.getOutputDirectory(), cfg.getName()));

        //Checks

        this.checker.do_check(cfg);
        this.checker.printReport();

        // GENERATE A MODULE FOR EACH THING
        for (Thing thing : ConfigurationHelper.allThings(cfg)) {
            ctx.setConcreteThing(thing);

            // GENERATE HEADER
            ctx.getCompiler().getThingApiCompiler().generatePublicAPI(thing, ctx);

            // GENERATE IMPL
            ctx.getCompiler().getThingImplCompiler().generateImplementation(thing, ctx);
            ctx.clearConcreteThing();
        }

        // GENERATE A MODULE FOR THE CONFIGURATION (+ its dependencies)
        getMainCompiler().generateMainAndInit(cfg, ThingMLHelpers.findContainingModel(cfg), ctx);

        // WRITE THE GENERATED CODE
        ctx.writeGeneratedCodeToFiles();
		
	}

	@Override
	public ThingMLCompiler clone() {
		return new TeensyCompiler();
	}

	@Override
	public String getID() {
		return "teensy";
	}

	@Override
	public String getName() {
		return "C++ for Teensy 3.X (ARM Microcontrollers)";
	}

	@Override
	public String getDescription() {
		return "Generates C++ code for Teensy 3.X microcontrollers (arm-none-eabi compiler).";
	}

}
