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

import com.tagdynamics.png.api.AncillaryChunkType;
import com.tagdynamics.png.api.CorruptedChunkException;
import com.tagdynamics.png.api.CriticalChunkType;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.api.IllegalChunkSignatureException;

/**
 * Creates either well-defined critical or ancillary chunk types, or creates custom PNG chunk types.
 */
public final class ChunkFactory
{
    private ChunkFactory()
    {
    }

    /**
     * Creates a well-defined critical chunk type.
     *
     * @param chunkType Critical chunk type to instantiate
     * @return Chunk type shell corresponding to the critical chunk type without length, data, or CRC
     */
    public static IMutableChunk createChunkType(CriticalChunkType chunkType)
    {
        return new Chunk(chunkType.getSignature());
    }

    /**
     * Creates a well-defined ancillary chunk type.
     *
     * @param chunkType Ancillary chunk type to instantiate
     * @return Chunk type shell corresponding to the ancillary chunk type without length, data, or CRC
     */
    public static IMutableChunk createChunkType(AncillaryChunkType chunkType)
    {
        return new Chunk(chunkType.getSignature());
    }

    /**
     * Creates a custom chunk type. The chunk type signature must conform to the specification documented in Section 5.4
     * of the PNG specification.
     *
     * @param signature Chunk type signature.
     * @return Custom chunk type shell without length, data, or CRC
     * @throws IllegalChunkSignatureException when the signature does not conform to the specification
     */
    public static IMutableChunk createChunkType(byte[] signature) throws IllegalChunkSignatureException
    {
        return new Chunk(signature);
    }

    /**
     * Creates a chunk based on the chunk layout defined in Section 5.3 of the PNG specification.
     *
     * @param length    A four-byte unsigned integer giving the number of bytes in the chunk's data field. The length
     *                  counts only the data field, not itself, the chunk type, or the CRC. Zero is a valid length.
     * @param signature A four-byte sequence defining the chunk type
     * @param data      Data bytes appropriate to the chunk type, if any. This field can be of zero length.
     * @param crc       A four-byte CRC calculated on the preceding bytes in the chunk, including the chunk type field
     *                  and chunk data fields, but not including the length field.
     * @return Complete chunk type representing the chunk
     * @throws CorruptedChunkException when the chunk header fields are corrupted
     */
    public static IMutableChunk createChunkType(int length, byte[] signature, byte[] data, long crc)
            throws CorruptedChunkException
    {
        return new Chunk(length, signature, data, crc);
    }

}
