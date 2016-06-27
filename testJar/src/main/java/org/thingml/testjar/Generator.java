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
package org.thingml.testjar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;


/**
 *
 * @author sintef
 */
public class Generator {

    private final static String thingTemplate = loadTemplate("generator/thing.thingml");
    private final static String atomicTemplate = loadTemplate("generator/atomic.thingml");
    private final static String compositeTemplate = loadTemplate("generator/composite.thingml");
    private final static String regionTemplate = loadTemplate("generator/region.thingml");
    private final static String sessionTemplate = loadTemplate("generator/session.thingml");
    private final static String emptyTemplate = loadTemplate("generator/emptytransitions.thingml");
    private final static String transitionTemplate = loadTemplate("generator/transitions.thingml");
    private final static String[] sendActions = {loadTemplate("generator/actionSend1.thingml"), loadTemplate("generator/actionSend2.thingml")};
    private final static String[] actions = {loadTemplate("generator/action0.thingml"), loadTemplate("generator/action1.thingml"), loadTemplate("generator/action2.thingml")};
    private final static String forkAction = loadTemplate("generator/actionFork.thingml");

    private final static Random random = new Random();

    private static String loadTemplate(String resource) {
        try {
            final InputStream input = Generator.class.getClassLoader().getResourceAsStream(resource);
            final List<String> lines = IOUtils.readLines(input);
            String result = "";
            for (String line : lines) {
                result += line + "\n";
            }
            input.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    private static String createComposite(final String template, String name, int depth, int nbAtomic, int nbComposite, int nbRegion, int nbSession) {
        StringBuilder builder = new StringBuilder();
        if (depth > 1) {
            for (int i = 0; i < nbComposite; i++) {
                builder.append(createComposite(compositeTemplate, name + "_" + i, depth - 1, nbAtomic, nbComposite, nbRegion, nbSession));
            }
        }
        for(int i = 0; i < nbAtomic; i++) {
            String state = atomicTemplate.replace("/*$NAME$*/", name + "_" + (nbComposite + i));
            final String next = (i < nbAtomic - 1)? name + "_" + (nbComposite + i + 1) : name + "_" + (nbComposite);
            String transition = "";
            if (i%2 == 0) {
                transition = transitionTemplate.replace("/*$ON_A$*/", next);
                transition = transition.replace("/*$ON_B$*/", name + "_" + (nbComposite + (Math.abs(random.nextInt()) % nbAtomic)));

            } else {
                transition = emptyTemplate.replace("/*$NEXT$*/", next);
            }
            state = state.replace("/*$SEND$*/", sendActions[Math.abs(random.nextInt()) % sendActions.length]);
            state = state.replace("/*$ACTION1$*/", actions[Math.abs(random.nextInt()) % actions.length]);
            state = state.replace("/*$ACTION2$*/", actions[Math.abs(random.nextInt()) % actions.length]);
            final int r = Math.abs(random.nextInt());
            transition = transition.replace("/*$ACTION1$*/", actions[r % actions.length]);
            transition = transition.replace("/*$ACTION2$*/", actions[(r+1) % actions.length]);
            state = state.replace("/*$TRANSITIONS$*/", transition);
            builder.append(state);
        }
        if (depth > 1) {
            for (int i = 0; i < nbSession; i++) {
                builder.append(createComposite(sessionTemplate, name + "_s_" + i, depth - 1, nbAtomic, nbComposite, nbRegion, nbSession));
            }
            for (int i = 0; i < nbRegion; i++) {
                builder.append(createComposite(regionTemplate, name + "_r_" + i, depth - 1, nbAtomic, nbComposite, nbRegion, nbSession));
            }
        }
        return template.replace("/*$NAME$*/", name).replace("/*$INIT_NAME$*/", name + "_" + nbComposite).replace("/*$BEHAVIOR$*/", builder.toString());
    }

    public static void main(String[] args) {
        final String behavior = Generator.createComposite(compositeTemplate, "c", 2, 5, 2, 2, 2);
        final String thing = thingTemplate.replace("/*$NAME$*/", "myThing").replace("/*$INIT_NAME$*/", "c").replace("/*$MAX_T$*/", "100000").replace("/*$BEHAVIOR$*/", behavior);

        try {
            final File f = new File("generated.thingml");
            final OutputStream output = new FileOutputStream(f);
            IOUtils.write(thing, output, Charset.forName("UTF-8"));
            IOUtils.closeQuietly(output);
        } catch (Exception e) {

        }
        System.out.println(thing);
    }
}
