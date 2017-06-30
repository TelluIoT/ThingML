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
package org.thingml.compilers.c.posix;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCompilerContext;

/**
 * Created by ffl on 11.06.15.
 */
public class CCompilerContextPosix extends CCompilerContext {

    public CCompilerContextPosix(ThingMLCompiler c) {
        super(c);
    }

    public boolean sync_fifo() {
        return true;
    }

    public int fifoSize() {
        return 32768;
    }


}
