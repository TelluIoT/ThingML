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

import java.util.*;

/**
 * @author ludovic
 */
public class ParseResult {
    private List<Integer> idEventToStream;

    private int id;
    private Map<Integer,List<Integer>> idMergedEvent;

    private JoinResult joinResult;

    public ParseResult() {
        this.idEventToStream = new ArrayList<>();
        this.idMergedEvent = new HashMap<>();
        this.id = 0;
    }

    public void addEventToStream(int id) {
        idEventToStream.add(id);
    }

    public void addMergedEvents(List<Integer> ids) {
        idMergedEvent.put(id++,ids);
    }

    public List<Integer> getIdEventToStream() {
        List<Integer> result = new ArrayList<>();

        for (int i : idEventToStream) {
            result.add(i);
        }

        return result;
    }

    public Map<Integer, List<Integer>> getIdMergedEvent() {
        Map<Integer,List<Integer>> result = new HashMap<>();

        for(Map.Entry<Integer, List<Integer>> entry : idMergedEvent.entrySet()) {
            List<Integer> list = new ArrayList<>();

            for (Integer i : entry.getValue()) {
                list.add(i);
            }
            result.put(entry.getKey(),list);
        }

        return result;
    }

    public Optional<JoinResult> getJoinResult() {
        return Optional.ofNullable(joinResult);
    }

    public void setJoinResult(JoinResult joinResult) {
        this.joinResult = joinResult;
    }
}
