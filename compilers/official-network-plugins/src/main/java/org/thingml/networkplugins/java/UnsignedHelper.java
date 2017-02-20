/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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
     * b is a C-like uint8, wrapped as a positive Java short
     * result is a Java byte, whose binary value is consistent with b interpreted as an uint8
     */
    public static byte toUInt8(short b) {return (byte) (b & 0xFF);}

    /**
     * b is a C-like uint16
     * result is an always positive int, whose value is consistent with b interpreted as an uint8
     */
    public static int asUInt16(short b) {
        return (int) (b & 0xFFFF);
    }

    /**
     * b is a C-like uint16, wrapped as a positive Java int
     * result is a Java short, whose binary value is consistent with b interpreted as an uint16
     */
    public static short toUInt16(int b) {return (short) (b & 0xFFFF);}

    /**
     * b is a C-like uint32
     * result is an always positive int, whose value is consistent with b interpreted as an uint8
     */
    public static long asUInt32(int b) {
        return (long) (b & 0xFFFFFFFFL);
    }

    /**
     * b is a C-like uint32, wrapped as a positive Java long
     * result is a Java int, whose binary value is consistent with b interpreted as an uint32
     */
    public static int toUInt32(long b) {return (int) (b & 0xFFFFFFFFL);}


    public static void main(String args[]) {
        final byte a = 1;
        final byte b = -1;
        final byte c = Byte.MAX_VALUE;
        final byte d = Byte.MIN_VALUE;
        final byte e = 0;

        System.out.println("asUInt8(" + a + ") = " + asUInt8(a));
        System.out.println("asUInt8(" + b + ") = " + asUInt8(b));
        System.out.println("asUInt8(" + c + ") = " + asUInt8(c));
        System.out.println("asUInt8(" + d + ") = " + asUInt8(d));
        System.out.println("asUInt8(" + e + ") = " + asUInt8(e));


        System.out.println(a + " = " + toUInt8(asUInt8(a)));
        System.out.println(b + " = " + toUInt8(asUInt8(b)));
        System.out.println(c + " = " + toUInt8(asUInt8(c)));
        System.out.println(d + " = " + toUInt8(asUInt8(d)));
        System.out.println(e + " = " + toUInt8(asUInt8(e)));

        final short f = 1;
        final short g = -1;
        final short h = Short.MAX_VALUE;
        final short i = Short.MIN_VALUE;
        final short j = 0;

        System.out.println("asUInt16(" + f + ") = " + asUInt16(f));
        System.out.println("asUInt16(" + g + ") = " + asUInt16(g));
        System.out.println("asUInt16(" + h + ") = " + asUInt16(h));
        System.out.println("asUInt16(" + i + ") = " + asUInt16(i));
        System.out.println("asUInt16(" + j + ") = " + asUInt16(j));

        System.out.println(f + " = " + toUInt16(asUInt16(f)));
        System.out.println(g + " = " + toUInt16(asUInt16(g)));
        System.out.println(h + " = " + toUInt16(asUInt16(h)));
        System.out.println(i + " = " + toUInt16(asUInt16(i)));
        System.out.println(j + " = " + toUInt16(asUInt16(j)));

        final int k = 1;
        final int l = -1;
        final int m = Integer.MAX_VALUE;
        final int n = Integer.MIN_VALUE;
        final int o = 0;

        System.out.println("asUInt32(" + k + ") = " + asUInt32(k));
        System.out.println("asUInt32(" + l + ") = " + asUInt32(l));
        System.out.println("asUInt32(" + m + ") = " + asUInt32(m));
        System.out.println("asUInt32(" + n + ") = " + asUInt32(n));
        System.out.println("asUInt32(" + o + ") = " + asUInt32(o));

        System.out.println(k + " = " + toUInt32(asUInt32(k)));
        System.out.println(l + " = " + toUInt32(asUInt32(l)));
        System.out.println(m + " = " + toUInt32(asUInt32(m)));
        System.out.println(n + " = " + toUInt32(asUInt32(n)));
        System.out.println(o + " = " + toUInt32(asUInt32(o)));
    }
}
