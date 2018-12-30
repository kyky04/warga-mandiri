package co.id.wargamandiri.helper;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;


public class StringBlock {
    public static final int CHUNK_STRINGBLOCK = 1835009;
    public static final int IS_UTF8 = 256;
    private static final CharsetDecoder UTF16LE_DECODER = Charset.forName("UTF-16LE").newDecoder();
    private static final CharsetEncoder UTF16LE_ENCODER = Charset.forName("UTF-16LE").newEncoder();
    private static final CharsetDecoder UTF8_DECODER = Charset.forName("UTF-8").newDecoder();
    private static final CharsetEncoder UTF8_ENCODER = Charset.forName("UTF-8").newEncoder();
    private int chunkSize;
    private int flags;
    private boolean m_isUTF8;
    private int[] m_stringOffsets;
    byte[] m_strings;
    private int[] m_styleOffsets;
    private int[] m_styles;
    private int stringsOffset;
    private int styleOffsetCount;
    private int stylesOffset;

    public static StringBlock read(LEDataInputStream reader) throws IOException {
        int i;
        reader.skipCheckInt(CHUNK_STRINGBLOCK);
        StringBlock block = new StringBlock();
        int chunkSize = reader.readInt();
        block.chunkSize = chunkSize;
        System.out.println("chunkSize " + chunkSize);
        int stringCount = reader.readInt();
        System.out.println("stringCount " + stringCount);
        int styleOffsetCount = reader.readInt();
        block.styleOffsetCount = styleOffsetCount;
        System.out.println("styleOffsetCount " + styleOffsetCount);
        int flags = reader.readInt();
        block.flags = flags;
        int stringsOffset = reader.readInt();
        block.stringsOffset = stringsOffset;
        System.out.println("stringsOffset " + stringsOffset);
        int stylesOffset = reader.readInt();
        block.stylesOffset = stylesOffset;
        System.out.println("stylesOffset " + stylesOffset);
        block.m_isUTF8 = (flags & IS_UTF8) != 0;
        block.m_stringOffsets = reader.readIntArray(stringCount);
        if (styleOffsetCount != 0) {
            block.m_styleOffsets = reader.readIntArray(styleOffsetCount);
        }
        if (stylesOffset == 0) {
            i = chunkSize;
        } else {
            i = stylesOffset;
        }
        int size = i - stringsOffset;
        if (size % 4 != 0) {
            throw new IOException("String data size is not multiple of 4 (" + size + ").");
        }
        block.m_strings = new byte[size];
        reader.readFully(block.m_strings);
        if (stylesOffset != 0) {
            size = chunkSize - stylesOffset;
            if (size % 4 != 0) {
                throw new IOException("Style data size is not multiple of 4 (" + size + ").");
            }
            block.m_styles = reader.readIntArray(size / 4);
            System.out.println("m_styles_size " + size);
        }
        System.out.println();
        return block;
    }

    public void getStrings(List<String> list) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
//            list.add(Utf8Utils.escapeString(getString(i)));
        }
    }

    public void write(LEDataOutputStream out) throws IOException {
        List<String> list = new ArrayList(getSize());
        getStrings(list);
        write(list, out);
    }

    public void write(List<String> list, LEDataOutputStream out) throws IOException {
        int i;
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        LEDataOutputStream led = new LEDataOutputStream(outBuf);
        int size = list.size();
        int[] offset = new int[size];
        int len = 0;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        LEDataOutputStream mStrings = new LEDataOutputStream(bOut);
        for (i = 0; i < size; i++) {
            offset[i] = len;
//            char[] charbuf = Utf8Utils.escapeSequence((String) list.get(i)).toCharArray();
//            mStrings.writeShort((short) charbuf.length);
//            mStrings.writeCharArray(charbuf);
//            mStrings.writeShort((short) 0);
//            len += (charbuf.length * 2) + 4;
        }
        int m_strings_size = bOut.size();
        int size_mod = m_strings_size % 4;
        if (size_mod != 0) {
            for (i = 0; i < 4 - size_mod; i++) {
                bOut.write(0);
            }
            m_strings_size += 4 - size_mod;
        }
        byte[] m_strings = bOut.toByteArray();
        System.out.println("string chunk size: " + this.chunkSize);
        led.writeInt(size);
        led.writeInt(this.styleOffsetCount);
        led.writeInt(this.flags);
        led.writeInt(this.stringsOffset);
        led.writeInt(this.stylesOffset);
        led.writeIntArray(offset);
        if (this.styleOffsetCount != 0) {
            System.out.println("write stylesOffset");
            led.writeIntArray(this.m_styleOffsets);
        }
        led.writeFully(m_strings);
        if (this.m_styles != null) {
            System.out.println("write m_styles");
            led.writeIntArray(this.m_styles);
        }
        out.writeInt(CHUNK_STRINGBLOCK);
        byte[] b = outBuf.toByteArray();
        out.writeInt(b.length + 8);
        out.writeFully(b);
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public String getString(int index) {
        if (index < 0 || this.m_stringOffsets == null || index >= this.m_stringOffsets.length) {
            return null;
        }
        int length;
        int offset = this.m_stringOffsets[index];
        if (this.m_isUTF8) {
            offset += getVarint(this.m_strings, offset)[1];
            int[] varint = getVarint(this.m_strings, offset);
            offset += varint[1];
            length = varint[0];
        } else {
            length = getShort(this.m_strings, offset) * 2;
            offset += 2;
        }
        return decodeString(offset, length);
    }

    public int getSize() {
        return this.m_stringOffsets != null ? this.m_stringOffsets.length : 0;
    }

    public String getHTML(int index) {
        String raw = getString(index);
        if (raw == null) {
            return raw;
        }
        int[] style = getStyle(index);
        if (style == null) {
            return raw;
        }
        StringBuilder html = new StringBuilder(raw.length() + 32);
        int[] opened = new int[(style.length / 3)];
        int offset = 0;
        int depth = 0;
        while (true) {
            int i = -1;
            int j = 0;
            while (j != style.length) {
                if (style[j + 1] != -1 && (i == -1 || style[i + 1] > style[j + 1])) {
                    i = j;
                }
                j += 3;
            }
            int start = i != -1 ? style[i + 1] : raw.length();
            j = depth - 1;
            while (j >= 0) {
                int last = opened[j];
                int end = style[last + 2];
                if (end >= start) {
                    break;
                }
                if (offset <= end) {
                    html.append(raw.substring(offset, end + 1));
                    offset = end + 1;
                }
                outputStyleTag(getString(style[last]), html, true);
                j--;
            }
            depth = j + 1;
            if (offset < start) {
                html.append(raw.substring(offset, start));
                offset = start;
            }
            if (i == -1) {
                return html.toString();
            }
            outputStyleTag(getString(style[i]), html, false);
            style[i + 1] = -1;
            int depth2 = depth + 1;
            opened[depth] = i;
            depth = depth2;
        }
    }

    private void outputStyleTag(String tag, StringBuilder builder, boolean close) {
        builder.append('<');
        if (close) {
            builder.append('/');
        }
        int pos = tag.indexOf(59);
        if (pos == -1) {
            builder.append(tag);
        } else {
            builder.append(tag.substring(0, pos));
            if (!close) {
                boolean loop = true;
                while (loop) {
                    String val;
                    int pos2 = tag.indexOf(61, pos + 1);
                    builder.append(' ').append(tag.substring(pos + 1, pos2)).append("=\"");
                    pos = tag.indexOf(59, pos2 + 1);
                    if (pos != -1) {
                        val = tag.substring(pos2 + 1, pos);
                    } else {
                        loop = false;
                        val = tag.substring(pos2 + 1);
                    }
                    builder.append(val).append('\"');
                }
            }
        }
        builder.append('>');
    }

    private int[] getStyle(int index) {
        int[] iArr = null;
        if (!(this.m_styleOffsets == null || this.m_styles == null || index >= this.m_styleOffsets.length)) {
            int offset = this.m_styleOffsets[index] / 4;
            int count = 0;
            int i = offset;
            while (i < this.m_styles.length && this.m_styles[i] != -1) {
                count++;
                i++;
            }
            if (count != 0 && count % 3 == 0) {
                iArr = new int[count];
                i = offset;
                int j = 0;
                while (i < this.m_styles.length && this.m_styles[i] != -1) {
                    int j2 = j + 1;
                    int i2 = i + 1;
                    iArr[j] = this.m_styles[i];
                    j = j2;
                    i = i2;
                }
            }
        }
        return iArr;
    }

    private String decodeString(int offset, int length) {
        try {
            return (this.m_isUTF8 ? UTF8_DECODER : UTF16LE_DECODER).decode(ByteBuffer.wrap(this.m_strings, offset, length)).toString();
        } catch (CharacterCodingException e) {
            return null;
        }
    }

    private static final int getShort(byte[] array, int offset) {
        return ((array[offset + 1] & 255) << 8) | (array[offset] & 255);
    }

    private static final int[] getVarint(byte[] array, int offset) {
        boolean more = false;
        int val = array[offset];
//        if ((val & Opcode.ODEXED_INSTANCE_VOLATILE) != 0) {
//            more = true;
//        } else {
//            more = false;
//        }
        val &= 127;
        if (more) {
            return new int[]{(val << 8) | (array[offset + 1] & 255), 2};
        }
        return new int[]{val, 1};
    }
}
