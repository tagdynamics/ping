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

package com.tagdynamics.png.chunk.types.hIST;

import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

import java.util.HashMap;
import java.util.Map;

public class hISTChunk extends AbstractChunk
{
    private Map<Integer, Integer> histogram;

    public hISTChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        histogram = new HashMap<Integer, Integer>();
        Integer counter = 0;

        byte[] data = chunk.getData();
        int len = data.length;

        for (int i = 0; i < len; i += 2)
        {
            histogram.put(counter++, chunk.getUnsignedInt2(i));
        }
    }

    public Map<Integer, Integer> getHistogram()
    {
        return histogram;
    }

    @Override
    public String toString()
    {
        return "hISTChunk{" +
                "dataLength=" + chunk.getData().length +
                ", histogramSize=" + histogram.size() +
                '}';
    }
}
