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

package com.tagdynamics.png.chunk.types.PLTE;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

import java.util.ArrayList;
import java.util.List;

public class PLTEChunk extends AbstractChunk
{
    private List<PaletteEntry> paletteEntries;

    public PLTEChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        paletteEntries = new ArrayList<PaletteEntry>();

        byte[] data = chunk.getData();
        int len = data.length;

        int validEntryCount = len % 3;
        if (0 != validEntryCount)
            throw new IllegalPaletteChunkLength("Palette data must be divisible by 3 (Section 11.2.3)");

        for (int i = 0; i < len; i += 3)
        {
            PaletteEntry paletteEntry = new PaletteEntry(data[i], data[i + 1], data[i + 2]);
            paletteEntries.add(paletteEntry);
        }
    }

    public List<PaletteEntry> getPaletteEntries()
    {
        return paletteEntries;
    }

    @Override
    public String toString()
    {
        return "PLTEChunk{" +
                "dataLength=" + chunk.getData().length +
                ", paletteEntriesSize=" + paletteEntries.size() +
                '}';
    }
}
