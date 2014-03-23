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

package com.tagdynamics.png.chunk.types.zTXt;

import com.tagdynamics.png.PingGraphic;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class zTXtChunk extends AbstractChunk
{
    private String keyword;

    private int compressionMethod;

    private ByteArrayOutputStream compressedTextStream;

    public zTXtChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        ByteArrayOutputStream keywordStream = new ByteArrayOutputStream();
        compressedTextStream = new ByteArrayOutputStream();


        int i = 0;
        byte[] data = chunk.getData();
        int len = data.length;
        for (; i < len; i++)
        {
            if (0 == data[i])
            {
                i++;
                break;
            }

            keywordStream.write(data[i]);
        }

        try
        {
            keyword = new String(keywordStream.toByteArray(), PingGraphic.PNG_CHARSET);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        compressionMethod = chunk.getByte(i++);


        for (; i < len; i++)
        {
            compressedTextStream.write(data[i]);
        }
    }

    public ByteArrayOutputStream getCompressedTextStream()
    {
        return compressedTextStream;
    }

    public void setCompressedTextStream(ByteArrayOutputStream compressedTextStream)
    {
        this.compressedTextStream = compressedTextStream;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public int getCompressionMethod()
    {
        return compressionMethod;
    }

    public void setCompressionMethod(int compressionMethod)
    {
        this.compressionMethod = compressionMethod;
    }

    @Override
    public String toString()
    {
        return "zTXtChunk{" +
                "keyword='" + keyword + '\'' +
                ", compressionMethod=" + compressionMethod +
                ", compressedTextStreamLength=" + compressedTextStream.toByteArray().length +
                '}';
    }
}
