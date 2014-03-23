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

package com.tagdynamics.png.chunk.types.tRNS;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;
import com.tagdynamics.png.chunk.types.IHDR.ColorType;

import java.util.HashMap;
import java.util.Map;

public class tRNSChunk extends AbstractChunk
{
    private int grayscaleSample;

    private int redSample;

    private int greenSample;

    private int blueSample;

    private Map<Integer, Integer> paletteIndexSample;

    public tRNSChunk(ColorType colorType, IMutableChunk chunk)
    {
        super(colorType, chunk);
    }

    @Override
    protected void processChunk()
    {
        paletteIndexSample = new HashMap<Integer, Integer>();
        Integer counter = 0;

        switch (colorType)
        {
            case Grayscale:
                grayscaleSample = chunk.getInt2(0);
                break;
            case Truecolor:
                redSample = chunk.getInt2(0);
                greenSample = chunk.getInt2(2);
                blueSample = chunk.getInt2(4);
            case IndexedColor:
                byte[] data = chunk.getData();
                int len = data.length;
                for (int i = 0; i < len; i++)
                {
                    paletteIndexSample.put(counter++, chunk.getInt1(i));
                }
                break;
        }

    }

    public Map<Integer, Integer> getPaletteIndexSample()
    {
        return paletteIndexSample;
    }

    public int getGrayscaleSample()
    {
        return grayscaleSample;
    }

    public void setGrayscaleSample(int grayscaleSample)
    {
        this.grayscaleSample = grayscaleSample;
    }

    public int getRedSample()
    {
        return redSample;
    }

    public void setRedSample(int redSample)
    {
        this.redSample = redSample;
    }

    public int getGreenSample()
    {
        return greenSample;
    }

    public void setGreenSample(int greenSample)
    {
        this.greenSample = greenSample;
    }

    public int getBlueSample()
    {
        return blueSample;
    }

    public void setBlueSample(int blueSample)
    {
        this.blueSample = blueSample;
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("tRNSChunk{");
        buf.append("colorType=").append(colorType.toString());

        switch (colorType)
        {
            case Grayscale:
                buf.append(", grayscaleSample=").append(grayscaleSample);
                break;
            case Truecolor:
                buf.append(", redSample=").append(redSample);
                buf.append(", greenSample=").append(greenSample);
                buf.append(", blueSample=").append(blueSample);
                break;
            case IndexedColor:
                buf.append(", dataLength=").append(chunk.getData().length);
                buf.append(", paletteSampleSize=").append(paletteIndexSample.size());
                break;
        }

        buf.append("}");
        return buf.toString();
    }
}
