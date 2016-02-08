/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.compilers.javascript.cepHelper;

import org.sintef.thingml.*;
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
        builder.append(".bufferWithTime(" + timeWindow.getSize() + "," + timeWindow.getStep() + ")");
    }

    @Override
    public void generate(LengthWindow lengthWindow, StringBuilder builder, Context context) {
        builder.append(".bufferWithCount(" + lengthWindow.getNbEvents());
        if (lengthWindow.getStep() != -1) {
            builder.append(", " + lengthWindow.getStep());
        }
        builder.append(")");
    }
}
