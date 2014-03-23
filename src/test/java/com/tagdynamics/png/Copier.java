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

import java.io.*;
import java.util.Random;

public class Copier
{
    public static void main(String[] args)
    {
        PingDecoder decoder = new PingDecoder();

        File file = new File(args[0]);
        File outFile = new File(args[1]);

        Random rnd = new Random();

        InputStream is = null;
        OutputStream os = null;
        try
        {
            is = new FileInputStream(file);
            PingGraphic graphic = decoder.decodePing(is);

            //
            // Example of adding IDAT chunks
            //

//            for (int i = 0; i < 5; i++)
//            {
//                IMutableChunk chunk = ChunkFactory.createChunkType(CriticalChunkType.IDAT);
//                byte[] bytes = new byte[graphic.getIdatChunks().get(0).getChunk().getData().length];
//                rnd.nextBytes(bytes);
//                chunk.setData(bytes);
//                IDATChunk idatChunk = new IDATChunk(chunk);
//                graphic.getIdatChunks().add(idatChunk);
//            }

            os = new FileOutputStream(outFile);
            PingEncoder encoder = new PingEncoder();
            encoder.encode(graphic, os);
            os.flush();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (CorruptedChunkException e)
        {
            e.printStackTrace();
        } catch (UnrecognizedPingSignatureException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (null != is)
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            if (null != os)
                try
                {
                    os.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

}
