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
package org.thingml.cmd;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.thingml.cgenerator.CGenerator;
import org.thingml.compilers.*;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.compilers.javascript.JavaScriptCompiler;
import org.thingml.compilers.java.JavaCompiler;

import java.io.File;

/**
 * Created by bmori on 27.05.2015.
 */
public class Cmd {

//    public static File targetFile = null;

//    private static Cmd ourInstance = new Cmd();

//    public static Cmd getInstance() {
//        return ourInstance;
//    }

//    private Cmd() {
//    }

    public static ThingMLModel loadThingMLmodel(File file) {
        try {
            Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
            reg.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

            ResourceSet rs = new ResourceSetImpl();
            URI xmiuri = URI.createFileURI(file.getAbsolutePath());
            Resource model = rs.createResource(xmiuri);
            model.load(null);
            return (ThingMLModel) model.getContents().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void compile(String compilerName, ThingMLModel thingmlModel) {
//        ThingMLModel thingmlModel = Cmd.loadThingMLmodel(targetFile);
        if (compilerName.equals("linux")) {
            CGenerator.compileToLinuxAndNotMake(thingmlModel);
        } else if (compilerName.equals("javascript")) {
            OpaqueThingMLCompiler compiler = new JavaScriptCompiler();
            for(Configuration c : thingmlModel.allConfigurations()) {
                File folder = new File("tmp/ThingML_Javascript/");
                folder.mkdirs();
                compiler.setOutputDirectory(folder);
                compiler.compile(c);
            }
        }
        else if (compilerName.equals("java")) {
            OpaqueThingMLCompiler compiler = new JavaCompiler();
            for(Configuration c : thingmlModel.allConfigurations()) {
                File folder = new File("tmp/ThingML_Java/" + c.getName());
                folder.mkdirs();
                compiler.setOutputDirectory(folder);
                Context ctx = new Context(compiler, "match", "requires", "type", "abstract", "do", "finally", "import", "object", "throw", "case", "else", "for", "lazy", "override", "return", "trait", "catch", "extends", "forSome", "match", "package", "sealed", "try", "while", "class", "false", "if", "new", "private", "super", "true", "final", "null", "protected", "this", "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@");
                compiler.do_call_compiler(c, "org.thingml.generated", "doingTests");
            }
        }
        else if (compilerName.equals("arduino")) {
            CGenerator.compileAndRunArduino(thingmlModel, "", "", true);
        }
    }

    public static void main(String args[]) {
        if (args.length==2) {
            File currentDirectory = new File(System.getProperty("user.dir"));
            File targetFile = new File(currentDirectory.getParent(),args[1]);
            System.out.println("Compiler: " + args[0]);
            System.out.println("Input file : " + targetFile);
//            if (targetFile == null) return;
//            compile(args[0]);
            if(targetFile.exists()) {
                ThingMLModel thingmlModel = Cmd.loadThingMLmodel(targetFile);
                compile(args[0], thingmlModel);
            }

        }
//        System.exit(0);
    }

}
