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
package org.thingml.documentrospection;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author sintef
 */
public class Annotation implements Comparable{
    String name;
    String category;
    Set<String> placeFound;
    Set<File> placeFoundFile;
    Set<String> samples;
    public boolean isValueUsed;
    
    public Annotation(String name) {
        this.name = name;
        this.placeFound = new HashSet<>();
        this.placeFoundFile = new HashSet<>();
        this.samples = new HashSet<>();
        this.isValueUsed = false;
        if(name.contains("_")) {
            category = name.split("_")[0];
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void addPlaceFound(File f) {
        for(String s : placeFound) {
            if(s.compareTo(f.getPath()) == 0) return;
        }
        placeFound.add(f.getPath());
        placeFoundFile.add(f);
    }
    
    public void addSample(File f) {
        for(String s : samples) {
            if(s.compareTo(f.getPath()) == 0) return;
        }
        samples.add(f.getPath());
    }
    
    public static Annotation getAnnotationByName(Set<Annotation> annotations, String name) {
        for(Annotation a : annotations) {
            if(a.name.compareTo(name) == 0) return a;
        }
        return null;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Annotation)
            return ((Annotation)o).getName().compareTo(this.getName());
        else return -1;
    }
}
