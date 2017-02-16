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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.testconfigurationgenerator;

import org.thingml.thingmltools.ThingMLTool;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

/**
 *
 * @author sintef
 */
public class TestConfigurationGenerator extends ThingMLTool {


    public TestConfigurationGenerator() {
        super();
    }

    @Override
    public String getID() {
        return "testconfigurationgen";
    }

    @Override
    public String getName() {
        return "Test Configuration Generator";
    }

    @Override
    public String getDescription() {
        return "Generates test configuration for things annnotated with @test \"input # output\".";
    }

    @Override
    public ThingMLTool clone() {
        return new TestConfigurationGenerator();
    }

    @Override
    public void generateThingMLFrom(ThingMLModel model) {
        System.out.println("Generate ThingML from model");
        for (Thing t : ThingMLHelpers.allThings(model)) {
            int i = 0;
            for (String an : AnnotatedElementHelper.annotation(t, "test")) {
                String[] testAn = an.split("#");
                String testInputs, testOutputs;
                if (testAn.length == 2) {
                    testInputs = testAn[0];
                    testOutputs = testAn[1];
                    System.out.println("in: " + testInputs + ", out: " + testOutputs);
                    for (Language lang : TestGenConfig.getLanguages(outDir, this.options)) {
                        generateCfg(t, testInputs, testOutputs, lang, i);
                    }
                    i++;
                }
            }
        }

        writeGeneratedCodeToFiles();
    }

    public void generateTester(StringBuilder builder, Thing t, String in, String out) {
        builder.append("thing Tester includes TestHarness, TimerClient {\n");

        int i = 0;
        builder.append("    statechart TestChart init e0 {\n");
        builder.append("        on entry do\n");
        //builder.append("            print \"[Expected] \"\n");
        //builder.append("            print \"" + out + "\"\n");
        //builder.append("            print \" [Test] \"\n");
        builder.append("            print \"[Expected] " + out + " [Test] \"\n");
        builder.append("        end\n");
        for (char c : in.toCharArray()) {
            if (c != ' ') {
                builder.append("        state e" + i + " {\n");
                builder.append("            on entry timer!timer_start(10)\n");
                builder.append("            transition -> e" + (i + 1) + "\n");
                builder.append("            event timer?timer_timeout\n");
                builder.append("            action test!testIn('\\'" + c + "\\'')\n");
                builder.append("        }\n");
                i++;
            }
        }
        builder.append("        state e" + i + " {\n");
        int testDuration = 250;
        if (AnnotatedElementHelper.hasAnnotation(t, "test_duration")) {
            String timerDuration = AnnotatedElementHelper.annotation(t, "test_duration").iterator().next();
            Integer dur = Integer.parseInt(timerDuration);
            testDuration = dur.intValue();
        }
        builder.append("            on entry timer!timer_start(" + testDuration + ")\n");
        builder.append("            transition -> e" + (i+1) + "\n");
        builder.append("            event timer?timer_timeout\n");
        builder.append("        }\n");
        i++;

        builder.append("        state e" + i + " {\n");
        builder.append("            on entry do\n"
                + "                testEnd!testEnd()\n"
                + "            end");
        builder.append("        }\n");

        builder.append("    }\n");

        builder.append("}\n");
    }

    public String LowerFirstLetter(String str) {
        if (str == null)
            return null;
        if (str.length() < 2)
            return str.toLowerCase();

        return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
    }

    public void generateCfg(Thing t, String in, String out, Language lang, int testNumber) {


        StringBuilder builder = new StringBuilder();

        builder.append("import \"../../../src/main/resources/tests/thingml.thingml\"\n\n");

        builder.append("import \"../../../src/main/resources/tests/core/_" + lang.longName + "/test.thingml\"\n");
        builder.append("import \"../../../src/main/resources/tests/" + src.getParentFile().getName() + "/" + LowerFirstLetter(t.getName()) + ".thingml\"\n");
        //builder.append("import \"" + src.getAbsolutePath() + "\"\n");
        builder.append("import \"../../../src/main/resources/tests/core/_" + lang.longName + "/timer.thingml\"\n");

        builder.append("\n");
        generateTester(builder, t, in, out);
        builder.append("\n");

        builder.append("configuration " + t.getName() + "_" + testNumber + "_Cfg\n");
        builder.append("@arduino_stdout \"Serial\"\n");
        builder.append("@arduino_stdout_baudrate \"115200\"\n");
        builder.append("{\n");

        builder.append("    instance harness : Tester\n");
        builder.append("    instance dump : TestDump" + lang.shortName + "\n");
        builder.append("    instance test : " + t.getName() + "\n");
        builder.append("    instance timer : Timer" + lang.shortName + "\n");
        if (AnnotatedElementHelper.hasAnnotation(t, "test_timer_instance")) {
            String timer_inst_name = AnnotatedElementHelper.annotation(t, "test_timer_instance").iterator().next();
            builder.append("    instance " + timer_inst_name + " : Timer" + lang.shortName + "\n");
        }
        builder.append("\n");
        builder.append("    connector harness.testEnd => dump.dumpEnd\n");
        builder.append("    connector harness.timer => timer.timer\n");
        builder.append("    connector test.harnessOut => dump.dump\n");
        builder.append("    connector test.harnessIn => harness.test\n");

        for (String an : AnnotatedElementHelper.annotation(t, "conf")) {
            builder.append("    " + an + "\n");
        }

        builder.append("}\n");

        generatedCode.put("_" + lang.longName + "/" + t.getName() + "_" + testNumber + ".thingml", builder);
    }

}
