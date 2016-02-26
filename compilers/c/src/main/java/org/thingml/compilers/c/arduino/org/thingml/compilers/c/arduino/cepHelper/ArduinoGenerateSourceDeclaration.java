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
package org.thingml.compilers.c.arduino.org.thingml.compilers.c.arduino.cepHelper;

import org.sintef.thingml.JoinSources;
import org.sintef.thingml.MergeSources;
import org.sintef.thingml.SimpleSource;
import org.sintef.thingml.Stream;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;

public class ArduinoGenerateSourceDeclaration extends ThingCepSourceDeclaration{
    @Override
    public void generate(Stream stream, SimpleSource source, StringBuilder builder, Context context) {

    }

    @Override
    public void generate(Stream stream, MergeSources source, StringBuilder builder, Context context) {

    }

    @Override
    public void generate(Stream stream, JoinSources sources, StringBuilder builder, Context context) {

    }
}
