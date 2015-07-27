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
package org.thingml.compilers.java;

import org.sintef.thingml.JoinSources;
import org.sintef.thingml.MergeSources;
import org.sintef.thingml.SimpleSource;
import org.sintef.thingml.Stream;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.CepCompiler;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.cepHelper.JavaGenerateSourceDeclaration;
import org.thingml.compilers.java.cepHelper.JavaGenerateSubscription;

/**
 * @author ludovic
 */
public class JavaCepCompiler extends CepCompiler {
    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        JavaGenerateSourceDeclaration.generate(stream,stream.getInput(),builder,ctx);
        if(stream.getInput() instanceof SimpleSource) {
            generateSimpleStream(stream, builder, ctx);
        } else if(stream.getInput() instanceof MergeSources) {
            generateMergedStream(stream, builder, ctx);
        } else if(stream.getInput() instanceof JoinSources) {
            generateJoinedStream(stream, builder, ctx);
        } else {
            throw UnsupportedException.sourceException(stream.getClass().getName());
        }
    }

    private void generateJoinedStream(Stream stream, StringBuilder builder, Context ctx) {
//        JavaGenerateSourceDeclaration.generate(stream,(JoinSources)stream.getInput(),builder,ctx);
        JavaGenerateSubscription.generateJoineStreamSubscription(stream,builder,ctx);

    }

    private void generateMergedStream(Stream stream, StringBuilder builder, Context ctx) {
//        JavaGenerateSourceDeclaration.generate(stream, (SourceComposition) stream.getInput(), builder, ctx);
        JavaGenerateSubscription.generateMergeStreamSubscription(stream,builder,ctx);
    }

    private void generateSimpleStream(Stream stream, StringBuilder builder, Context ctx) {
//        JavaGenerateSourceDeclaration.generate(stream, (SimpleSource) stream.getInput(), builder, ctx);
        JavaGenerateSubscription.generateSimpleStreamSubscription(stream,builder,ctx);
    }
}
