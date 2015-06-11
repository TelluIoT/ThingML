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
package org.thingml.cep.benchmark;


import org.sintef.thingml.*;
import org.thingml.cmd.Cmd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ludovic
 */
public class Launcher {

    private  static List<Integer> nbIterList;

    private static void initList() {
        nbIterList = new ArrayList<>();
        nbIterList.add(10);
        //nbIterList.add(100000);
        //nbIterList.add(1000000000);
    }
    public static void main(String args[]) {
        if(args.length == 3) {
            initList();

            for(int i = 1; i <3; i++) {
                ThingMLModel model = Cmd.loadThingMLmodel(new File(args[i]));

                for (Integer integer : nbIterList) {
                    for (Thing thing : model.allThings()) {
                        if (thing.getName().equals("Sender")) {
                            for (Property property : thing.allProperties()) {
                                if (property.getName().equals("nbIter")) {
                                    IntegerLiteral newValue = ThingmlFactory.eINSTANCE.createIntegerLiteral();
                                    newValue.setIntValue(integer);
                                    property.setInit(newValue);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    Cmd.compile(args[0], model);

                    Configuration configuration = model.getConfigs().get(0);
                    File workingFolder = new File(System.getProperty("user.dir") + "/tmp/ThingML_Javascript/" + configuration.getName());

                    ProcessBuilder npmINstall = new ProcessBuilder("npm","install");
                    npmINstall.directory(workingFolder);

                    ProcessBuilder nodeMain = new ProcessBuilder("node","main.js");
                    nodeMain.directory(workingFolder);

                    try {
                        npmINstall.start();
                        Process process = nodeMain.start();
                        System.out.println("Execution : " + configuration.getName() + " with nbIter = " + integer);;
                        process.waitFor();

                    } catch (IOException ioex) {
                        ioex.printStackTrace();
                    } catch (InterruptedException iex) {
                        iex.printStackTrace();
                    }
                }

            }
        } else {
            System.err.println("Arguments numer not valid. \n" +
                    "Usage : [exec cmd] {javascript|arduino|linux|java} filePath1 filePath12");
        }
    }

}
