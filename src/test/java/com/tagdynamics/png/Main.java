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
import com.tagdynamics.png.api.IllegalChunkSignatureException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class.getCanonicalName());

    public static void main(String[] args)
    {
        PingDecoder decoder = new PingDecoder();

        File file = new File("basn2c16.png");

        if (logger.isLoggable(Level.FINE))
            logger.fine("Processing file " + file.getAbsolutePath());

        InputStream is = null;
        try
        {
            is = new FileInputStream(file);
            PingGraphic graphic = decoder.decodePing(is);
            System.out.println(graphic.getIhdrChunk().getColorType());
            System.out.println(graphic.getIhdrChunk().getWidth() + " x " + graphic.getIhdrChunk().getHeight());
            System.out.println(graphic.getIdatChunks().size());
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (UnrecognizedPingSignatureException e)
        {
            e.printStackTrace();
        } catch (IllegalChunkSignatureException e)
        {
            e.printStackTrace();
        } catch (CorruptedChunkException e)
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
        }
    }
}