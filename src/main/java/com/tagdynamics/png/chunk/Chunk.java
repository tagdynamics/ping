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

package com.tagdynamics.png.chunk;

import com.tagdynamics.png.api.ChunkCopySafety;
import com.tagdynamics.png.api.ChunkKind;
import com.tagdynamics.png.api.ChunkScope;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.api.IllegalChunkSignatureException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Chunk Types, as defined in Section 5.6 of the PNG Specification.
 */
final class Chunk implements IMutableChunk
{
    private byte[] signature;

    private String canonicalName;

    private ChunkKind kind;

    private ChunkScope scope;

    private ChunkCopySafety safety;

    private int length;

    private byte[] data;

    private long crc;

    /**
     * Constructs a PNG chunk type based on the signature byte array. Section 5.4 of the specification dictates that
     * chunk type signatures must be exactly 4 bytes in length and within a predefined set of values.
     *
     * @param signature Signature of the chunk type
     * @throws IllegalChunkSignatureException when the byte array does not conform to the .png specification
     */
    Chunk(byte[] signature) throws IllegalChunkSignatureException
    {
        setSignature(signature, false);
    }

    Chunk(int length, byte[] signature, byte[] data, long crc)
    {
        this(signature);
        this.length = length;
        this.data = data;
        this.crc = crc;
    }

    private void processSignature() throws IllegalChunkSignatureException
    {
        kind = ((signature[0] & (1L << 5)) == 0)
                ? ChunkKind.Critical
                : ChunkKind.Ancillary;


        scope = ((signature[1] & (1L << 5)) == 0)
                ? ChunkScope.Public
                : ChunkScope.Private;


        safety = ((signature[3] & (1L << 5)) == 0)
                ? ChunkCopySafety.Unsafe
                : ChunkCopySafety.Safe;
    }

    @Override
    public int getLength()
    {
        return length;
    }

    @Override
    public byte[] getData()
    {
        return data;
    }

    @Override
    public void setData(byte[] data)
    {
        this.data = data;
        this.length = data.length;
        recalculateCRC();
    }

    private void recalculateCRC()
    {
        CRC32 crc32 = new CRC32();
        crc32.update(signature);
        crc32.update(data);
        this.crc = crc32.getValue();
    }

    @Override
    public byte[] getBytes(int offset)
    {
        return Arrays.copyOfRange(data, offset, length);
    }

    @Override
    public ChunkKind getKind()
    {
        return kind;
    }

    @Override
    public ChunkScope getScope()
    {
        return scope;
    }

    @Override
    public ChunkCopySafety getSafety()
    {
        return safety;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getSignature()
    {
        return signature;
    }

    @Override
    public void setSignature(byte[] signature)
    {
        setSignature(signature, true);
    }

    private void setSignature(byte[] signature, boolean updateCRC)
    {
        if (null == signature || signature.length != 4)
            throw new IllegalChunkSignatureException("Parameter 'signatureBytes' must not be null, and must be of length 4");

        validateChunkTypeSignature(signature);

        this.signature = signature;

        processSignature();

        //
        // Always treated as ISO 646 characters, Section 5.4
        try
        {
            this.canonicalName = new String(signature, "US-ASCII");
        } catch (UnsupportedEncodingException e)
        {
            //
            // The validateChunkTypeSignature() guarantees that we won't get here
            //
        }

        if (updateCRC)
            recalculateCRC();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCanonicalName()
    {
        return canonicalName;
    }

    @Override
    public long getCrc()
    {
        return crc;
    }

    @Override
    public byte getByte(int offset)
    {
        return data[offset];
    }

    @Override
    public int getInt1(int offset)
    {
        return data[offset] & 0xff;
    }

    @Override
    public int getInt2(int offset)
    {
        return ((data[offset] & 0xff) << 8) |
                (data[offset + 1] & 0xff);
    }

    @Override
    public int getInt4(int offset)
    {
        return ((data[offset] & 0xff) << 24) |
                ((data[offset + 1] & 0xff) << 16) |
                ((data[offset + 2] & 0xff) << 8) |
                (data[offset + 3] & 0xff);
    }

    @Override
    public String getString4(int offset)
    {
        return ""
                + (char) data[offset]
                + (char) data[offset + 1]
                + (char) data[offset + 2]
                + (char) data[offset + 3];
    }

    @Override
    public int getUnsignedInt2(int offset)
    {
        int value = 0;
        for (int i = 0; i < 2; i++)
            value += (data[offset + i] & 0xff) << ((1 - i) * 8);
        return value;
    }

    @Override
    public long getUnsignedInt4(int offset)
    {
        long value = 0;
        for (int i = 0; i < 4; i++)
            value += (data[offset + i] & 0xff) << ((3 - i) * 8);
        return value;
    }

    @Override
    public int getUnsignedByte(int offset)
    {
        return (data[offset] & 0x00ff);
    }

    @Override
    public String toString()
    {
        return "Chunk{" +
                "canonicalName=" + this.getCanonicalName() +
                ", signature=" + signature[0] + ", " + signature[1] + ", " + signature[2] + ", " + signature[3] +
                ", kind=" + kind +
                ", scope=" + scope +
                ", safety=" + safety +
                ", dataLength=" + length +
                ", crc=" + crc +
                '}';
    }

    /**
     * Validates that the bytes conform to the PNG chunk naming specifications, Section 5.4.
     *
     * @param signature bytes representing a .png chunk signature (length of 4)
     * @throws IllegalChunkSignatureException when the byte array does not conform to the .png specification
     */
    public static void validateChunkTypeSignature(byte[] signature) throws IllegalChunkSignatureException
    {
        for (int i = 0; i < 4; i++)
        {
            int curByte = signature[i];

            if (!((curByte >= 65 && curByte <= 91) ||
                    (curByte >= 97 && curByte <= 122)))
            {
                throw new IllegalChunkSignatureException("Chunk type byte value outside of specification range: " +
                        curByte +
                        " (Section 5.3, Table 5.1, Chunk Type)");
            }
        }
    }

}
