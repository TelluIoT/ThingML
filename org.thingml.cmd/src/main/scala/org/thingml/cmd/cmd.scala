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
package org.thingml.cmd

import org.sintef.thingml._
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.sintef.thingml.resource.thingml.IThingmlTextDiagnostic
import org.sintef.thingml.resource.thingml.mopp._
import scala.collection.JavaConversions._
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.resource.{ResourceSet, Resource}
import org.thingml.cgenerator.CGenerator
import org.thingml.cppgenerator.CPPGenerator
import org.thingml.scalagenerator.ScalaGenerator
//import org.thingml.java.pauwaregenerator.PauWareGenerator
import org.thingml.javagenerator.gui.SwingGenerator
import org.thingml.thingmlgenerator.ThingMLGenerator
import org.thingml.kotlingenerator.KotlinGenerator
//import org.thingml.scalagenerator.coap.ScalaCoAPGenerator
//import org.thingml.mediatorgenerator.LoggerGenerator
import java.io._
//import java.nio.file.Path
//import java.nio.file.Paths
import java.util.Hashtable
import javax.management.remote.rmi._RMIConnection_Stub
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.thingml.kevoreegenerator.KevoreeGenerator

import scala.collection.JavaConversions._

object Cmd {
  var targetFile: Option[File] = None
	def main(args: Array[String]) {
		if (args.length==2)
		{
	    	if (args(0) == "c")
	      		compileToC(args(1))
	    	if (args(0) == "scala")
	      		compileToScala(args(1))
	    }
		System.exit(0)
    }
    
    
	def compileToC(path : String) {
			val currentDirectory = new File(System.getProperty("user.dir"))
			val file : File = new File(currentDirectory.getParent(),path)
			targetFile = Some(file)
        println("Input file : " + targetFile)
        if (targetFile.isEmpty) return;
               try {

         val thingmlModel = loadThingMLmodel(targetFile.get)
            CGenerator.compileToLinuxAndNotMake(thingmlModel)
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
	}

   
    def compileToScala(path : String) {
			val currentDirectory = new File(System.getProperty("user.dir"))
			val file : File = new File(currentDirectory.getParent(),path)
			targetFile = Some(file)
        println("Input file : " + targetFile)
		
        if (targetFile.isEmpty) 
          return
        try {
          val thingmlModel = loadThingMLmodel(targetFile.get)
			println("Input file : " + targetFile.get.getAbsolutePath)
          thingmlModel.allConfigurations.foreach{c =>
            ScalaGenerator.compileAndNotRun(c, thingmlModel)                                                                      
          }
          
        }
        catch {
          case t : Throwable => t.printStackTrace()
        }
      }         
      
  
  def loadThingMLmodel(file : File) = {
	val reg = Resource.Factory.Registry.INSTANCE;
	reg.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

    var rs: ResourceSet = new ResourceSetImpl
    var xmiuri: URI = URI.createFileURI(file.getAbsolutePath)
    var model: Resource = rs.createResource(xmiuri)
    model.load(null)
    model.getContents.get(0).asInstanceOf[ThingMLModel]
  }
}