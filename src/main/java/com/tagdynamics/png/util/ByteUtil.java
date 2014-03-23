/*
 * Java Portable Network Graphics Library
 * (C) Copyright 2013-2014 Tag Dynamics, LLC (http://tagdynamics.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tagdynamics.png.util;

public final class ByteUtil
{
    private static final String HEX_VALUES = "0123456789ABCDEF";

    private ByteUtil()
    {
    }

    public static byte[] int2ToByteArray(int value)
    {
        return new byte[]{
                (byte) (value >>> 8),
                (byte) value};
    }

    public static byte[] int4ToByteArray(int value)
    {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    public static int byteArrayToInt4(byte[] b)
    {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
    }

    public static String toHex(byte[] raw)
    {
        if (raw == null)
        {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw)
        {
            hex.append(HEX_VALUES.charAt((b & 0xF0) >> 4))
                    .append(HEX_VALUES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    public static byte[] unsignedIntToByteArray(long value)
    {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};

//        byte[] bytes = null;
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        DataOutputStream daos = new DataOutputStream(baos);
//
//        try
//        {
//            daos.writeLong(value);
//            daos.flush();
//            bytes = newbaos.toByteArray();
//        } catch (IOException e)
//        {
//
//        }
//
//        return bytes;
    }
//
//    public static long byteArrayToLong(byte[] b)
//    {
//        long value = -1;
//
//        ByteArrayInputStream bais = new ByteArrayInputStream(b);
//        DataInputStream daos = new DataInputStream(bais);
//
//        try
//        {
//            value = daos.readLong();
//        } catch (IOException e)
//        {
//
//        }
//
//        return value;
//    }
}
