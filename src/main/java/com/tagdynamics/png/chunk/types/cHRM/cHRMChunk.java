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

package com.tagdynamics.png.chunk.types.cHRM;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

public class cHRMChunk extends AbstractChunk
{
    int whitePointX;

    int whitePointY;

    int redX;

    int redY;

    int greenX;

    int greenY;

    int blueX;

    int blueY;

    public cHRMChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        whitePointX = chunk.getInt4(0);
        whitePointY = chunk.getInt4(4);
        redX = chunk.getInt4(8);
        redY = chunk.getInt4(12);
        greenX = chunk.getInt4(16);
        greenY = chunk.getInt4(20);
        blueX = chunk.getInt4(24);
        blueY = chunk.getInt4(28);
    }

    public int getWhitePointX()
    {
        return whitePointX;
    }

    public void setWhitePointX(int whitePointX)
    {
        this.whitePointX = whitePointX;
    }

    public int getWhitePointY()
    {
        return whitePointY;
    }

    public void setWhitePointY(int whitePointY)
    {
        this.whitePointY = whitePointY;
    }

    public int getRedX()
    {
        return redX;
    }

    public void setRedX(int redX)
    {
        this.redX = redX;
    }

    public int getRedY()
    {
        return redY;
    }

    public void setRedY(int redY)
    {
        this.redY = redY;
    }

    public int getGreenX()
    {
        return greenX;
    }

    public void setGreenX(int greenX)
    {
        this.greenX = greenX;
    }

    public int getGreenY()
    {
        return greenY;
    }

    public void setGreenY(int greenY)
    {
        this.greenY = greenY;
    }

    public int getBlueX()
    {
        return blueX;
    }

    public void setBlueX(int blueX)
    {
        this.blueX = blueX;
    }

    public int getBlueY()
    {
        return blueY;
    }

    public void setBlueY(int blueY)
    {
        this.blueY = blueY;
    }

    @Override
    public String toString()
    {
        return "cHRMChunk{" +
                "whitePointX=" + whitePointX +
                ", whitePointY=" + whitePointY +
                ", redX=" + redX +
                ", redY=" + redY +
                ", greenX=" + greenX +
                ", greenY=" + greenY +
                ", blueX=" + blueX +
                ", blueY=" + blueY +
                '}';
    }
}
