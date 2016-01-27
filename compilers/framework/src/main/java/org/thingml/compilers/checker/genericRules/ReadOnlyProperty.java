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
package org.thingml.compilers.checker.genericRules;

import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Expression;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Instance;
import org.sintef.thingml.Port;
import org.sintef.thingml.PropertyAssign;
import org.sintef.thingml.Thing;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 *
 * @author sintef
 */
public class ReadOnlyProperty extends Rule {
    
    public ReadOnlyProperty() {
        super();
    }

    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.ERROR;
    }

    @Override
    public String getName() {
        return "Read Only Property";
    }

    @Override
    public String getDescription() {
        return "Check that no read only property has its value changed after initialization";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for(Thing t : cfg.allThings()) {
            for(Expression e : t.allExpression(PropertyAssign.class)) {
                PropertyAssign pa = (PropertyAssign) e;
                if(!pa.getProperty().isChangeable()) {
                    checker.addGenericError("Property " + pa.getProperty().getName() + " (" + t.getName() + ") is ReadOnly and has its value changed", pa.getProperty());
                }
            }
        }
    }
    
}
