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
package org.thingml.compilers.thing;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.Context;

/**
 * @author ludovic
 */
public class ThingCepSourceDeclaration {
    public void generate(Stream stream, Source source, StringBuilder builder, Context context) {
        if(source instanceof SimpleSource) {
            generate(stream, (SimpleSource) source, builder, context);
        } else if(source instanceof MergeSources) {
            generate(stream,(MergeSources)source,builder,context);
        } else if(source instanceof JoinSources) {
            generate(stream,(JoinSources)source,builder,context);
        } else {
            throw UnsupportedException.sourceException(source.getClass().getName());
        }
    }

    public void generate(Stream stream, SimpleSource source, StringBuilder builder, Context context) {
        throw new UnsupportedOperationException("Cep declaration are platform-specific. Cannot generate stream inout for " + stream.getName());
    }

    public void generate(Stream stream, MergeSources source, StringBuilder builder, Context context) {
        throw new UnsupportedOperationException("Cep declaration are platform-specific. Cannot generate stream inout for " + stream.getName());
    }

    public void generate(Stream stream, JoinSources sources, StringBuilder builder, Context context) {
        throw new UnsupportedOperationException("Cep declaration are platform-specific. Cannot generate stream inout for " + stream.getName());
    }

    protected void generateOperatorCalls(String name, Source source, StringBuilder builder, Context context) {
        if (source.getOperators().size() > 0) {
            builder.append(name + " = " + name);
            for (ViewSource view : source.getOperators()) {
                context.getCompiler().getCepCompiler().getCepViewCompiler().generate(view,builder,context);
            }
            builder.append(";\n");
        }
    }
}
