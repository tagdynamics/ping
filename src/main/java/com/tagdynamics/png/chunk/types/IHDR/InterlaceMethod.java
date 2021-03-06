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

public enum InterlaceMethod
{
    None,
    Adam7;

    public static InterlaceMethod fromByte(byte interlaceByte)
    {
        InterlaceMethod method = None;

        switch (interlaceByte)
        {
            case 0:
                method = None;
                break;
            case 1:
                method = Adam7;
                break;
            default:
                throw new UnrecognizedInterlaceMethodException(interlaceByte);
        }

        return method;
    }
}
