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

package com.tagdynamics.png;

import com.tagdynamics.png.api.CorruptedChunkException;
import com.tagdynamics.png.api.IChunk;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.api.IllegalChunkSignatureException;
import com.tagdynamics.png.chunk.ChunkFactory;
import com.tagdynamics.png.util.ByteUtil;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;

/**
 * Decodes an input stream pointing to a PNG graphic into a PingGraphic object for manipulation.
 */
public final class PingDecoder
{
    /**
     * Logging
     */
    private static final Logger logger = Logger.getLogger(PingDecoder.class.getCanonicalName());

    private List<IMutableChunk> chunks = new ArrayList<IMutableChunk>();

    public PingGraphic decodePing(InputStream in) throws IOException, UnrecognizedPingSignatureException,
            IllegalChunkSignatureException, CorruptedChunkException
    {
        DataInputStream dis = new DataInputStream(in);

        //
        // Verify that the input stream is pointing to a ping graphic
        verifySignature(dis);

        if (logger.isLoggable(Level.FINE))
            logger.fine("Valid PNG file signature found");

        //
        // Read all of the chunks from the ping graphic
        readChunks(dis, 1);

        if (logger.isLoggable(Level.FINE))
            logger.fine("Total Chunks Found: " + chunks.size());


        return new PingGraphic(chunks);
    }

    /**
     * Recursive method that reads all of the ping chunks.
     *
     * @param dis          ping data input stream
     * @param currentChunk current chunk being processed
     * @throws IOException                                            when the file couldn't be read
     * @throws com.tagdynamics.png.api.IllegalChunkSignatureException when an invalid chunk types dis detected
     * @throws com.tagdynamics.png.api.CorruptedChunkException        when the chunk's CRC dis invalid
     */
    private void readChunks(DataInputStream dis, int currentChunk) throws IOException, IllegalChunkSignatureException, CorruptedChunkException
    {
        //
        // Section 5.3
        //

        try
        {
            //
            // Read the chunk chunkLength
            //
            int chunkLength = dis.readInt();
            if (chunkLength < 0)
                throw new IllegalChunkSignatureException("chunk chunkLength was a negative value");


            byte[] signature = new byte[4];
            byte[] data = new byte[chunkLength];

            //
            // Read the chunk types
            //
            dis.readFully(signature);

            if (logger.isLoggable(Level.FINEST))
            {
                IChunk chunk = ChunkFactory.createChunkType(signature);
                logger.finest("Processing chunk #" + currentChunk + " " + chunk.getCanonicalName() + " chunk of length " + chunkLength);
            }

            //
            // Read the chunk data, if there dis data in this chunk
            //
            if (chunkLength > 0)
            {
                dis.readFully(data);

            }

            //
            // Read the CRC
            //
            long crc = dis.readInt() & 0x00000000ffffffffL; // Make it unsigned.

            if (logger.isLoggable(Level.FINEST))
            {
                logger.finest("Found CRC value of " + crc);
                logger.finest(ByteUtil.toHex(data));
            }

            if (!verifyCRC(signature, data, crc))
                throw new CorruptedChunkException("chunk " + currentChunk + " is corrupted (invalid CRC)");

            IMutableChunk chunk = ChunkFactory.createChunkType(chunkLength, signature, data, crc);
            chunks.add(chunk);

            if (logger.isLoggable(Level.FINEST))
                logger.finest(chunk.toString());

            //
            // Read the next chunk
            readChunks(dis, ++currentChunk);

        } catch (EOFException eofe)
        {
            //
            // All done- no more chunks to read; end the recursive descent
            //
        }
    }


    private void verifySignature(DataInputStream is) throws UnrecognizedPingSignatureException, IOException
    {
        //
        // Section 5.2
        //

        if (is.readLong() != PingGraphic.PNG_SIGNATURE)
            throw new UnrecognizedPingSignatureException("PingGraphic signature not found in the first eight bytes");
    }


    private boolean verifyCRC(byte[] typeBytes, byte[] data, long crc)
    {
        CRC32 crc32 = new CRC32();
        crc32.update(typeBytes);
        crc32.update(data);
        long calculated = crc32.getValue();

        if (logger.isLoggable(Level.FINE))
            logger.fine("Calculated CRC: " + calculated);

        return (calculated == crc);
    }


}
