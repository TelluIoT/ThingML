/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.generated.network;

import java.util.ArrayList;
import java.util.Arrays;

//This Class is extracted (and enhanced) from Apache ArrayUtils, distributed under Apache License 2.0
public class JavaBinaryHelper {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];

    public static final byte ESC = 0x7D;
    public static final byte NULL = 0x00;
    public static final byte ESC_NULL = 0x48;

    public static final java.util.List<Byte> toEscape = new java.util.ArrayList<>();

    static {
        toEscape.add(ESC);
        toEscape.add(NULL);
    }

    public static byte[] toPrimitive(final Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    public static Byte[] toObject(final byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        final Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Byte.valueOf(array[i]);
        }
        return result;
    }

    public static byte[] escape(final byte[] array) {
        int newSize = array.length;
        for (byte b : array) {
            if (toEscape.contains(b))
                newSize++;
        }
        final byte[] result = new byte[newSize];
        int i = 0;
        for (byte b : array) {
            if (toEscape.contains(b)) {
                result[i] = ESC;
                i++;
            }
            result[i] = b;
            i++;
        }
        return result;
    }

    public static byte[] unescape(final byte[] array) {
        final byte[] buffer = new byte[array.length];
        final int RCV_MSG = 1;
        final int RCV_ESC = 2;
        int buffer_idx = 0;
        int state = RCV_MSG;
        for(byte data : array) {
            if (state == RCV_MSG) {
                if (data == ESC) {
                    state = RCV_ESC;
                } else { // it is just a byte to store
                    buffer[buffer_idx] = data;
                    buffer_idx++;
                }
            } else if (state == RCV_ESC) {
                // Store the byte without looking at it
                buffer[buffer_idx] = data;
                buffer_idx++;
                state = RCV_MSG;
            }
        }
        return java.util.Arrays.copyOf(buffer, buffer_idx);
    }
}//see https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/ArrayUtils.java