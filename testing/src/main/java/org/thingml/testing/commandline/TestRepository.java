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

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jakobho on 25.04.2017.
 */
public class TestRepository {
    private static Pattern testPattern = Pattern.compile("^test(.+)\\.thingml$");

    public static class ThingMLTest {
        private String name;
        private File file;
        private ThingMLTestCategory category;

        private ThingMLTest(File testFile, String name, ThingMLTestCategory category) {
            this.name = name;
            this.file = testFile;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public File getFile() {
            return file;
        }

        public ThingMLTestCategory getCategory() {
            return category;
        }
    }

    public static class ThingMLTestCategory {
        private String name = "";
        private Map<String, ThingMLTest> tests = new HashMap<>();

        private ThingMLTestCategory(File categoryDir) {
            name = categoryDir.getName();

            for (File testFile : categoryDir.listFiles()) {
                Matcher m = testPattern.matcher(testFile.getName());
                if (m.matches()) {
                    ThingMLTest test = new ThingMLTest(testFile, m.group(1), this);
                    tests.put(test.getName(), test);
                }

            }
        }

        public String getName() {
            return name;
        }

        public Boolean isEmpty() {
            return tests.isEmpty();
        }

        public Set<Map.Entry<String, ThingMLTest>> getTests() {
            return tests.entrySet();
        }
    }

    private static Map<String, ThingMLTestCategory> categories = new HashMap<>();
    private static Map<String, ThingMLTest> tests = new HashMap<>();

    public static void buildTestRepository (File workingDir) {
        //final File testsDir = new File(workingDir, "/src/main/resources/tests"); // TODO: Swap
        final File testsDir = new File(workingDir, "../testJar/src/main/resources/tests");
        if (!testsDir.exists() || !testsDir.isDirectory()) {
            throw new java.lang.RuntimeException("The tests directory \""+testsDir.getAbsolutePath()+"\" does not exist");
        }

        categories.clear();
        tests.clear();

        // Build list of categories
        for (File catDir : testsDir.listFiles()) {
            if (catDir.isDirectory()) {
                ThingMLTestCategory category = new ThingMLTestCategory(catDir);
                if (!category.isEmpty() && !category.getName().isEmpty()) {
                    categories.put(category.getName(), category);
                    for (Map.Entry<String, ThingMLTest> test : category.getTests())
                        tests.put(test.getKey(), test.getValue());
                }
            }
        }
    }

    public static void listTestRepository () {
        System.out.println(" --- ThingML testing repository ---\n");
        for (String category : categories.keySet()) {
            System.out.println(category+":");
            for (int i = 0; i < category.length()+1; i++) System.out.print("-");
            System.out.println("");

            Integer count = 0;
            for (Map.Entry<String,ThingMLTest> test : categories.get(category).getTests()) {
                if (count > 0) System.out.print(", ");

                System.out.print(test.getKey());

                if (count == 5) { count = 0; System.out.println(""); }
                else count++;
            }
            System.out.println("\n");
        }
    }

    public static List<String> getSelectedCategoryNames(TestConfiguration.Blacklist categoryUseBlacklist, String categoryList) {
        List<String> result = new ArrayList<>();
        for (ThingMLTestCategory category : getSelectedCategories(categoryUseBlacklist, categoryList))
            result.add(category.getName());
        return result;
    }

    public static List<ThingMLTestCategory> getSelectedCategories(TestConfiguration.Blacklist useBlacklist, String categoryList) {
        List<ThingMLTestCategory> result = new ArrayList<>();

        if (useBlacklist == TestConfiguration.Blacklist.ALL) {
            // Use all categories
            result.addAll(categories.values());
        } else {
            Set<String> list = new HashSet<>();
            for (String category : categoryList.split(",")) list.add(category.trim().toLowerCase());

            for (Map.Entry<String,ThingMLTestCategory> category : categories.entrySet()) {
                if (useBlacklist == TestConfiguration.Blacklist.TRUE && !list.contains(category.getKey().toLowerCase()))
                    result.add(category.getValue());
                else if (useBlacklist == TestConfiguration.Blacklist.FALSE && list.contains(category.getKey().toLowerCase()))
                    result.add(category.getValue());
            }
        }

        return result;
    }

    public static List<String> getSelectedTestNames(List<ThingMLTestCategory> selectedCategories, TestConfiguration.Blacklist useBlacklist, String testList) {
        List<String> result = new ArrayList<>();
        for (ThingMLTest test: getSelectedTests(selectedCategories, useBlacklist, testList))
            result.add(test.getName());
        return result;
    }

    public static List<ThingMLTest> getSelectedTests(List<ThingMLTestCategory> selectedCategories, TestConfiguration.Blacklist useBlacklist, String testList) {
        List<ThingMLTest> result = new ArrayList<>();

        // Get all tests in the selected categories
        Map<String,ThingMLTest> availableTests = new HashMap<>();
        for (ThingMLTestCategory category : selectedCategories)
            for (Map.Entry<String,ThingMLTest> test : category.getTests())
                availableTests.put(test.getKey(), test.getValue());

        if (useBlacklist == TestConfiguration.Blacklist.ALL) {
            // Use all tests
            result.addAll(availableTests.values());
        } else {
            Set<String> list = new HashSet<>();
            for (String test : testList.split(",")) list.add(test.trim().toLowerCase());

            for (Map.Entry<String,ThingMLTest> test : availableTests.entrySet()) {
                if (useBlacklist == TestConfiguration.Blacklist.TRUE && !list.contains(test.getKey().toLowerCase()))
                    result.add(test.getValue());
                else if (useBlacklist == TestConfiguration.Blacklist.FALSE && list.contains(test.getKey().toLowerCase()))
                    result.add(test.getValue());
            }
        }

        return result;
    }
}
