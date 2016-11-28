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
package org.thingml.compilers.utils;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by ffl on 24.11.14.
 */
public abstract class OpaqueThingMLCompiler extends ThingMLCompiler {

    PrintStream m, e;

    public OpaqueThingMLCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, ThingImplCompiler thingImplCompiler, ThingCepCompiler cepCompiler) {
        super(thingActionCompiler, thingApiCompiler, mainCompiler, cfgBuildCompiler, thingImplCompiler, cepCompiler);
        final OutputStream stream = getMessageStream();
        if (stream != null) {
            m = new PrintStream(stream);
            e = new PrintStream(stream);
        }
    }

    protected void println(String msg) {
        if (m != null)
            m.println(msg);
        else
            System.out.println(msg);
    }

    protected void erroln(String msg) {
        if (e != null)
            e.println(msg);
        else
            System.err.println(msg);
    }

    @Override
    public boolean compile(Configuration cfg, String... options) {
        try {
            println("Running " + getName() + " compiler on configuration " + cfg.getName());
            final long start = System.currentTimeMillis();
            do_call_compiler(cfg, options);
            println("Compilation complete. Took " + (System.currentTimeMillis() - start) + " ms.");
        } catch (Error err) {
            erroln("Compilation error:" + err.getMessage());
            err.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean compileConnector(String connector, Configuration cfg, String... options) {
        println("Running connector compiler " + connector + " on configuration " + cfg.getName());
        final long start = System.currentTimeMillis();
        boolean result = super.compileConnector(connector, cfg, options);
        if (result) {
            println("Compilation complete. Took " + (System.currentTimeMillis() - start) + " ms.");
        } else {
            println("Compilation error. Connector compiler " + connector + " not found.");
        }
        return result;
    }

    public abstract void do_call_compiler(Configuration cfg, String... options);

}
