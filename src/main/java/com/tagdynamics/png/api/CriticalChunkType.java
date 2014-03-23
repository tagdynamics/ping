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

package com.tagdynamics.png.api;

public enum CriticalChunkType
{
    //
    // Critical Chunks
    //
    IHDR(new byte[]{73, 72, 68, 82}),
    PLTE(new byte[]{80, 76, 84, 69}),
    IDAT(new byte[]{73, 68, 65, 84}),
    IEND(new byte[]{73, 69, 78, 68});


    private byte[] signature;

    CriticalChunkType(byte[] signature)
    {
        this.signature = signature;
    }

    public byte[] getSignature()
    {
        return signature;
    }

    @Override
    public String toString()
    {
        return "CriticalChunkType{" +
                "name=" + name() +
                ", signature={" + signature[0] + ", " + signature[1] + ", " + signature[2] + ", " + signature[3] + "}" +
                '}';
    }
}
