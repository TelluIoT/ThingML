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
package org.thingml.compilers.checker;


import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Checker.InfoType;
/**
 *
 * @author sintef
 */
public abstract class Rule {
    
    public Rule() {
    }
    
    public abstract InfoType getHighestLevel();
    public abstract String getName();
    public abstract String getDescription();
    
    public abstract void check(Configuration cfg, Checker checker);
    public void check(ThingMLModel model, Checker checker) {
        for(Configuration c : model.allConfigurations()) {
            check(c, checker);
        }
    }
    
}
