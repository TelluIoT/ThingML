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
package org.thingml.monitor;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.thingmltools.ThingMLTool;
import org.thingml.xtext.ThingMLStandaloneSetup;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ThingMLModel;

/**
 *
 * @author Brice Morin
 */
public class CoverageTool extends ThingMLTool {

    public CoverageTool() {
        super();        
    }

    @Override
    public String getID() {    	
    		return "coverage";    	
    }

    @Override
    public String getName() {
    	return "Coverage Tool";    	
    }

    @Override
    public String getDescription() {
        return "[UNDER ACTIVE DEVELOPMENT] Computes how many (types of) expressions, actions and others are instantiated";
    }

    @Override
    public ThingMLTool clone() {
        return new CoverageTool();
    }

    @Override
    public void generateThingMLFrom(ThingMLModel model) {
    	model = ThingMLHelpers.flattenModel(model);
    	
        long actions = 0, expressions = 0, others = 0;
        Set<String> actionTypes = new HashSet<String>(), expressionTypes = new HashSet<String>(), otherTypes = new HashSet<String>();
        
        TreeIterator<EObject> it = model.eAllContents();
        while(it.hasNext()) {
        	EObject o = it.next();
        	if (o instanceof Expression) {
        		expressions++;
        		expressionTypes.add(o.eClass().getName());
        	} else if (o instanceof Action) {
        		actions++;
        		actionTypes.add(o.eClass().getName());
        	} else {
        		others++;
        		otherTypes.add(o.eClass().getName());
        	}
        }
        
        System.out.println("------------");
        System.out.println("| Coverage |");
        System.out.println("------------");
        System.out.println("|\tActions: " + actions + " actions covering " + actionTypes.size() + " meta-classes." );
        System.out.println("|\tExpressions: " + expressions + " expressions covering " + expressionTypes.size() + " meta-classes." );
        System.out.println("|\tOthers: " + others + " other objects covering " + otherTypes.size() + " meta-classes." );
        System.out.println("------------");
    }
    
}
