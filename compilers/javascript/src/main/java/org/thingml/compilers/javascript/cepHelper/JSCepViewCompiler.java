/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.compilers.javascript.cepHelper;

import org.thingml.xtext.thingML.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingCepViewCompiler;

/**
 * @author ludovic
 */
public class JSCepViewCompiler extends ThingCepViewCompiler {
    @Override
    public void generate(Filter filter, StringBuilder builder, Context context) {
        String param = "x";
        if (filter.eContainer() instanceof SimpleSource) {
            SimpleSource s = (SimpleSource) filter.eContainer();
            param = s.getMessage().getMessage().getName();
        } else if (filter.eContainer() instanceof SourceComposition) {
            SourceComposition s = (SourceComposition) filter.eContainer();
            param = s.getResultMessage().getName();
        }
        builder.append(".filter(function(" + param + ", idx, obs) {return ");
        context.getCompiler().getThingActionCompiler().generate(filter.getGuard(), builder, context);
        builder.append(";})");
    }

    @Override
    public void generate(TimeWindow timeWindow, StringBuilder builder, Context context) {
        builder.append(".bufferWithTime(");
        context.getCompiler().getThingActionCompiler().generate(timeWindow.getDuration(), builder, context);
        builder.append(",");
        if (timeWindow.getStep() != null)
            context.getCompiler().getThingActionCompiler().generate(timeWindow.getStep(), builder, context);
        else
            context.getCompiler().getThingActionCompiler().generate(timeWindow.getDuration(), builder, context);
        builder.append(")");
    }

    @Override
    public void generate(LengthWindow lengthWindow, StringBuilder builder, Context context) {
        builder.append(".bufferWithCount(");
        context.getCompiler().getThingActionCompiler().generate(lengthWindow.getSize(), builder, context);
        builder.append(", ");
        if (lengthWindow.getStep() != null)
            context.getCompiler().getThingActionCompiler().generate(lengthWindow.getStep(), builder, context);
        else
            context.getCompiler().getThingActionCompiler().generate(lengthWindow.getSize(), builder, context);
        builder.append(")");
    }
}
