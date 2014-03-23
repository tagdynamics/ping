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

package com.tagdynamics.png.chunk.types.iCCP;

import com.tagdynamics.png.PingGraphic;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.AbstractChunk;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class iCCPChunk extends AbstractChunk
{
    private String profileName;

    private byte[] compressedProfile;

    private int compressionMethod;

    public iCCPChunk(IMutableChunk chunk)
    {
        super(chunk);
    }

    @Override
    protected void processChunk()
    {
        ByteArrayOutputStream profileNameStream = new ByteArrayOutputStream();

        int i = 0;
        byte[] data = chunk.getData();
        int len = data.length;
        for (; i < len; i++)
        {
            if (0 == data[i])
            {
                i++;
                break;
            }

            profileNameStream.write(data[i]);
        }

        try
        {
            profileName = new String(profileNameStream.toByteArray(), PingGraphic.PNG_CHARSET);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        compressionMethod = data[++i];
        compressedProfile = chunk.getBytes(++i);
    }

    public String getProfileName()
    {
        return profileName;
    }

    public void setProfileName(String profileName)
    {
        this.profileName = profileName;
    }

    public byte[] getCompressedProfile()
    {
        return compressedProfile;
    }

    public void setCompressedProfile(byte[] compressedProfile)
    {
        this.compressedProfile = compressedProfile;
    }

    public int getCompressionMethod()
    {
        return compressionMethod;
    }

    public void setCompressionMethod(int compressionMethod)
    {
        this.compressionMethod = compressionMethod;
    }

    @Override
    public String toString()
    {
        return "iCCPChunk{" +
                "profileName='" + profileName + '\'' +
                ", compressionMethod=" + compressionMethod +
                ", compressedProfile=" + compressedProfile.length + " bytes " +
                '}';
    }
}
