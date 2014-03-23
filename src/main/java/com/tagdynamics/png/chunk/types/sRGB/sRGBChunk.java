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

package com.tagdynamics.png.chunk.types.sRGB;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

public class sRGBChunk extends AbstractChunk
{
    private RenderingIntent renderingIntent;

    public sRGBChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        renderingIntent = RenderingIntent.fromByte(chunk.getByte(0));
    }

    public RenderingIntent getRenderingIntent()
    {
        return renderingIntent;
    }

    public void setRenderingIntent(RenderingIntent renderingIntent)
    {
        this.renderingIntent = renderingIntent;
    }

    @Override
    public String toString()
    {
        return "sRGBChunk{" +
                "renderingIntent=" + renderingIntent +
                '}';
    }
}
