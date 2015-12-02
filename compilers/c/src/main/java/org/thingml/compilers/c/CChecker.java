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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.c;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.Checker;

/**
 *
 * @author sintef
 */
public abstract class CChecker extends Checker{

    public CChecker(String compiler) {
        super(compiler);
    }

    @Override
    public void do_check(Configuration cfg) {
        String Cname = "C";
        
        //ADD C specific checks
        
        this.do_generic_check(cfg);
    }
    
}
