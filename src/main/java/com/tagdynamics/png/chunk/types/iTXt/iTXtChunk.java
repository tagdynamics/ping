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

package com.tagdynamics.png.chunk.types.iTXt;

import com.tagdynamics.png.PingGraphic;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class iTXtChunk extends AbstractChunk
{
    private String keyword;

    private String languageTag;

    private String translationKeyword;

    private String translatedValue;

    private int compressionFlag;

    private int compressionMethod;

    public iTXtChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        ByteArrayOutputStream keywordStream = new ByteArrayOutputStream();
        ByteArrayOutputStream languageStream = new ByteArrayOutputStream();
        ByteArrayOutputStream translationKeywordStream = new ByteArrayOutputStream();
        ByteArrayOutputStream translatedValueStream = new ByteArrayOutputStream();


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

        compressionFlag = chunk.getByte(i++);
        compressionMethod = chunk.getByte(i++);

        for (; i < len; i++)
        {
            if (0 == data[i])
            {
                i++;
                break;
            }

            languageStream.write(data[i]);
        }

        try
        {
            //
            // See Section 11.3.4.5 which dictactes the US-ASCII (ISO-646) encoding here
            //
            languageTag = new String(languageStream.toByteArray(), "US-ASCII");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }


        for (; i < len; i++)
        {
            if (0 == data[i])
            {
                i++;
                break;
            }

            translationKeywordStream.write(data[i]);
        }

        try
        {
            //
            // See Section 11.3.4.5 which dictactes the UTF-8 encoding here
            //
            translationKeyword = new String(translationKeywordStream.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        for (; i < len; i++)
        {
            if (0 == data[i])
            {
                i++;
                break;
            }

            translatedValueStream.write(data[i]);
        }

        try
        {
            //
            // See Section 11.3.4.5 which dictactes the US-ASCII (ISO-646) encoding here
            //
            translatedValue = new String(translatedValueStream.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getLanguageTag()
    {
        return languageTag;
    }

    public void setLanguageTag(String languageTag)
    {
        this.languageTag = languageTag;
    }

    public String getTranslationKeyword()
    {
        return translationKeyword;
    }

    public void setTranslationKeyword(String translationKeyword)
    {
        this.translationKeyword = translationKeyword;
    }

    public String getTranslatedValue()
    {
        return translatedValue;
    }

    public void setTranslatedValue(String translatedValue)
    {
        this.translatedValue = translatedValue;
    }

    public int getCompressionFlag()
    {
        return compressionFlag;
    }

    public void setCompressionFlag(int compressionFlag)
    {
        this.compressionFlag = compressionFlag;
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
        return "iTXtChunk{" +
                "keyword='" + keyword + '\'' +
                ", languageTag='" + languageTag + '\'' +
                ", translationKeyword='" + translationKeyword + '\'' +
                ", translatedValue='" + translatedValue + '\'' +
                ", compressionFlag=" + compressionFlag +
                ", compressionMethod=" + compressionMethod +
                '}';
    }
}
