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

public enum ColorType
{
    Grayscale(0),
    Truecolor(2),
    IndexedColor(3),
    GrayscaleAlpha(4),
    TruecolorAlpha(6);

    int colorType;

    ColorType(int colorType)
    {
        this.colorType = colorType;
    }

    public int getColorType()
    {
        return colorType;
    }

    @Override
    public String toString()
    {
        return "ColorType{" +
                "name=" + this.name() +
                ", colorType=" + colorType +
                '}';
    }

    public static ColorType fromByte(byte dataByte)
    {
        ColorType colorType = TruecolorAlpha;

        switch (dataByte)
        {
            case 0:
                colorType = Grayscale;
                break;
            case 2:
                colorType = Truecolor;
                break;
            case 3:
                colorType = IndexedColor;
                break;
            case 4:
                colorType = GrayscaleAlpha;
                break;
            case 6:
                colorType = TruecolorAlpha;
                break;
            default:
                throw new UnrecognizedColorTypeException(dataByte);
        }

        return colorType;
    }
}
