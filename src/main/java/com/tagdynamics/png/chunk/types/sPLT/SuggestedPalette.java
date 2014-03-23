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

package com.tagdynamics.png.chunk.types.sPLT;

public class SuggestedPalette
{
    private int frequency;

    private int red;

    private int green;

    private int blue;

    private int alpha;

    public SuggestedPalette(int red, int green, int blue, int alpha, int frequency)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.frequency = frequency;
    }

    public int getFrequency()
    {
        return frequency;
    }

    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
    }

    public int getAlpha()
    {
        return alpha;
    }

    @Override
    public String toString()
    {
        return "SuggestedPalette{" +
                "frequency=" + frequency +
                ", red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", alpha=" + alpha +
                '}';
    }
}
