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
package org.thingml.compilers.spi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.validation.Checker;


/**
 * Created by vassik on 21.10.16.
 */
public abstract class ExternalThingPlugin {

    protected Map<String, PlatformAnnotation> annotationMap;

    public ExternalThingPlugin() {
        annotationMap = new HashMap<>();
    }

    public abstract String getSupportedExternalThingTypeID();

    public abstract List<String> getTargetedLanguages();

    public String getPluginID() { return this.getClass().getSimpleName(); }

    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.NOTICE;
    }

    public String getExternalThingAnnotation(String name) {
        PlatformAnnotation annotation = annotationMap.get(name);
        return (annotation != null) ? annotation.getValue() : null;
    }

    public void addExternalThingAnnotations(Set<PlatformAnnotation> annotations) {
        for(PlatformAnnotation annotation : annotations)
            annotationMap.put(annotation.getName(), annotation);
    }


    public String getName() {
        return this.getPluginID() + " plugin's rules";
    }


    public String getDescription() {
        return "Check that " + this.getPluginID() + " plugin can be used.";
    }

    /*  START
        Should be overridden if the plugin needs to perform
     * some specific checking.
    */

    public void check(Configuration cfg, Checker checker) {

    }

    public abstract ThingApiCompiler getThingApiCompiler();

    public abstract ThingImplCompiler getThingImplCompiler();

    public abstract CfgMainGenerator getCfgMainGenerator();

    public abstract CfgBuildCompiler getCfgBuildCompiler();

    public abstract void generateExternalLibrary(Configuration cfg, Context ctx);

    public static String calculateExternalThingTypeID(Thing thing) {
        //this should be adjusted when we decide how we distinguish normal things from the external
        //for now it is the annotation @external 'protocal name', e.g. @external 'DNSSD'
        List<String> values = AnnotatedElementHelper.annotation(thing, "external");
        return (values.size() != 0) ? values.iterator().next() : null;
    }

    public static Boolean isExternalThing(Thing thing) {
        //this should be adjusted when we decide how we distinguish normal things from the external
        //for now it is the annotation @external 'protocal name', e.g. @external 'DNSSD'
        List<String> values = AnnotatedElementHelper.annotation(thing, "external");
        return (values.size() != 0) ? true : false;
    }

    public static List<Thing> getAllExternalThings(List<Thing> things) {
        List<Thing> ext_things = new ArrayList<Thing>();
        for(Thing thing: things)
            if(isExternalThing(thing))
                ext_things.add(thing);

        return ext_things;
    }



}
