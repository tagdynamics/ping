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

package com.tagdynamics.png.chunk.types.bKGD;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;
import com.tagdynamics.png.chunk.types.IHDR.ColorType;

public class bKGDChunk extends AbstractChunk
{
    private int grayscale;

    private int red;

    private int green;

    private int blue;

    private int paletteIndex;

    public bKGDChunk(ColorType colorType, IMutableChunk chunk)
    {
        super(colorType, chunk);
    }

    @Override
    protected void processChunk()
    {
        switch (colorType)
        {
            case Grayscale:
            case GrayscaleAlpha:
                grayscale = chunk.getByte(0);
                break;
            case Truecolor:
            case TruecolorAlpha:
                red = chunk.getByte(0);
                green = chunk.getByte(1);
                blue = chunk.getByte(2);
                break;
            case IndexedColor:
                paletteIndex = chunk.getByte(0);
                break;
        }
    }

    public int getGrayscale()
    {
        return grayscale;
    }

    public void setGrayscale(int grayscale)
    {
        this.grayscale = grayscale;
    }

    public int getRed()
    {
        return red;
    }

    public void setRed(int red)
    {
        this.red = red;
    }

    public int getGreen()
    {
        return green;
    }

    public void setGreen(int green)
    {
        this.green = green;
    }

    public int getBlue()
    {
        return blue;
    }

    public void setBlue(int blue)
    {
        this.blue = blue;
    }

    public int getPaletteIndex()
    {
        return paletteIndex;
    }

    public void setPaletteIndex(int paletteIndex)
    {
        this.paletteIndex = paletteIndex;
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("bKGDChunk{");
        buf.append("colorType=").append(colorType.toString());

        switch (colorType)
        {
            case Grayscale:
            case GrayscaleAlpha:
                buf.append(", grayscale=").append(grayscale);
                break;
            case Truecolor:
            case TruecolorAlpha:
                buf.append(", red=").append(red);
                buf.append(", green=").append(green);
                buf.append(", blue=").append(blue);
                break;
            case IndexedColor:
                buf.append(", paletteIndex=").append(paletteIndex);
                break;
        }

        buf.append("}");
        return buf.toString();
    }
}
