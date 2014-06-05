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
package org.thingml.comm.rxtx;

import java.nio.ByteBuffer;

/**
 * Created by bmori on 08.05.2014.
 */
public class SerializationHelper {

    public static short toShort(byte bytes[]) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return bb.getShort();
    }

    public static int toInt(byte bytes[]) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return bb.getInt();
    }

    public static byte[] toBytes(short s) {
        ByteBuffer bb = ByteBuffer.allocate(Short.SIZE);
        bb.putShort(s);
        return bb.array();
    }

    public static byte[] toBytes(int i) {
        ByteBuffer bb = ByteBuffer.allocate(Short.SIZE);
        bb.putInt(i);
        return bb.array();
    }

    public static void main(String args[]) {
        short s = 3;
        short r = SerializationHelper.toShort(SerializationHelper.toBytes(s));

        System.out.println(r);
    }
}
