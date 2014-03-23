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

package com.tagdynamics.png.chunk.types.gAMA;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

public class gAMAChunk extends AbstractChunk
{
    private byte[] gama;

    public gAMAChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        gama = chunk.getData();
    }

    public byte[] getGama()
    {
        return gama;
    }

    public void setGama(byte[] gama)
    {
        this.gama = gama;
    }

    @Override
    public String toString()
    {
        return "gAMAChunk{" +
                "gama=" + gama[0] + ", " + gama[1] + ", " + gama[2] + ", " + gama[3] +
                ", gamaInt = " + chunk.getInt4(0) +
                '}';
    }
}
