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
package org.thingml.compilers.c;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.c.checkerRules.ArrayCardinality;
import org.thingml.compilers.c.checkerRules.PointerParameters;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sintef
 */
public abstract class CChecker extends Checker {
    Set<Rule> CRules;

    public CChecker(String compiler) {
        super(compiler);
        CRules = new HashSet<Rule>();
        CRules.add(new PointerParameters());
        CRules.add(new ArrayCardinality());
    }

    @Override
    public void do_generic_check(Configuration cfg) {
        String Cname = "C";

        for (Rule r : CRules) {
            r.check(cfg, this);
        }
        //ADD C specific checks

        super.do_generic_check(cfg);
    }

}
