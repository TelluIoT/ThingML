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
package org.thingml.networkplugins.java;

/**
 * Created by bmori on 21.10.2016.
 */
public class UnsignedHelper {

    /**
     * b is a C-like uint8
     * result is an always positive short, whose value is consistent with b interpreted as an uint8
     */
    public static short asUInt8(byte b) {
        return (short) (b & 0xFF);
    }

    /**
     * b is a C-like uint16
     * result is an always positive int, whose value is consistent with b interpreted as an uint8
     */
    public static int asUInt16(short b) {
        return (int) (b & 0xFFFF);
    }

    /**
     * b is a C-like uint32
     * result is an always positive int, whose value is consistent with b interpreted as an uint8
     */
    public static long asUInt32(int b) {
        return (long) (b & 0xFFFFFFFFL);
    }


    public static void main(String args[]) {
        final byte a = 1;
        final byte b = -1;
        final byte c = Byte.MAX_VALUE;
        final byte d = Byte.MIN_VALUE;
        final byte e = 0;

        System.out.println("toUInt8(" + a + ") = " + asUInt8(a));
        System.out.println("toUInt8(" + b + ") = " + asUInt8(b));
        System.out.println("toUInt8(" + c + ") = " + asUInt8(c));
        System.out.println("toUInt8(" + d + ") = " + asUInt8(d));
        System.out.println("toUInt8(" + e + ") = " + asUInt8(e));

        final short f = 1;
        final short g = -1;
        final short h = Short.MAX_VALUE;
        final short i = Short.MIN_VALUE;
        final short j = 0;

        System.out.println("toUInt16(" + f + ") = " + asUInt16(f));
        System.out.println("toUInt16(" + g + ") = " + asUInt16(g));
        System.out.println("toUInt16(" + h + ") = " + asUInt16(h));
        System.out.println("toUInt16(" + i + ") = " + asUInt16(i));
        System.out.println("toUInt16(" + j + ") = " + asUInt16(j));

        final int k = 1;
        final int l = -1;
        final int m = Integer.MAX_VALUE;
        final int n = Integer.MIN_VALUE;
        final int o = 0;

        System.out.println("toUInt32(" + k + ") = " + asUInt32(k));
        System.out.println("toUInt32(" + l + ") = " + asUInt32(l));
        System.out.println("toUInt32(" + m + ") = " + asUInt32(m));
        System.out.println("toUInt32(" + n + ") = " + asUInt32(n));
        System.out.println("toUInt32(" + o + ") = " + asUInt32(o));
    }
}
