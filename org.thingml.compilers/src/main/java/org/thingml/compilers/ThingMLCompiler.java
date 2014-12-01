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
package org.thingml.compilers;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.actions.ActionCompiler;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by ffl on 23.11.14.
 */
public abstract class ThingMLCompiler {


    private ActionCompiler actionCompiler;

    public ThingMLCompiler() {
        this.actionCompiler = new ActionCompiler();
    }

    public ThingMLCompiler(ActionCompiler actionCompiler) {
        this.actionCompiler = actionCompiler;
    }

    public abstract ThingMLCompiler clone();

    /**************************************************************
     * META-DATA about this particular compiler
     **************************************************************/
    public abstract String getPlatform();
    public abstract String getName();
    public abstract String getDescription();

    /**************************************************************
     * Parameters common to all compilers
     **************************************************************/
    private File outputDirectory = null;

    public void setOutputDirectory(File outDir) {
        if (!outDir.exists()) throw new Error("ERROR: The output directory does not exist (" + outDir.getAbsolutePath() + ").");
        if (!outDir.isDirectory()) throw new Error("ERROR: The output directory has to be a directory (" + outDir.getAbsolutePath() + ").");
        if (!outDir.canWrite()) throw new Error("ERROR: The output directory is not writable (" + outDir.getAbsolutePath() + ").");
        outputDirectory = outDir;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    private OutputStream messageStream;
    private OutputStream errorStream;

    public OutputStream getErrorStream() {
        return errorStream;
    }

    public void setErrorStream(OutputStream errorStream) {
        this.errorStream = errorStream;
    }

    public OutputStream getMessageStream() {
        return messageStream;
    }

    public void setMessageStream(OutputStream messageStream) {
        this.messageStream = messageStream;
    }

    /**************************************************************
     * Entry point of the compiler
     **************************************************************/
    public abstract boolean compile(Configuration cfg);

    public ActionCompiler getActionCompiler() {
        return actionCompiler;
    }
}
