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
package org.thingml.thingmltools;

import org.sintef.thingml.ThingMLModel;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sintef
 */
public abstract class ThingMLTool {
    public File outDir;
    public File inDir;
    public File src;
    public Map<String, StringBuilder> generatedCode = new HashMap<>();
    public String options;

    public ThingMLTool() {
    }

    public abstract String getID();

    public abstract String getName();

    public abstract String getDescription();

    public abstract ThingMLTool clone();

    public File getOutputDirectory() {
        return outDir;
    }
    
    public void setSourceFile(File src) {
        this.src = src;
    }

    public void setOutputDirectory(File outputDirectory) {
        outputDirectory.mkdirs();
        if (!outputDirectory.exists())
            throw new Error("ERROR: The output directory does not exist (" + outputDirectory.getAbsolutePath() + ").");
        if (!outputDirectory.isDirectory())
            throw new Error("ERROR: The output directory has to be a directory (" + outputDirectory.getAbsolutePath() + ").");
        if (!outputDirectory.canWrite())
            throw new Error("ERROR: The output directory is not writable (" + outputDirectory.getAbsolutePath() + ").");
        outDir = outputDirectory.getAbsoluteFile();
    }
  
    public File getInputDirectory() {
        return inDir;
    }

    public void setInputDirectory(File inputDirectory) {
        if (!inputDirectory.exists())
            throw new Error("ERROR: The input directory does not exist (" + inDir.getAbsolutePath() + ").");
        if (!inputDirectory.isDirectory())
            throw new Error("ERROR: The input directory has to be a directory (" + inDir.getAbsolutePath() + ").");
        if (!inputDirectory.canRead())
            throw new Error("ERROR: The input directory is not readable (" + inDir.getAbsolutePath() + ").");
        inDir = inputDirectory.getAbsoluteFile();
    }

    public abstract void generateThingMLFrom(ThingMLModel model);

    public void writeGeneratedCodeToFiles() {
        for (Map.Entry<String, StringBuilder> e : generatedCode.entrySet()) {
            writeTextFile(e.getKey(), e.getValue().toString());
        }
    }

    /**
     * Allows to writeTextFile additional files (not generated in the normal generatedCode)
     *
     * @param path
     * @param content
     */
    public void writeTextFile(String path, String content) {
        try {
            //System.out.println("[PATH] " + path);
            File file = new File(outDir, path);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            PrintWriter w = new PrintWriter(file);
            w.print(content);
            w.close();
        } catch (Exception ex) {
            System.err.println("Problem while dumping the code");
            ex.printStackTrace();
        }
    }

}
