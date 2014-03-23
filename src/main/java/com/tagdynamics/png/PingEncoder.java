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
import com.tagdynamics.png.chunk.types.IHDR.ColorType;
import com.tagdynamics.png.chunk.types.PLTE.PaletteEntry;
import com.tagdynamics.png.chunk.types.iTXt.iTXtChunk;
import com.tagdynamics.png.chunk.types.sPLT.SuggestedPalette;
import com.tagdynamics.png.chunk.types.sPLT.sPLTChunk;
import com.tagdynamics.png.chunk.types.tEXt.tEXtChunk;
import com.tagdynamics.png.chunk.types.zTXt.zTXtChunk;
import com.tagdynamics.png.util.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encodes a PNG Graphic according to the PNG specification lattice diagrams defined in Section 5.6, Chunk Ordering.
 *
 * @see com.tagdynamics.png.PingGraphic
 */
public class PingEncoder
{
    private static final Logger logger = Logger.getLogger(PingEncoder.class.getCanonicalName());

    private DataOutputStream out;

    private PingGraphic graphic;

    public void encode(PingGraphic graphic, OutputStream out) throws IOException
    {
        this.out = new DataOutputStream(out);
        this.graphic = graphic;

        writeFileSignature();

        write_IHDR();
        write_tIME();
        write_zTXt();
        write_tEXt();
        write_iTXt();
        write_pHYs();
        write_sPLT();
        write_iCCP_or_sRGB();
        write_sBIT();
        write_gAMA();
        write_cHRM();

        if (graphic.hasPLTEChunk())
        {
            if (logger.isLoggable(Level.FINE))
                logger.fine("PNG image with PLTE in the datastream");

            write_PLTE();
            write_tRNS();
            write_hIST();
            write_bKGD();
        } else
        {
            write_tRNS();
            write_bKGD();
        }

        write_Custom();
        write_IDAT();
        write_IEND();
    }

    private void write_Custom() throws IOException
    {
        for (IMutableChunk chunk : graphic.getCustomChunks())
        {
            out.write(ByteUtil.unsignedIntToByteArray(chunk.getLength()));
            out.write(chunk.getSignature());
            out.write(chunk.getData());
            out.write(ByteUtil.unsignedIntToByteArray(chunk.getCrc()));
        }
    }

    private void write_IEND() throws IOException
    {
        out.write(ByteUtil.unsignedIntToByteArray(0));
        out.write(CriticalChunkType.IEND.getSignature());
        out.write(ByteUtil.unsignedIntToByteArray(graphic.getIendChunk().getChunk().getCrc()));
    }

    private void write_IDAT() throws IOException
    {
        for (IDATChunk chunk : graphic.getIdatChunks())
        {
            out.write(ByteUtil.unsignedIntToByteArray(chunk.getChunk().getData().length));
            out.write(CriticalChunkType.IDAT.getSignature());
            out.write(chunk.getChunk().getData());

            out.write(ByteUtil.unsignedIntToByteArray(chunk.getChunk().getCrc()));
        }
    }

    private void write_bKGD() throws IOException
    {
        if (null != graphic.getBkgdChunk())
        {
            ColorType colorType = graphic.getIhdrChunk().getColorType();

            switch (colorType)
            {
                case Grayscale:
                case GrayscaleAlpha:
                    out.write(ByteUtil.unsignedIntToByteArray(2));
                    out.write(AncillaryChunkType.bKGD.getSignature());
                    out.write(ByteUtil.int2ToByteArray(graphic.getBkgdChunk().getGrayscale()));
                    break;
                case Truecolor:
                case TruecolorAlpha:
                    out.write(ByteUtil.unsignedIntToByteArray(6));
                    out.write(AncillaryChunkType.bKGD.getSignature());
                    out.write(ByteUtil.int2ToByteArray(graphic.getBkgdChunk().getRed()));
                    out.write(ByteUtil.int2ToByteArray(graphic.getBkgdChunk().getGreen()));
                    out.write(ByteUtil.int2ToByteArray(graphic.getBkgdChunk().getBlue()));
                    break;
                case IndexedColor:
                    out.write(ByteUtil.unsignedIntToByteArray(1));
                    out.write(AncillaryChunkType.bKGD.getSignature());
                    out.writeByte(graphic.getBkgdChunk().getPaletteIndex());
                    break;
            }

            out.write(ByteUtil.unsignedIntToByteArray(graphic.getBkgdChunk().getChunk().getCrc()));
        }
    }

    private void write_hIST() throws IOException
    {
        if (null != graphic.getHistChunk())
        {
            out.write(ByteUtil.int2ToByteArray(graphic.getHistChunk().getHistogram().size() * 2));
            out.write(AncillaryChunkType.hIST.getSignature());

            for (int value : graphic.getHistChunk().getHistogram().values())
            {
                out.write(ByteUtil.int2ToByteArray(value));
            }

            out.write(ByteUtil.unsignedIntToByteArray(graphic.getHistChunk().getChunk().getCrc()));
        }
    }

    private void write_tRNS() throws IOException
    {
        if (null != graphic.getTrnsChunk())
        {
            ColorType colorType = graphic.getIhdrChunk().getColorType();

            switch (colorType)
            {
                case Grayscale:
                    out.write(ByteUtil.unsignedIntToByteArray(2));
                    out.write(AncillaryChunkType.tRNS.getSignature());
                    out.write(ByteUtil.int2ToByteArray(graphic.getTrnsChunk().getGrayscaleSample()));
                    break;
                case Truecolor:
                    out.write(ByteUtil.unsignedIntToByteArray(6));
                    out.write(AncillaryChunkType.tRNS.getSignature());
                    out.write(ByteUtil.int2ToByteArray(graphic.getTrnsChunk().getRedSample()));
                    out.write(ByteUtil.int2ToByteArray(graphic.getTrnsChunk().getGreenSample()));
                    out.write(ByteUtil.int2ToByteArray(graphic.getTrnsChunk().getBlueSample()));
                    break;
                case IndexedColor:
                    out.write(ByteUtil.unsignedIntToByteArray(graphic.getTrnsChunk().getPaletteIndexSample().size()));
                    out.write(AncillaryChunkType.tRNS.getSignature());
                    for (int value : graphic.getTrnsChunk().getPaletteIndexSample().values())
                    {
                        out.writeByte(value);
                    }
                    break;
            }

            out.write(ByteUtil.unsignedIntToByteArray(graphic.getTrnsChunk().getChunk().getCrc()));
        }
    }

    private void write_PLTE() throws IOException
    {
        if (null != graphic.getPlteChunk())
        {
            out.write(ByteUtil.unsignedIntToByteArray(graphic.getPlteChunk().getPaletteEntries().size() * 3));
            out.write(CriticalChunkType.PLTE.getSignature());

            for (PaletteEntry entry : graphic.getPlteChunk().getPaletteEntries())
            {
                out.writeByte(entry.getRed());
                out.writeByte(entry.getGreen());
                out.writeByte(entry.getBlue());
            }

            out.write(ByteUtil.unsignedIntToByteArray(graphic.getPlteChunk().getChunk().getCrc()));
        }
    }

    private void write_cHRM() throws IOException
    {
        if (null != graphic.getChrmChunk())
        {
            out.write(ByteUtil.unsignedIntToByteArray(32));
            out.write(AncillaryChunkType.cHRM.getSignature());

            out.writeInt(graphic.getChrmChunk().getWhitePointX());
            out.writeInt(graphic.getChrmChunk().getWhitePointY());
            out.writeInt(graphic.getChrmChunk().getRedX());
            out.writeInt(graphic.getChrmChunk().getRedY());
            out.writeInt(graphic.getChrmChunk().getGreenX());
            out.writeInt(graphic.getChrmChunk().getGreenY());
            out.writeInt(graphic.getChrmChunk().getBlueX());
            out.writeInt(graphic.getChrmChunk().getBlueY());

            out.write(ByteUtil.unsignedIntToByteArray(graphic.getChrmChunk().getChunk().getCrc()));
        }
    }

    private void write_gAMA() throws IOException
    {
        if (null != graphic.getGamaChunk())
        {
            out.write(ByteUtil.unsignedIntToByteArray(4));
            out.write(AncillaryChunkType.gAMA.getSignature());
            out.write(graphic.getGamaChunk().getGama());

            out.write(ByteUtil.unsignedIntToByteArray(graphic.getGamaChunk().getChunk().getCrc()));
        }
    }

    private void write_sBIT() throws IOException
    {
        if (null != graphic.getSbitChunk())
        {
            ColorType colorType = graphic.getIhdrChunk().getColorType();
            switch (colorType)
            {
                case Grayscale:
                    out.write(ByteUtil.unsignedIntToByteArray(1));
                    out.write(AncillaryChunkType.sBIT.getSignature());
                    out.writeByte(graphic.getSbitChunk().getSignificantGrayscaleBits());
                    break;
                case Truecolor:
                case IndexedColor:
                    out.write(ByteUtil.unsignedIntToByteArray(3));
                    out.write(AncillaryChunkType.sBIT.getSignature());
                    out.writeByte(graphic.getSbitChunk().getSignificantTruecolorIndexedColorRedBits());
                    out.writeByte(graphic.getSbitChunk().getSignificantTruecolorIndexedColorGreenBits());
                    out.writeByte(graphic.getSbitChunk().getSignificantTruecolorIndexedColorBlueBits());
                    break;
                case GrayscaleAlpha:
                    out.write(ByteUtil.unsignedIntToByteArray(2));
                    out.write(AncillaryChunkType.sBIT.getSignature());
                    out.writeByte(graphic.getSbitChunk().getSignificantGrayscaleAlphaGrayscaleBits());
                    out.writeByte(graphic.getSbitChunk().getSignificantGrayscaleAlphaAlphaBits());
                    break;
                case TruecolorAlpha:
                    out.write(ByteUtil.unsignedIntToByteArray(4));
                    out.write(AncillaryChunkType.sBIT.getSignature());
                    out.writeByte(graphic.getSbitChunk().getSignificantTruecolorAlphaRedBits());
                    out.writeByte(graphic.getSbitChunk().getSignificantTruecolorAlphaGreenBits());
                    out.writeByte(graphic.getSbitChunk().getSignificantTruecolorAlphaBlueBits());
                    out.writeByte(graphic.getSbitChunk().getSignificantTruecolorAlphaAlphaBits());
                    break;
            }

            out.write(ByteUtil.unsignedIntToByteArray(graphic.getSbitChunk().getChunk().getCrc()));
        }
    }

    private void write_iCCP_or_sRGB() throws IOException
    {
        boolean hasIccpChunk = (null != graphic.getIccpChunk());
        boolean hasRgbChunk = (null != graphic.getSrgbChunk());

        if (hasIccpChunk || hasRgbChunk)
        {
            if (hasIccpChunk)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream output = new DataOutputStream(baos);
                output.write(graphic.getIccpChunk().getProfileName().getBytes());
                output.writeByte((byte) 0);
                output.writeByte(graphic.getIccpChunk().getCompressionMethod());
                output.write(graphic.getIccpChunk().getCompressedProfile());


                out.write(ByteUtil.unsignedIntToByteArray(baos.size()));
                out.write(AncillaryChunkType.iCCP.getSignature());
                out.write(baos.toByteArray());
                out.write(ByteUtil.unsignedIntToByteArray(graphic.getIccpChunk().getChunk().getCrc()));
            } else
            {
                out.write(ByteUtil.unsignedIntToByteArray(1));
                out.write(AncillaryChunkType.sRGB.getSignature());
                out.writeByte(graphic.getSrgbChunk().getRenderingIntent().getIntent());
                out.write(ByteUtil.unsignedIntToByteArray(graphic.getSrgbChunk().getChunk().getCrc()));
            }
        }
    }

    private void write_sPLT() throws IOException
    {
        if (graphic.getSpltChunks().size() > 0)
        {
            for (sPLTChunk chunk : graphic.getSpltChunks())
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream output = new DataOutputStream(baos);

                output.write(chunk.getPaletteName().getBytes());
                output.writeByte((byte) 0);
                output.writeByte(chunk.getSampleDepth());

                boolean is6BytesLong = (chunk.getSampleDepth() == 8);

                for (SuggestedPalette palette : chunk.getSuggestedPalette())
                {
                    if (is6BytesLong)
                    {
                        output.writeByte(palette.getRed());
                        output.writeByte(palette.getGreen());
                        output.writeByte(palette.getBlue());
                        output.writeByte(palette.getAlpha());
                        output.write(ByteUtil.int2ToByteArray(palette.getFrequency()));
                    } else
                    {
                        output.write(ByteUtil.int2ToByteArray(palette.getRed()));
                        output.write(ByteUtil.int2ToByteArray(palette.getGreen()));
                        output.write(ByteUtil.int2ToByteArray(palette.getBlue()));
                        output.write(ByteUtil.int2ToByteArray(palette.getAlpha()));
                        output.write(ByteUtil.int2ToByteArray(palette.getFrequency()));
                    }
                }

                out.write(ByteUtil.unsignedIntToByteArray(baos.size()));
                out.write(AncillaryChunkType.sPLT.getSignature());
                out.write(baos.toByteArray());
                out.write(ByteUtil.unsignedIntToByteArray(chunk.getChunk().getCrc()));
            }
        }
    }

    private void write_pHYs() throws IOException
    {
        if (null != graphic.getPhysChunk())
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream daos = new DataOutputStream(baos);
            daos.writeLong(graphic.getPhysChunk().getxAxisPixelsPerUnit());
            daos.writeLong(graphic.getPhysChunk().getyAxisPixelsPerUnit());
            byte[] bytes = baos.toByteArray();

            out.write(ByteUtil.unsignedIntToByteArray(9));
            out.write(AncillaryChunkType.pHYs.getSignature());
            out.write(bytes, 4, 4);
            out.write(bytes, 12, 4);
            out.writeByte(graphic.getPhysChunk().getUnitType().ordinal());
            out.write(ByteUtil.unsignedIntToByteArray(graphic.getPhysChunk().getChunk().getCrc()));
        }
    }

    private void write_iTXt() throws IOException
    {
        if (graphic.getItxtChunks().size() > 0)
        {
            for (iTXtChunk chunk : graphic.getItxtChunks())
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream output = new DataOutputStream(baos);


                //
                // Writing keywords - we can't just get Bytes because it won't be encoded properly!!
                //

                output.write(chunk.getKeyword().getBytes(PingGraphic.PNG_CHARSET));
                output.writeByte((byte) 0);
                output.writeByte(chunk.getCompressionFlag());
                output.writeByte(chunk.getCompressionMethod());
                output.write(chunk.getLanguageTag().getBytes("US-ASCII"));
                output.writeByte((byte) 0);
                output.write(chunk.getTranslationKeyword().getBytes("UTF-8"));
                output.writeByte((byte) 0);
                output.write(chunk.getTranslatedValue().getBytes("UTF-8"));

                out.write(ByteUtil.unsignedIntToByteArray(baos.size()));
                out.write(AncillaryChunkType.iTXt.getSignature());
                out.write(baos.toByteArray());
                out.write(ByteUtil.unsignedIntToByteArray(chunk.getChunk().getCrc()));
            }
        }
    }

    private void write_tEXt() throws IOException
    {
        if (graphic.getTextChunks().size() > 0)
        {
            for (tEXtChunk chunk : graphic.getTextChunks())
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream output = new DataOutputStream(baos);

                output.write(chunk.getKey().getBytes(PingGraphic.PNG_CHARSET));
                output.writeByte((byte) 0);
                output.write(chunk.getValue().getBytes(PingGraphic.PNG_CHARSET));

                out.write(ByteUtil.unsignedIntToByteArray(baos.size()));
                out.write(AncillaryChunkType.tEXt.getSignature());
                out.write(baos.toByteArray());
                out.write(ByteUtil.unsignedIntToByteArray(chunk.getChunk().getCrc()));
            }
        }
    }

    private void write_zTXt() throws IOException
    {
        if (graphic.getZtxtChunks().size() > 0)
        {
            for (zTXtChunk chunk : graphic.getZtxtChunks())
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream output = new DataOutputStream(baos);
                output.write(chunk.getKeyword().getBytes(PingGraphic.PNG_CHARSET));
                output.writeByte((byte) 0);
                output.writeByte(chunk.getCompressionMethod());
                output.write(chunk.getCompressedTextStream().toByteArray());

                out.write(ByteUtil.unsignedIntToByteArray(baos.size()));
                out.write(AncillaryChunkType.zTXt.getSignature());
                out.write(baos.toByteArray());
                out.write(ByteUtil.unsignedIntToByteArray(chunk.getChunk().getCrc()));
            }
        }
    }

    private void write_tIME() throws IOException
    {
        if (null != graphic.getTimeChunk())
        {
            out.write(ByteUtil.unsignedIntToByteArray(7));
            out.write(AncillaryChunkType.tIME.getSignature());

            out.write(ByteUtil.int2ToByteArray(graphic.getTimeChunk().getYear()));
            out.writeByte(graphic.getTimeChunk().getMonth());
            out.writeByte(graphic.getTimeChunk().getDay());
            out.writeByte(graphic.getTimeChunk().getHour());
            out.writeByte(graphic.getTimeChunk().getMinute());
            out.writeByte(graphic.getTimeChunk().getSecond());

            out.write(ByteUtil.unsignedIntToByteArray(graphic.getTimeChunk().getChunk().getCrc()));
        }
    }

    private void write_IHDR() throws IOException
    {
        out.write(ByteUtil.int4ToByteArray(13));
        out.write(CriticalChunkType.IHDR.getSignature());

        out.writeInt(graphic.getIhdrChunk().getWidth());
        out.writeInt(graphic.getIhdrChunk().getHeight());
        out.writeByte(graphic.getIhdrChunk().getBitDepth().getBitDepth());
        out.writeByte(graphic.getIhdrChunk().getColorType().getColorType());
        out.writeByte(graphic.getIhdrChunk().getCompressionMethod());
        out.writeByte(graphic.getIhdrChunk().getFilterMethod());
        out.writeByte(graphic.getIhdrChunk().getInterlaceMethod().ordinal());

        out.write(ByteUtil.unsignedIntToByteArray(graphic.getIhdrChunk().getChunk().getCrc()));
    }

    private void writeFileSignature() throws IOException
    {
        out.writeLong(PingGraphic.PNG_SIGNATURE);
    }

}
