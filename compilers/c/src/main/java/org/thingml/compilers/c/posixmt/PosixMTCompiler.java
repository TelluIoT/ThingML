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
package org.thingml.compilers.c.posixmt;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.posix.PosixCCfgBuildCompiler;
import org.thingml.compilers.c.posix.PosixCompiler;
import org.thingml.xtext.thingML.Configuration;

/**
 * Created by ffl on 25.11.14.
 */
@Deprecated
public class PosixMTCompiler extends PosixCompiler {

    public PosixMTCompiler() {
        super(new PosixMTThingActionCompiler(), new PosixMTThingApiCompiler(), new PosixMTCfgMainGenerator(),
                new PosixCCfgBuildCompiler(), new PosixMTThingImplCompiler());
    }

    @Override
    protected void setContext(Configuration cfg) {
    	ctx = new PosixMTCompilerContext(this);        
    }
    
    @Override
    public ThingMLCompiler clone() {
        return new PosixMTCompiler();
    }

    @Override
    public String getID() {
        return "posixmt";
    }

    @Override
    public String getName() {
        return "Multi-threaded C for Linux / Posix";
    }

    public String getDescription() {
        return "Generates multi-threaded C code for Linux or other Posix runtime environments (GCC compiler).";
    }

}
