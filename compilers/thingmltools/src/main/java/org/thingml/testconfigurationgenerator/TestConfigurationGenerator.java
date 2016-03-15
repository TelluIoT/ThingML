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
package org.thingml.testconfigurationgenerator;

import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingMLModel;
import org.thingml.thingmltools.ThingMLTool;

/**
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
        for (Thing t : model.allThings()) {
            int i = 0;
            for (String an : t.annotation("test")) {
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

    public void generateTester(StringBuilder builder, Thing t, String in, String out, Language lang) {
        if (lang.longName.equals("arduino")) {
            builder.append("thing Tester includes TestHarness, TimerMsgs {\n\n");
            builder.append("  required port pTimer {\n");
            builder.append("    sends mTimer_start\n");
            builder.append("    receives mTimer_timeout\n");
            builder.append("  }\n\n");
            generateArduinoStateChart(builder, in);
        } else {
            builder.append("thing Tester includes TestHarness, TimerClient {\n");
            generateCommonStateChart(builder, in);
        }


        builder.append("}\n");
    }

    private void generateArduinoStateChart(StringBuilder builder, String in) {
        int i = 0;
        builder.append("    statechart TestChart init e0 {\n");
        builder.append("        on entry print \"\\n[Test] \"\n");
        for (char c : in.toCharArray()) {
            if (c != ' ') {
                builder.append("        state e" + i + " {\n");
                builder.append("            on entry pTimer!mTimer_start(0, 10)\n");
                builder.append("            transition -> e" + (i + 1) + "\n");
                builder.append("            event pTimer?mTimer_timeout\n");
                builder.append("            action test!testIn('\\'" + c + "\\'')\n");
                builder.append("        }\n");
                i++;
            }
        }
        builder.append("        state e" + i + " {\n");
        builder.append("            on entry pTimer!mTimer_start(0, 250)\n");
        builder.append("            transition -> e" + (i + 1) + "\n");
        builder.append("            event pTimer?mTimer_timeout\n");
        builder.append("        }\n");
        i++;

        builder.append("        state e" + i + " {\n");
        builder.append("            on entry do"
                + "                print \"\\n\"\n"
                + "                testEnd!testEnd()\n"
                + "            end");
        builder.append("        }\n");

        builder.append("    }\n");
    }

    private void generateCommonStateChart(StringBuilder builder, String in) {
        int i = 0;
        builder.append("    statechart TestChart init e0 {\n");
        builder.append("        on entry print \"\\n[Test] \"\n");
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
        builder.append("            on entry timer!timer_start(250)\n");
        builder.append("            transition -> e" + (i + 1) + "\n");
        builder.append("            event timer?timer_timeout\n");
        builder.append("        }\n");
        i++;

        builder.append("        state e" + i + " {\n");
        builder.append("            on entry do"
                + "                print \"\\n\"\n"
                + "                testEnd!testEnd()\n"
                + "            end");
        builder.append("        }\n");

        builder.append("    }\n");
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

        //builder.append("import \"../../src/main/resources/tests/thingml.thingml\"\n\n");
        //
        //builder.append("import \"../../src/main/resources/tests/core/_" + lang.longName + "/test.thingml\"\n");
        //builder.append("import \"../../src/main/resources/tests/" + LowerFirstLetter(t.getName()) + ".thingml\"\n");
        //builder.append("import \"../../src/main/resources/tests/core/_" + lang.longName + "/timer.thingml\"\n");

        builder.append("import \"../thingml.thingml\"\n\n");

        builder.append("import \"../core/_" + lang.longName + "/test.thingml\"\n");
        builder.append("import \"../" + LowerFirstLetter(t.getName()) + ".thingml\"\n");

        if (lang.longName.equals("arduino"))
            generateArduinoMessages(builder);

        builder.append("\n");
        generateTester(builder, t, in, out, lang);
        builder.append("\n");

        if (lang.longName.equals("arduino"))
            generateArduinoProtocol(builder);

        builder.append("configuration " + t.getName() + "_" + testNumber + "_Cfg\n");
        if (lang.longName.equals("arduino"))
            builder.append("@arduino_stdout \"Serial\"\n");

        builder.append("{\n");

        builder.append("    instance harness : Tester\n");
        builder.append("    instance dump : TestDump" + lang.shortName + "\n");
        builder.append("    instance test : " + t.getName() + "\n");

        if (lang.longName.equals("arduino"))
            generateArduinoTimerCfg(builder, lang);
        else
            generateCommonTimerCfg(builder, lang);

        builder.append("\n");
        builder.append("    connector harness.testEnd => dump.dumpEnd\n");
        builder.append("    connector test.harnessOut => dump.dump\n");
        builder.append("    connector test.harnessIn => harness.test\n");

        for (String an : t.annotation("conf")) {
            builder.append("    " + an + "\n");
        }

        builder.append("}\n");

        generatedCode.put("_" + lang.longName + "/" + t.getName() + "_" + testNumber + ".thingml", builder);
    }

    private void generateArduinoMessages(StringBuilder builder) {
        builder.append("thing fragment TimerMsgs {\n");
        builder.append("  message mTimer_start(id : UInt8, time : UInt32) @timer_start \"true\";\n");
        builder.append("  message mTimer_timeout(id : UInt8) @timeout \"true\";\n");
        builder.append("}\n\n");
    }

    private void generateArduinoProtocol(StringBuilder builder) {
        builder.append("protocol Timer @hardware_timer \"0\"\n\n");
    }

    private void generateArduinoTimerCfg(StringBuilder builder, Language lang) {
        builder.append("    connector harness.pTimer over Timer @hardware_time \"0\"\n");
    }

    private void generateCommonTimerCfg(StringBuilder builder, Language lang) {
        builder.append("    instance timer : Timer" + lang.shortName + "\n");
        builder.append("    connector harness.timer => timer.timer\n");

    }
}
