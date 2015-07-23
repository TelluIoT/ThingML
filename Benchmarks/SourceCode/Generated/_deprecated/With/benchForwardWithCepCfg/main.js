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
var StreamWithCep = require('./StreamWithCep');
var Sender = require('./Sender');
var Receiver = require('./Receiver');
var benchForwardWithCepCfg_streamS = new StreamWithCep();
benchForwardWithCepCfg_streamS.setThis(benchForwardWithCepCfg_streamS);
benchForwardWithCepCfg_streamS.build();
var benchForwardWithCepCfg_sender = new Sender(10000, 10000, 10000);
benchForwardWithCepCfg_sender.setThis(benchForwardWithCepCfg_sender);
benchForwardWithCepCfg_sender.build();
var benchForwardWithCepCfg_receiver = new Receiver(0);
benchForwardWithCepCfg_receiver.setThis(benchForwardWithCepCfg_receiver);
benchForwardWithCepCfg_receiver.build();
benchForwardWithCepCfg_receiver.getRedoonsenderServiceListeners().push(benchForwardWithCepCfg_sender.receiveredoOnsenderService.bind(benchForwardWithCepCfg_sender));
benchForwardWithCepCfg_streamS.getCep_forwardMessageoncepServiceListeners().push(benchForwardWithCepCfg_receiver.receivecep_forwardMessageOncepService.bind(benchForwardWithCepCfg_receiver));
benchForwardWithCepCfg_streamS.getCep_forwardMessageWithParamsoncepServiceListeners().push(benchForwardWithCepCfg_receiver.receivecep_forwardMessageWithParamsOncepService.bind(benchForwardWithCepCfg_receiver));
benchForwardWithCepCfg_streamS.getCep_forwardWithModifoncepServiceListeners().push(benchForwardWithCepCfg_receiver.receivecep_forwardWithModifOncepService.bind(benchForwardWithCepCfg_receiver));
benchForwardWithCepCfg_streamS.getCep_endBenchoncepServiceListeners().push(benchForwardWithCepCfg_receiver.receivecep_endBenchOncepService.bind(benchForwardWithCepCfg_receiver));
benchForwardWithCepCfg_sender.getForwardMessageonsenderCepServiceListeners().push(benchForwardWithCepCfg_streamS.receiveforwardMessageOnsenderService.bind(benchForwardWithCepCfg_streamS));
benchForwardWithCepCfg_sender.getForwardMessageWithParamsonsenderCepServiceListeners().push(benchForwardWithCepCfg_streamS.receiveforwardMessageWithParamsOnsenderService.bind(benchForwardWithCepCfg_streamS));
benchForwardWithCepCfg_sender.getForwardWithModifonsenderCepServiceListeners().push(benchForwardWithCepCfg_streamS.receiveforwardWithModifOnsenderService.bind(benchForwardWithCepCfg_streamS));
benchForwardWithCepCfg_sender.getEndBenchonsenderCepServiceListeners().push(benchForwardWithCepCfg_streamS.receiveendBenchOnsenderService.bind(benchForwardWithCepCfg_streamS));
benchForwardWithCepCfg_sender._init();
benchForwardWithCepCfg_receiver._init();
benchForwardWithCepCfg_streamS._init();
//terminate all things on SIGINT (e.g. CTRL+C)
process.on('SIGINT', function() {
console.log("Stopping components... CTRL+D to force shutdown");
benchForwardWithCepCfg_receiver._stop();
benchForwardWithCepCfg_streamS._stop();
benchForwardWithCepCfg_sender._stop();
});

