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

public enum BitDepth
{
    One(1),
    Two(2),
    Four(4),
    Eight(8),
    Sixteen(16);

    int depth;

    BitDepth(int depth)
    {
        this.depth = depth;
    }

    @Override
    public String toString()
    {
        return "BitDepth{" +
                "name=" + this.name() +
                ", depth=" + depth +
                '}';
    }

    public int getBitDepth()
    {
        return depth;
    }

    public static BitDepth fromByte(byte bitDepthByte) throws UnrecognizedBitDepthException
    {
        BitDepth depth = Sixteen;
        switch (bitDepthByte)
        {
            case 1:
                depth = One;
                break;
            case 2:
                depth = Two;
                break;
            case 4:
                depth = Four;
                break;
            case 8:
                depth = Eight;
                break;
            case 16:
                depth = Sixteen;
                break;
            default:
                throw new UnrecognizedBitDepthException(bitDepthByte);
        }

        return depth;
    }
}
