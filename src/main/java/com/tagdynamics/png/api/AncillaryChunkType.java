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

public enum AncillaryChunkType
{
    //
    // Ancillary Chunks
    //
    cHRM(new byte[]{99, 72, 82, 77}),
    gAMA(new byte[]{103, 65, 77, 65}),
    iCCP(new byte[]{105, 67, 67, 80}),
    sBIT(new byte[]{115, 66, 73, 84}),
    sRGB(new byte[]{115, 82, 71, 66}),
    bKGD(new byte[]{98, 75, 71, 68}),
    hIST(new byte[]{104, 73, 83, 84}),
    tRNS(new byte[]{116, 82, 78, 83}),
    pHYs(new byte[]{112, 72, 89, 115}),
    sPLT(new byte[]{115, 80, 76, 84}),
    tIME(new byte[]{116, 73, 77, 69}),
    iTXt(new byte[]{105, 84, 88, 116}),
    tEXt(new byte[]{116, 69, 88, 116}),
    zTXt(new byte[]{122, 84, 88, 116});

//    //
//    // The test suite appears to be based upon a 1996 draft specification :-(
//    // See ftp://ftp.simplesystems.org/pub/libpng/png-group/documents/history/png-proposed-sPLTChunk-19961107.html#spAL
//    spAL(new byte[]{115, 112, 65, 76}),


    private byte[] signature;

    AncillaryChunkType(byte[] signature)
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
        return "AncillaryChunkType{" +
                "name=" + name() +
                ", signature={" + signature[0] + ", " + signature[1] + ", " + signature[2] + ", " + signature[3] + "}" +
                '}';
    }


}
