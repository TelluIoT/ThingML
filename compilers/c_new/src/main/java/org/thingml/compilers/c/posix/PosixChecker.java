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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.c.posix;

import org.thingml.compilers.Context;
import org.thingml.compilers.c.CChecker;
import org.thingml.xtext.thingML.Configuration;

/**
 *
 * @author sintef
 */
public class PosixChecker extends CChecker {

    public PosixChecker(String compiler, Context ctx) {
        super(compiler, ctx);
    }

    @Override
    public void do_check(Configuration cfg) {

        //ADD Posix specific checks
        super.do_generic_check(cfg);

    }

}
