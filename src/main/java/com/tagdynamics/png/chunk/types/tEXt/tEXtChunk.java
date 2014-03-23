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

package com.tagdynamics.png.chunk.types.tEXt;

import com.tagdynamics.png.PingGraphic;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class tEXtChunk extends AbstractChunk
{

    private String key;

    private String value;

    public tEXtChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        ByteArrayOutputStream keywordStream = new ByteArrayOutputStream();
        ByteArrayOutputStream valueStream = new ByteArrayOutputStream();

        boolean foundNull = false;
        for (byte b : chunk.getData())
        {
            if (0 == b)
            {
                foundNull = true;
                continue;
            }

            if (!foundNull)
            {
                keywordStream.write(b);
            } else
            {
                valueStream.write(b);
            }
        }

        try
        {
            key = new String(keywordStream.toByteArray(), PingGraphic.PNG_CHARSET);
            value = new String(valueStream.toByteArray(), PingGraphic.PNG_CHARSET);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "tEXtChunk{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
