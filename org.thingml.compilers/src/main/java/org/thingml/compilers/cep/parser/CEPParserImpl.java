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
public class CEPParserImpl implements CEPParser {
   /* @Override
    public ParseResult parse(String annotationValue) {
        ParseResult result = new ParseResult();
        String[] values = annotationValue.split(" ");

        for(String v : values) {
            if (v.contains("merge")) {
                result.addMergedEvents(parseMerge(v));
            } else if(v.contains("join")) {
                result.setJoinResult(parseJoin(v));
            } else {
                result.addEventToStream(Integer.valueOf(v.trim()));
            }
        }
        return result;
    }

    private JoinResult parseJoin(String joinValues) {

        joinValues = cleanString(joinValues,"join(",")");

        //idEvt1,idEvt2,timeInMS,funcName
        String[] values = joinValues.split(",");
        JoinResult result = new JoinResult(values[0],values[1],values[2],values[3]);

        return result;
    }

    private List<Integer> parseMerge(String values) {
        List<Integer> result = new ArrayList<>();

        values = cleanString(values,"merge","(",")");
        String[] sId = values.split(",");

        for (String s : sId) {
            result.add(Integer.valueOf(s.trim()));
        }

        return result;
    }

    private String cleanString(String toClean, String... toEliminate) {
        for (String s : toEliminate) {
            toClean = toClean.replace(s,"");
        }

        return toClean;
    }*/
}
