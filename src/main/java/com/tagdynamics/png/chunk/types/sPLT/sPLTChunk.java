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

package com.tagdynamics.png.chunk.types.sPLT;

import com.tagdynamics.png.PingGraphic;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class sPLTChunk extends AbstractChunk
{
    private String paletteName;

    private int sampleDepth;

    private List<SuggestedPalette> suggestedPalette;

    public sPLTChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        suggestedPalette = new ArrayList<SuggestedPalette>();

        ByteArrayOutputStream paletteNameStream = new ByteArrayOutputStream();

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

            paletteNameStream.write(data[i]);
        }

        try
        {
            paletteName = new String(paletteNameStream.toByteArray(), PingGraphic.PNG_CHARSET);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        sampleDepth = chunk.getInt1(i++);

        int entryCount = 0;
        int remainingBytes = (len - paletteName.length() - 2);
        switch (sampleDepth)
        {
            case 8:
                entryCount = remainingBytes / 6;
                break;
            case 16:
                entryCount = remainingBytes / 10;
                break;
        }


        for (int j = 0; i < entryCount; j++)
        {
            switch (sampleDepth)
            {
                case 8:
                    suggestedPalette.add(new SuggestedPalette(chunk.getInt1(i++), chunk.getInt1(i++), chunk.getInt1(i++), chunk.getInt1(i++), chunk.getInt2(i += 2)));
                    break;
                case 16:
                    suggestedPalette.add(new SuggestedPalette(chunk.getInt2(i += 2), chunk.getInt2(i += 2), chunk.getInt2(i += 2), chunk.getInt2(i += 2), chunk.getInt2(i += 2)));
                    break;
            }
        }
    }

    public List<SuggestedPalette> getSuggestedPalette()
    {
        return suggestedPalette;
    }

    public String getPaletteName()
    {
        return paletteName;
    }

    public void setPaletteName(String paletteName)
    {
        this.paletteName = paletteName;
    }

    public int getSampleDepth()
    {
        return sampleDepth;
    }

    public void setSampleDepth(int sampleDepth)
    {
        this.sampleDepth = sampleDepth;
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer(", suggestedPalette=");
        for (SuggestedPalette sp : suggestedPalette)
            buf.append(sp.toString()).append(" ");

        return "sPLTChunk{" +
                "paletteName='" + paletteName + '\'' +
                ", sampleDepth=" + sampleDepth +
                ", suggestedPaletteSize=" + suggestedPalette.size() +
                ", suggestedPalette=" + buf.toString() +
                '}';
    }
}
