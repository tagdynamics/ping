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

package com.tagdynamics.png.chunk.types.pHYs;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;
import com.tagdynamics.png.chunk.types.cHRM.UnitType;

public class pHYsChunk extends AbstractChunk
{
    long xAxisPixelsPerUnit;

    long yAxisPixelsPerUnit;

    UnitType unitType;

    public pHYsChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        xAxisPixelsPerUnit = chunk.getUnsignedInt4(0);
        yAxisPixelsPerUnit = chunk.getUnsignedInt4(4);
        unitType = (chunk.getByte(8) == 0) ? UnitType.Unknown : UnitType.Meter;
    }

    public long getxAxisPixelsPerUnit()
    {
        return xAxisPixelsPerUnit;
    }

    public void setxAxisPixelsPerUnit(long xAxisPixelsPerUnit)
    {
        this.xAxisPixelsPerUnit = xAxisPixelsPerUnit;
    }

    public long getyAxisPixelsPerUnit()
    {
        return yAxisPixelsPerUnit;
    }

    public void setyAxisPixelsPerUnit(long yAxisPixelsPerUnit)
    {
        this.yAxisPixelsPerUnit = yAxisPixelsPerUnit;
    }

    public UnitType getUnitType()
    {
        return unitType;
    }

    public void setUnitType(UnitType unitType)
    {
        this.unitType = unitType;
    }

    @Override
    public String toString()
    {
        return "pHYsChunk{" +
                "xAxisPixelsPerUnit=" + xAxisPixelsPerUnit +
                ", yAxisPixelsPerUnit=" + yAxisPixelsPerUnit +
                ", unitType=" + unitType +
                '}';
    }
}
