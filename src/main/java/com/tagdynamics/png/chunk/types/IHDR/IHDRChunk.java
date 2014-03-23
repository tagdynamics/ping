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

package com.tagdynamics.png.chunk.types.IHDR;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

public class IHDRChunk extends AbstractChunk
{
    private int width;

    private int height;

    private BitDepth bitDepth;

    private ColorType colorType;

    private int compressionMethod;

    private int filterMethod;

    private InterlaceMethod interlaceMethod;

    public IHDRChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public BitDepth getBitDepth()
    {
        return bitDepth;
    }

    public void setBitDepth(BitDepth depth)
    {
        this.bitDepth = depth;

    }

    public ColorType getColorType()
    {
        return colorType;
    }

    public void setColorType(ColorType colorType)
    {
        this.colorType = colorType;
    }

    public int getCompressionMethod()
    {
        return compressionMethod;
    }

    public void setCompressionMethod(int compressionMethod)
    {
        this.compressionMethod = compressionMethod;
    }

    public int getFilterMethod()
    {
        return filterMethod;
    }

    public void setFilterMethod(int filterMethod)
    {
        this.filterMethod = filterMethod;
    }

    public InterlaceMethod getInterlaceMethod()
    {
        return interlaceMethod;
    }

    public void setInterlaceMethod(InterlaceMethod interlaceMethod)
    {
        this.interlaceMethod = interlaceMethod;
    }

    @Override
    protected void processChunk()
    {
        width = chunk.getInt4(0);
        height = chunk.getInt4(4);
        bitDepth = BitDepth.fromByte(chunk.getByte(8));
        colorType = ColorType.fromByte(chunk.getByte(9));
        compressionMethod = chunk.getInt1(10);
        filterMethod = chunk.getInt1(11);
        interlaceMethod = InterlaceMethod.fromByte(chunk.getByte(12));
    }

    @Override
    public String toString()
    {
        return "IHDRChunk{" +
                "width=" + width +
                ", height=" + height +
                ", bitDepth=" + bitDepth +
                ", colorType=" + colorType +
                ", compressionMethod=" + compressionMethod +
                ", filterMethod=" + filterMethod +
                ", interlaceMethod=" + interlaceMethod +
                '}';
    }
}
