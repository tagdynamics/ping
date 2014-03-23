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

public enum RenderingIntent
{
    Perceptual(0),
    RelativeColorimetric(1),
    Saturation(2),
    AbsoluteColorimetric(3);

    int intent;

    RenderingIntent(int intent)
    {
        this.intent = intent;
    }

    public int getIntent()
    {
        return intent;
    }

    @Override
    public String toString()
    {
        return "RenderingIntent{" +
                "name=" + this.name() +
                ", intent=" + intent +
                '}';
    }

    public static RenderingIntent fromByte(byte intentByte)
    {
        RenderingIntent renderingIntent = Perceptual;

        switch (intentByte)
        {
            case 0:
                renderingIntent = Perceptual;
                break;
            case 1:
                renderingIntent = RelativeColorimetric;
                break;
            case 2:
                renderingIntent = Saturation;
                break;
            case 3:
                renderingIntent = AbsoluteColorimetric;
                break;
            default:
                throw new UnrecognizedRenderingIntentException(intentByte);
        }

        return renderingIntent;
    }

}
