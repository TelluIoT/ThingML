package org.thingml.compilers.c.teensy;

import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CThingApiCompiler;
import org.thingml.xtext.thingML.Thing;

public class CThingApiCompilerTeensy extends CThingApiCompiler{

	
	@Override
    protected void generatePublicPrototypes(Thing thing, StringBuilder builder, CCompilerContext ctx) {
        builder.append("// generateEventHandlers2\nint " + ctx.getEmptyHandlerName(thing));
        ctx.appendFormalParametersEmptyHandler(thing, builder);
        builder.append(";\n");
        super.generatePublicPrototypes(thing, builder, ctx);
    }
	
	@Override
	protected void generateInstanceStruct(Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile) {
		super.generateInstanceStruct(thing, builder, ctx, debugProfile);
		
		//FIXME :Raphaël Remove it (require for teensy timer no access to _instance)
		String name = thing.getName();		
		builder.append("extern struct "+ctx.getInstanceStructName(thing) +" " +Character.toLowerCase(name.charAt(0)) + name.substring(1) +  "_var;\n");
	}
}
