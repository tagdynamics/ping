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

package com.tagdynamics.png.chunk;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.types.IHDR.ColorType;

/**
 * Base class for all critical and ancillary PNG chunks, including custom chunks.
 */
public abstract class AbstractChunk
{
    protected IMutableChunk chunk;

    protected ColorType colorType;

    protected AbstractChunk(ColorType colorType, IMutableChunk chunk)
    {
        this.colorType = colorType;
        this.chunk = chunk;
        processChunk();
    }

    protected AbstractChunk(IMutableChunk chunk)
    {
        this.chunk = chunk;
        processChunk();
    }

    public IMutableChunk getChunk()
    {
        return chunk;
    }


    /**
     * Process the current raw chunk found in chunk, assigning values to all of the pertinent chunk-specific
     * properties.
     */
    protected abstract void processChunk();
}
