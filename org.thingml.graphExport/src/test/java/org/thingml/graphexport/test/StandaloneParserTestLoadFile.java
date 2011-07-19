/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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
/**
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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingml.graphexport.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import junit.framework.TestCase;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.thingml.cgenerator.*;

import org.thingml.graphexport.*;

import javax.lang.model.element.NestingKind;

/**
 *
 * @author ffl
 */
public class StandaloneParserTestLoadFile extends TestCase {

    String model_path;

    public StandaloneParserTestLoadFile(String model_path) {
        this.model_path = model_path;
    }

    @Override
    public void runTest() throws IOException {

            try {
               // Register the generated package and the XMI Factory
                EPackage.Registry.INSTANCE.put(ThingmlPackage.eNS_URI, ThingmlPackage.eINSTANCE);
                Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

                // Load the model
                ResourceSet rs = new ResourceSetImpl();
                URI xmiuri = URI.createFileURI(model_path);
                Resource model = rs.createResource(xmiuri);
                model.load(null);
                //org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(rs);

                assert(model.getContents().size() > 0);

                assert ( model.getContents().get(0) instanceof ThingMLModel );

                System.out.println("Model : " + model + " : ");

                File dir = new File("test_out");
                if (!dir.exists()) dir.mkdir();

                Hashtable<String, String> dots = ThingMLGraphExport.allGraphviz( (ThingMLModel)model.getContents().get(0) );
                for (String name : dots.keySet()) {
                    System.out.println(" -> Writing file " + name + ".dot");
                    PrintWriter w = new PrintWriter(new FileWriter("test_out/" + new File(name + ".dot")));
                    w.println(dots.get(name));
                    w.close();
                }

                Hashtable<String, String> gml = ThingMLGraphExport.allGraphML( (ThingMLModel)model.getContents().get(0) );
                for (String name : gml.keySet()) {
                    System.out.println(" -> Writing file " + name + ".graphml");
                    PrintWriter w = new PrintWriter(new FileWriter("test_out/" +new File(name + ".graphml")));
                    w.println(gml.get(name));
                    w.close();
                }

                Hashtable<Configuration, String> ccode =  CGenerator.compileAll( (ThingMLModel)model.getContents().get(0));
                for (Configuration t : CGenerator.compileAll( (ThingMLModel)model.getContents().get(0)).keySet()) {
                    System.out.println(" -> Writing file " + t.getName() + ".c");
                    PrintWriter w = new PrintWriter(new FileWriter("test_out/" +new File(t.getName() + ".c")));
                    w.println(ccode.get(t));
                    w.close();
                }
            }
            catch(Throwable t) {
               t.printStackTrace();
            }

            
    }

    @Override
    public String getName() {
        return this.model_path;
    }



}