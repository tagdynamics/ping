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

package com.tagdynamics.png.chunk.types.sBIT;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;
import com.tagdynamics.png.chunk.types.IHDR.ColorType;

public class sBITChunk extends AbstractChunk
{
    private int significantGrayscaleBits;

    private int significantTruecolorIndexedColorRedBits;

    private int significantTruecolorIndexedColorGreenBits;

    private int significantTruecolorIndexedColorBlueBits;

    private int significantGrayscaleAlphaGrayscaleBits;

    private int significantGrayscaleAlphaAlphaBits;

    private int significantTruecolorAlphaRedBits;

    private int significantTruecolorAlphaGreenBits;

    private int significantTruecolorAlphaBlueBits;

    private int significantTruecolorAlphaAlphaBits;

    public sBITChunk(ColorType colorType, IMutableChunk chunk)
    {
        super(colorType, chunk);
    }

    @Override
    protected void processChunk()
    {
        switch (colorType)
        {
            case Grayscale:
                significantGrayscaleBits = chunk.getByte(0);
                break;
            case Truecolor:
            case IndexedColor:
                significantTruecolorIndexedColorRedBits = chunk.getByte(0);
                significantTruecolorIndexedColorGreenBits = chunk.getByte(1);
                significantTruecolorIndexedColorBlueBits = chunk.getByte(2);
                break;
            case GrayscaleAlpha:
                significantGrayscaleAlphaGrayscaleBits = chunk.getByte(0);
                significantGrayscaleAlphaAlphaBits = chunk.getByte(1);
                break;
            case TruecolorAlpha:
                significantTruecolorAlphaRedBits = chunk.getByte(0);
                significantTruecolorAlphaGreenBits = chunk.getByte(1);
                significantTruecolorAlphaBlueBits = chunk.getByte(2);
                significantTruecolorAlphaAlphaBits = chunk.getByte(3);
                break;
        }
    }

    public int getSignificantGrayscaleBits()
    {
        return significantGrayscaleBits;
    }

    public void setSignificantGrayscaleBits(int significantGrayscaleBits)
    {
        this.significantGrayscaleBits = significantGrayscaleBits;
    }

    public int getSignificantTruecolorIndexedColorRedBits()
    {
        return significantTruecolorIndexedColorRedBits;
    }

    public void setSignificantTruecolorIndexedColorRedBits(int significantTruecolorIndexedColorRedBits)
    {
        this.significantTruecolorIndexedColorRedBits = significantTruecolorIndexedColorRedBits;
    }

    public int getSignificantTruecolorIndexedColorGreenBits()
    {
        return significantTruecolorIndexedColorGreenBits;
    }

    public void setSignificantTruecolorIndexedColorGreenBits(int significantTruecolorIndexedColorGreenBits)
    {
        this.significantTruecolorIndexedColorGreenBits = significantTruecolorIndexedColorGreenBits;
    }

    public int getSignificantTruecolorIndexedColorBlueBits()
    {
        return significantTruecolorIndexedColorBlueBits;
    }

    public void setSignificantTruecolorIndexedColorBlueBits(int significantTruecolorIndexedColorBlueBits)
    {
        this.significantTruecolorIndexedColorBlueBits = significantTruecolorIndexedColorBlueBits;
    }

    public int getSignificantGrayscaleAlphaGrayscaleBits()
    {
        return significantGrayscaleAlphaGrayscaleBits;
    }

    public void setSignificantGrayscaleAlphaGrayscaleBits(int significantGrayscaleAlphaGrayscaleBits)
    {
        this.significantGrayscaleAlphaGrayscaleBits = significantGrayscaleAlphaGrayscaleBits;
    }

    public int getSignificantGrayscaleAlphaAlphaBits()
    {
        return significantGrayscaleAlphaAlphaBits;
    }

    public void setSignificantGrayscaleAlphaAlphaBits(int significantGrayscaleAlphaAlphaBits)
    {
        this.significantGrayscaleAlphaAlphaBits = significantGrayscaleAlphaAlphaBits;
    }

    public int getSignificantTruecolorAlphaRedBits()
    {
        return significantTruecolorAlphaRedBits;
    }

    public void setSignificantTruecolorAlphaRedBits(int significantTruecolorAlphaRedBits)
    {
        this.significantTruecolorAlphaRedBits = significantTruecolorAlphaRedBits;
    }

    public int getSignificantTruecolorAlphaGreenBits()
    {
        return significantTruecolorAlphaGreenBits;
    }

    public void setSignificantTruecolorAlphaGreenBits(int significantTruecolorAlphaGreenBits)
    {
        this.significantTruecolorAlphaGreenBits = significantTruecolorAlphaGreenBits;
    }

    public int getSignificantTruecolorAlphaBlueBits()
    {
        return significantTruecolorAlphaBlueBits;
    }

    public void setSignificantTruecolorAlphaBlueBits(int significantTruecolorAlphaBlueBits)
    {
        this.significantTruecolorAlphaBlueBits = significantTruecolorAlphaBlueBits;
    }

    public int getSignificantTruecolorAlphaAlphaBits()
    {
        return significantTruecolorAlphaAlphaBits;
    }

    public void setSignificantTruecolorAlphaAlphaBits(int significantTruecolorAlphaAlphaBits)
    {
        this.significantTruecolorAlphaAlphaBits = significantTruecolorAlphaAlphaBits;
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("sBITChunk{");
        buf.append("colorType=" + colorType.toString());

        switch (colorType)
        {
            case Grayscale:
                buf.append(", significantGrayscaleBits=").append(significantGrayscaleBits);
                significantGrayscaleBits = chunk.getByte(0);
                break;
            case Truecolor:
            case IndexedColor:
                buf.append(", significantTruecolorIndexedColorRedBits=").append(significantTruecolorIndexedColorRedBits);
                buf.append(", significantTruecolorIndexedColorGreenBits=").append(significantTruecolorIndexedColorGreenBits);
                buf.append(", significantTruecolorIndexedColorBlueBits=").append(significantTruecolorIndexedColorBlueBits);
                break;
            case GrayscaleAlpha:
                buf.append(", significantGrayscaleAlphaGrayscaleBits=").append(significantGrayscaleAlphaGrayscaleBits);
                buf.append(", significantGrayscaleAlphaAlphaBits=").append(significantGrayscaleAlphaAlphaBits);
                break;
            case TruecolorAlpha:
                buf.append(", significantTruecolorAlphaRedBits=").append(significantTruecolorAlphaRedBits);
                buf.append(", significantTruecolorAlphaGreenBits=").append(significantTruecolorAlphaGreenBits);
                buf.append(", significantTruecolorAlphaBlueBits=").append(significantTruecolorAlphaBlueBits);
                buf.append(", significantTruecolorAlphaAlphaBits=").append(significantTruecolorAlphaAlphaBits);
                break;
        }

        buf.append("}");
        return buf.toString();
    }
}
