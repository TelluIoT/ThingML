/*
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
var Sample = require('./Sample');
var sampleCfg_sample = new Sample();
sampleCfg_sample.setThis(sampleCfg_sample);
sampleCfg_sample.build();
sampleCfg_sample.getM1onsendPortListeners().push(sampleCfg_sample.receivem1OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getM2onsendPortListeners().push(sampleCfg_sample.receivem2OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getM3onsendPortListeners().push(sampleCfg_sample.receivem3OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getM4onsendPortListeners().push(sampleCfg_sample.receivem4OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getM5onsendPortListeners().push(sampleCfg_sample.receivem5OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getM6onsendPortListeners().push(sampleCfg_sample.receivem6OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getM7onsendPortListeners().push(sampleCfg_sample.receivem7OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getM8onsendPortListeners().push(sampleCfg_sample.receivem8OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getCep1onsendPortListeners().push(sampleCfg_sample.receivecep1OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getCep2onsendPortListeners().push(sampleCfg_sample.receivecep2OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getCep3onsendPortListeners().push(sampleCfg_sample.receivecep3OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getCep4onsendPortListeners().push(sampleCfg_sample.receivecep4OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample.getCep5onsendPortListeners().push(sampleCfg_sample.receivecep5OnrcvPort.bind(sampleCfg_sample));
sampleCfg_sample._init();
//terminate all things on SIGINT (e.g. CTRL+C)
process.on('SIGINT', function() {
console.log("Stopping components... CTRL+D to force shutdown");
sampleCfg_sample._stop();
});

