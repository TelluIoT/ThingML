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
package org.thingml.externalthingplugins.c.posix;

import org.sintef.thingml.Configuration;
import org.thingml.compilers.Context;
import org.thingml.compilers.spi.ExternalThingPlugin;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingImplCompiler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vassik on 21.10.16.
 */
public class PosixDNSSDExternalThingPlugin extends ExternalThingPlugin {

    final String supportedTypeId = "DNSSD";

    @Override
    public String getSupportedExternalThingTypeID() {
        return supportedTypeId;
    }

    @Override
    public List<String> getTargetedLanguages() {
        List<String> languages = new ArrayList<String>();
        languages.add("posix");
        languages.add("posixmt");
        return languages;
    }

    @Override
    public ThingApiCompiler getThingApiCompiler() {
        return null;
    }

    @Override
    public ThingImplCompiler getThingImplCompiler() {
        return null;
    }

    @Override
    public void generateExternalLibrary(Configuration cfg, Context ctx) {

    }
}
