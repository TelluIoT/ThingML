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
package org.thingml.devices;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by bmorin on 14.08.14.
 */
public class Dumper {

    private PrintWriter printer;
    private PrintWriter tPrinter;

    public Dumper() {
        try
        {
            printer = new PrintWriter("dump");
            tPrinter = new PrintWriter("transitionsCount");
        } catch (FileNotFoundException fnf) {
            System.err.println(fnf.getLocalizedMessage());
        }
        catch (Exception e) {

        }
    }

    public void write(String string) {
        printer.append(string);
        printer.flush();
    }

    public void stop(int transitionsCount) {
        tPrinter.append(Integer.toString(transitionsCount));
        tPrinter.flush();
        tPrinter.close();
        printer.close();
        //System.exit(0);
    }
}
