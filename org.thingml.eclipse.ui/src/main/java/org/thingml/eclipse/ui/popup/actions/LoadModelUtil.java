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
package org.thingml.eclipse.ui.popup.actions;

import java.io.File;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.util.HashMap;

import javax.swing.plaf.synth.Region;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;

public class LoadModelUtil {

	
	private static LoadModelUtil instance = new LoadModelUtil();
	

	  public static LoadModelUtil getInstance() {
		return instance;
	}


	  
	public ThingMLModel loadThingMLmodel(File file)  {
		  org.eclipse.emf.ecore.resource.Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		  reg.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

		  
		 ResourceSet rs = new ResourceSetImpl();
		 rs.getResourceFactoryRegistry()
		    .getExtensionToFactoryMap()
		    .put("thingml", new ThingmlResourceFactory());
	     URI xmiuri = URI.createFileURI(file.getAbsolutePath());
	     System.out.println(file.getAbsolutePath());
	     Resource model= rs.getResource(xmiuri,true);
//	    Resource model = rs.createResource(xmiuri);
	    try {
			model.load(new HashMap<>());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return (ThingMLModel) model.getContents().get(0);
	  }
}
