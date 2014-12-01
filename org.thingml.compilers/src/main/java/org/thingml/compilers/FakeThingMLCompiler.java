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
package org.thingml.compilers;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.actions.ActionCompiler;

import java.io.File;

/**
 * Created by ffl on 25.11.14.
 */
public class FakeThingMLCompiler extends ThingMLCompiler {

    public FakeThingMLCompiler (ActionCompiler actionCompiler) {
        super(actionCompiler);
        File folder = new File("tmp/ThingML");
        folder.delete();
        folder.mkdirs();
        folder.deleteOnExit();
        setOutputDirectory(folder);
        setErrorStream(System.out);
        setMessageStream(System.err);
    }


    @Override
    public ThingMLCompiler clone() {
        return new FakeThingMLCompiler(new ActionCompiler());
    }

    @Override
    public String getPlatform() {
        return "Fake";
    }

    @Override
    public String getName() {
        return "Fake";
    }

    @Override
    public String getDescription() {
        return "Fake";
    }

    @Override
    public boolean compile(Configuration cfg) {
        return true;
    }


}
