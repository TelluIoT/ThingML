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
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 *
 * @author ffl
 */
public class StandaloneParserTestSuite extends TestSuite {

    public StandaloneParserTestSuite() {}

    public static Test suite() {
        try {
            TestSuite suite = new TestSuite("Standalone Parser Test Suite");
            populate(suite, "model/actions");
            populate(suite, "model/datatypes");
            populate(suite, "model/devices");
            populate(suite, "model/msp");
            populate(suite, "model/statecharts");
            return suite;
        } 
        catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static void populate(TestSuite ts, String folder) {
              File dir = new File(StandaloneParserTestSuite.class.getClassLoader().getResource(folder).getFile());
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (f.isFile() && f.getName().endsWith(".thingml")) {
                    ts.addTest(new StandaloneParserTestLoadFile(f.getAbsolutePath()));
                }
            }
        }
    }


}