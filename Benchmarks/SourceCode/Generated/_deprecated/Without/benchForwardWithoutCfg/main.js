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
var StreamWithoutCEP = require('./StreamWithoutCEP');
var Sender = require('./Sender');
var Receiver = require('./Receiver');
var benchForwardWithoutCfg_sender = new Sender(10000, 10000, 10000);
benchForwardWithoutCfg_sender.setThis(benchForwardWithoutCfg_sender);
benchForwardWithoutCfg_sender.build();
var benchForwardWithoutCfg_streamWithoutCep = new StreamWithoutCEP();
benchForwardWithoutCfg_streamWithoutCep.setThis(benchForwardWithoutCfg_streamWithoutCep);
benchForwardWithoutCfg_streamWithoutCep.build();
var benchForwardWithoutCfg_receiver = new Receiver(0);
benchForwardWithoutCfg_receiver.setThis(benchForwardWithoutCfg_receiver);
benchForwardWithoutCfg_receiver.build();
benchForwardWithoutCfg_receiver.getRedoonsenderServiceListeners().push(benchForwardWithoutCfg_sender.receiveredoOnsenderService.bind(benchForwardWithoutCfg_sender));
benchForwardWithoutCfg_streamWithoutCep.getC_forwardMessageoncServiceListeners().push(benchForwardWithoutCfg_receiver.receivec_forwardMessageOncService.bind(benchForwardWithoutCfg_receiver));
benchForwardWithoutCfg_streamWithoutCep.getC_forwardMessageWithParamsoncServiceListeners().push(benchForwardWithoutCfg_receiver.receivec_forwardMessageWithParamsOncService.bind(benchForwardWithoutCfg_receiver));
benchForwardWithoutCfg_streamWithoutCep.getC_forwardWithModifoncServiceListeners().push(benchForwardWithoutCfg_receiver.receivec_forwardWithModifOncService.bind(benchForwardWithoutCfg_receiver));
benchForwardWithoutCfg_streamWithoutCep.getC_endBenchoncServiceListeners().push(benchForwardWithoutCfg_receiver.receivec_endBenchOncService.bind(benchForwardWithoutCfg_receiver));
benchForwardWithoutCfg_sender.getForwardMessageonsenderServiceListeners().push(benchForwardWithoutCfg_streamWithoutCep.receiveforwardMessageOnsenderService.bind(benchForwardWithoutCfg_streamWithoutCep));
benchForwardWithoutCfg_sender.getForwardMessageWithParamsonsenderServiceListeners().push(benchForwardWithoutCfg_streamWithoutCep.receiveforwardMessageWithParamsOnsenderService.bind(benchForwardWithoutCfg_streamWithoutCep));
benchForwardWithoutCfg_sender.getForwardWithModifonsenderServiceListeners().push(benchForwardWithoutCfg_streamWithoutCep.receiveforwardWithModifOnsenderService.bind(benchForwardWithoutCfg_streamWithoutCep));
benchForwardWithoutCfg_sender.getEndBenchonsenderServiceListeners().push(benchForwardWithoutCfg_streamWithoutCep.receiveendBenchOnsenderService.bind(benchForwardWithoutCfg_streamWithoutCep));
benchForwardWithoutCfg_receiver._init();
benchForwardWithoutCfg_sender._init();
benchForwardWithoutCfg_streamWithoutCep._init();
//terminate all things on SIGINT (e.g. CTRL+C)
process.on('SIGINT', function() {
console.log("Stopping components... CTRL+D to force shutdown");
benchForwardWithoutCfg_streamWithoutCep._stop();
benchForwardWithoutCfg_sender._stop();
benchForwardWithoutCfg_receiver._stop();
});

