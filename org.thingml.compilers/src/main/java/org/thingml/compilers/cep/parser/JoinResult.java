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
package org.thingml.compilers.cep.parser;

/**
 * @author ludovic
 */
public class JoinResult {
    private String idEvt1;
    private String idEvt2;
    private String timeMS;
    private String funcValueName;

    public JoinResult(String idEvt1, String idEvt2, String timeMS, String funcValueName) {
        this.idEvt1 = idEvt1;
        this.idEvt2 = idEvt2;
        this.timeMS = timeMS;
        this.funcValueName = funcValueName;
    }

    public String getIdEvt1() {
        return idEvt1;
    }

    public String getIdEvt2() {
        return idEvt2;
    }

    public String getTimeMS() {
        return timeMS;
    }

    public String getFuncValueName() {
        return funcValueName;
    }
}
