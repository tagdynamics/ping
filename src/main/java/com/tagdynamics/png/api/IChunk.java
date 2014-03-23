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

package com.tagdynamics.png.api;

public interface IChunk
{
    /**
     * Signature of the chunk type.
     *
     * @return signature byte array (always 4 bytes per the specification)
     */
    byte[] getSignature();

    /**
     * Textual representation of the chunk type signature.
     *
     * @return Textual representation of the signature
     */
    String getCanonicalName();

    ChunkKind getKind();

    ChunkScope getScope();

    ChunkCopySafety getSafety();

    int getLength();

    byte[] getData();

    long getCrc();

    byte getByte(int offset);

    byte[] getBytes(int offset);

    int getUnsignedByte(int offset);

    int getInt1(int offset);

    int getInt2(int offset);

    int getInt4(int offset);

    String getString4(int offset);

    int getUnsignedInt2(int offset);

    long getUnsignedInt4(int offset);
}
