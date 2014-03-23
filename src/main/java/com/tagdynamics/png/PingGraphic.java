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

package com.tagdynamics.png;

import com.tagdynamics.png.api.AncillaryChunkType;
import com.tagdynamics.png.api.CriticalChunkType;
import com.tagdynamics.png.api.IMutableChunk;
import com.tagdynamics.png.chunk.types.IDAT.IDATChunk;
import com.tagdynamics.png.chunk.types.IEND.IENDChunk;
import com.tagdynamics.png.chunk.types.IHDR.IHDRChunk;
import com.tagdynamics.png.chunk.types.PLTE.PLTEChunk;
import com.tagdynamics.png.chunk.types.bKGD.bKGDChunk;
import com.tagdynamics.png.chunk.types.cHRM.cHRMChunk;
import com.tagdynamics.png.chunk.types.gAMA.gAMAChunk;
import com.tagdynamics.png.chunk.types.hIST.hISTChunk;
import com.tagdynamics.png.chunk.types.iCCP.iCCPChunk;
import com.tagdynamics.png.chunk.types.iTXt.iTXtChunk;
import com.tagdynamics.png.chunk.types.pHYs.pHYsChunk;
import com.tagdynamics.png.chunk.types.sBIT.sBITChunk;
import com.tagdynamics.png.chunk.types.sPLT.sPLTChunk;
import com.tagdynamics.png.chunk.types.sRGB.sRGBChunk;
import com.tagdynamics.png.chunk.types.tEXt.tEXtChunk;
import com.tagdynamics.png.chunk.types.tIME.tIMEChunk;
import com.tagdynamics.png.chunk.types.tRNS.tRNSChunk;
import com.tagdynamics.png.chunk.types.zTXt.zTXtChunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Concrete representation of a PNG graphic.
 */
public final class PingGraphic
{
    private static final Logger logger = Logger.getLogger(PingGraphic.class.getCanonicalName());

    /**
     * Every PingDecoder graphics file must begin with the same eight bytes, {137, 80, 78, 71, 13, 10, 26, 10}
     */
    public static final long PNG_SIGNATURE = 0x89504e470d0a1a0aL;

    private List<IMutableChunk> chunks;

    //
    //
    // Refer to Figure 5.2 and Figure 5.3 to differentiate cardinality between PNG images with PLTE in datastream
    // versus PNG images without PLTE in datastream
    //
    //

    //
    // Exactly one
    private IHDRChunk ihdrChunk = null;

    //
    // Zero or more
    List<sPLTChunk> spltChunks = new ArrayList<sPLTChunk>();

    List<zTXtChunk> ztxtChunks = new ArrayList<zTXtChunk>();

    List<tEXtChunk> textChunks = new ArrayList<tEXtChunk>();

    List<iTXtChunk> itxtChunks = new ArrayList<iTXtChunk>();

    //
    // Zero or one
    private pHYsChunk physChunk = null;

    private tIMEChunk timeChunk = null;

    private sBITChunk sbitChunk = null;

    private gAMAChunk gamaChunk = null;

    private cHRMChunk chrmChunk = null;

    //
    // Either or
    private iCCPChunk iccpChunk = null;

    private sRGBChunk srgbChunk = null;

    //
    // Exactly one, if iCCP | sRGB, sBIT, gAMA, cHRM is present
    private PLTEChunk plteChunk = null;

    //
    // Zero or one, if PLTE chunk is present
    private tRNSChunk trnsChunk = null;

    private hISTChunk histChunk = null;

    private bKGDChunk bkgdChunk = null;

    //
    // One or more
    private List<IDATChunk> idatChunks = new ArrayList<IDATChunk>();

    private List<IMutableChunk> customChunks = new ArrayList<IMutableChunk>();


    //
    // Exactly one
    private IENDChunk iendChunk = null;

    /**
     * See Section 11.3.4.3
     */
    public static final String PNG_CHARSET = "ISO-8859-1";


    public PingGraphic(List<IMutableChunk> chunks)
    {
        this.chunks = chunks;
        processChunks();
    }

    public boolean hasPLTEChunk()
    {
        return (null != plteChunk);
    }


    private void processChunks()
    {
        for (IMutableChunk curChunk : chunks)
        {
            byte[] chunkTypeBytes = curChunk.getSignature();

            if (Arrays.equals(chunkTypeBytes, CriticalChunkType.IHDR.getSignature()))
            {
                ihdrChunk = new IHDRChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(ihdrChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, CriticalChunkType.PLTE.getSignature()))
            {
                plteChunk = new PLTEChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(plteChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, CriticalChunkType.IDAT.getSignature()))
            {
                IDATChunk chunk = new IDATChunk(curChunk);
                idatChunks.add(chunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(chunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, CriticalChunkType.IEND.getSignature()))
            {
                iendChunk = new IENDChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(iendChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.sRGB.getSignature()))
            {
                srgbChunk = new sRGBChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(srgbChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.gAMA.getSignature()))
            {
                gamaChunk = new gAMAChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(gamaChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.cHRM.getSignature()))
            {
                chrmChunk = new cHRMChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(chrmChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.tEXt.getSignature()))
            {
                tEXtChunk chunk = new tEXtChunk(curChunk);
                textChunks.add(chunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(chunk.toString());
            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.pHYs.getSignature()))
            {
                physChunk = new pHYsChunk(curChunk);


                if (logger.isLoggable(Level.FINE))
                    logger.fine(physChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.tIME.getSignature()))
            {
                timeChunk = new tIMEChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(timeChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.sPLT.getSignature()))
            {
                sPLTChunk chunk = new sPLTChunk(curChunk);
                spltChunks.add(chunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(chunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.iCCP.getSignature()))
            {
                iccpChunk = new iCCPChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(iccpChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.sBIT.getSignature()))
            {
                sbitChunk = new sBITChunk(ihdrChunk.getColorType(), curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(sbitChunk.toString());
            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.bKGD.getSignature()))
            {
                bkgdChunk = new bKGDChunk(ihdrChunk.getColorType(), curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(bkgdChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.hIST.getSignature()))
            {
                histChunk = new hISTChunk(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(histChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.tRNS.getSignature()))
            {
                trnsChunk = new tRNSChunk(ihdrChunk.getColorType(), curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(trnsChunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.iTXt.getSignature()))
            {
                iTXtChunk chunk = new iTXtChunk(curChunk);
                itxtChunks.add(chunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(chunk.toString());

            } else if (Arrays.equals(chunkTypeBytes, AncillaryChunkType.zTXt.getSignature()))
            {
                zTXtChunk chunk = new zTXtChunk(curChunk);
                ztxtChunks.add(chunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine(chunk.toString());

            } else
            {
                customChunks.add(curChunk);

                if (logger.isLoggable(Level.FINE))
                    logger.fine("Custom Chunk: " + curChunk.toString());

            }
        }
    }

    public IHDRChunk getIhdrChunk()
    {
        return ihdrChunk;
    }

    public List<sPLTChunk> getSpltChunks()
    {
        return spltChunks;
    }

    public List<zTXtChunk> getZtxtChunks()
    {
        return ztxtChunks;
    }

    public List<tEXtChunk> getTextChunks()
    {
        return textChunks;
    }

    public List<iTXtChunk> getItxtChunks()
    {
        return itxtChunks;
    }

    public pHYsChunk getPhysChunk()
    {
        return physChunk;
    }

    public tIMEChunk getTimeChunk()
    {
        return timeChunk;
    }

    public sBITChunk getSbitChunk()
    {
        return sbitChunk;
    }

    public gAMAChunk getGamaChunk()
    {
        return gamaChunk;
    }

    public cHRMChunk getChrmChunk()
    {
        return chrmChunk;
    }

    public iCCPChunk getIccpChunk()
    {
        return iccpChunk;
    }

    public sRGBChunk getSrgbChunk()
    {
        return srgbChunk;
    }

    public PLTEChunk getPlteChunk()
    {
        return plteChunk;
    }

    public tRNSChunk getTrnsChunk()
    {
        return trnsChunk;
    }

    public hISTChunk getHistChunk()
    {
        return histChunk;
    }

    public bKGDChunk getBkgdChunk()
    {
        return bkgdChunk;
    }

    public List<IDATChunk> getIdatChunks()
    {
        return idatChunks;
    }

    public List<IMutableChunk> getCustomChunks()
    {
        return customChunks;
    }

    public IENDChunk getIendChunk()
    {
        return iendChunk;
    }

    public void setIhdrChunk(IHDRChunk ihdrChunk)
    {
        this.ihdrChunk = ihdrChunk;
    }

    public void setPhysChunk(pHYsChunk physChunk)
    {
        this.physChunk = physChunk;
    }

    public void setTimeChunk(tIMEChunk timeChunk)
    {
        this.timeChunk = timeChunk;
    }

    public void setSbitChunk(sBITChunk sbitChunk)
    {
        this.sbitChunk = sbitChunk;
    }

    public void setGamaChunk(gAMAChunk gamaChunk)
    {
        this.gamaChunk = gamaChunk;
    }

    public void setChrmChunk(cHRMChunk chrmChunk)
    {
        this.chrmChunk = chrmChunk;
    }

    public void setIccpChunk(iCCPChunk iccpChunk)
    {
        this.iccpChunk = iccpChunk;
    }

    public void setSrgbChunk(sRGBChunk srgbChunk)
    {
        this.srgbChunk = srgbChunk;
    }

    public void setPlteChunk(PLTEChunk plteChunk)
    {
        this.plteChunk = plteChunk;
    }

    public void setTrnsChunk(tRNSChunk trnsChunk)
    {
        this.trnsChunk = trnsChunk;
    }

    public void setHistChunk(hISTChunk histChunk)
    {
        this.histChunk = histChunk;
    }

    public void setBkgdChunk(bKGDChunk bkgdChunk)
    {
        this.bkgdChunk = bkgdChunk;
    }

    public void setIendChunk(IENDChunk iendChunk)
    {
        this.iendChunk = iendChunk;
    }

    @Override
    public String toString()
    {
        return "PingGraphic{" +
                "chunksCount=" + chunks.size() +
                ", ihdrChunk=" + ihdrChunk.toString() +
                '}';
    }
}
