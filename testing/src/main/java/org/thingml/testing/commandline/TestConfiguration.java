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
package org.thingml.testing.commandline;

import org.thingml.testing.languages.SupportedLanguage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by jakobho on 25.04.2017.
 */
public class TestConfiguration {
    public enum Blacklist {
        TRUE,
        FALSE,
        ALL;

        @Override
        public String toString() {
            if (this == TRUE) return "Blacklist";
            if (this == FALSE) return "Whitelist";
            if (this == ALL) return "All";
            return "ERROR";
        }
    }

    public Set<String> languageList = new HashSet<>();
    public Blacklist categoryBlackList = Blacklist.ALL;
    public List<TestRepository.ThingMLTestCategory> categoryList;
    public Blacklist testBlackList = Blacklist.ALL;
    public List<TestRepository.ThingMLTest> testList;

    public Boolean localLink = true;
    public Boolean headerFooter = true;
    public String myIP = "";
    public String myHTTPServerPort = "";

    TestConfiguration(File configuration) throws java.lang.Exception {
        Properties prop = new Properties();
        FileInputStream in = new FileInputStream(configuration);

        // Load the properties
        prop.load(in);


        // --- Languages to compile to ---
        String _languageList = prop.getProperty("languageList", "");
        if (_languageList.compareToIgnoreCase("all") == 0) {
            // Add all supported languages
            for (SupportedLanguage language : SupportedLanguage.Languages) {
                languageList.add(language.getID());
            }
        } else {
            for (String language : _languageList.split(",")) {
                if (SupportedLanguage.IsSupported(language))
                    languageList.add(language);
                else
                    throw new java.lang.RuntimeException("Language " + language + " is not supported on current platform");
            }
        }

        // --- Test to run ---
        // Categories
        if (prop.getProperty("categoryUseBlackList","").compareToIgnoreCase("true") == 0) categoryBlackList = Blacklist.TRUE;
        if (prop.getProperty("categoryUseBlackList","").compareToIgnoreCase("false") == 0) categoryBlackList = Blacklist.FALSE;
        categoryList = TestRepository.getSelectedCategories(categoryBlackList, prop.getProperty("categoryList",""));

        // Tests
        if (prop.getProperty("useBlackList","").compareToIgnoreCase("true") == 0) testBlackList = Blacklist.TRUE;
        if (prop.getProperty("useBlackList","").compareToIgnoreCase("false") == 0) testBlackList = Blacklist.FALSE;
        testList = TestRepository.getSelectedTests(categoryList, testBlackList, prop.getProperty("testList",""));

        // --- Report settings ---
        if (prop.getProperty("webLink","").compareToIgnoreCase("true") == 0)
            localLink = false;
        if (prop.getProperty("headerFooter","").compareToIgnoreCase("false") == 0)
            headerFooter = false;
        myIP = prop.getProperty("myIP", "");
        myHTTPServerPort = prop.getProperty("myHTTPServerPort","");
    }

    public void printSummary() {
        System.out.println("****************************************");
        System.out.println("*          Test configuration          *");
        System.out.println("****************************************");

        System.out.println("Language list = "+String.join(", ",languageList));
        System.out.print("Test categories = ["+categoryBlackList.toString()+"] ");
        System.out.println(categoryList.stream().map(TestRepository.ThingMLTestCategory::getName).collect(Collectors.joining(", ")));
        System.out.print("Tests = ["+testBlackList.toString()+"] ");
        System.out.println(testList.stream().map(TestRepository.ThingMLTest::getName).collect(Collectors.joining(", ")));

    }
}
