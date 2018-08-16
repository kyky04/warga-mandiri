package co.id.wargamandiri.helper;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class LEDataInputStream implements DataInput {
    protected final DataInputStream dis;
    int end;
    protected final InputStream is;
    int s;
    protected final byte[] work = new byte[8];

    public LEDataInputStream(InputStream in) {
        this.is = in;
        this.dis = new DataInputStream(in);
    }

    public int size() {
        return this.end;
    }

    public final boolean readBoolean() throws IOException {
        return this.dis.readBoolean();
    }

    public final byte readByte() throws IOException {
        return this.dis.readByte();
    }

    public final char readChar() throws IOException {
        this.dis.readFully(this.work, 0, 2);
        return (char) (((this.work[1] & 255) << 8) | (this.work[0] & 255));
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public int[] readIntArray(int length) throws IOException {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = readInt();
        }
        return array;
    }

    public void skipInt() throws IOException {
        skipBytes(4);
    }

    public void skipCheckInt(int expected) throws IOException {
        if (readInt() != expected) {
            throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", new Object[]{Integer.valueOf(expected), Integer.valueOf(readInt())}));
        }
    }

    public void skipCheckShort(short expected) throws IOException {
        if (readShort() != expected) {
            throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", new Object[]{Short.valueOf(expected), Short.valueOf(readShort())}));
        }
    }

    public void skipCheckByte(byte expected) throws IOException {
        if (readByte() != expected) {
            throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", new Object[]{Byte.valueOf(expected), Byte.valueOf(readByte())}));
        }
    }

    public String readNulEndedString(int length, boolean fixed) throws IOException {
        StringBuilder string = new StringBuilder(16);
        int length2 = length;
        while (true) {
            length = length2 - 1;
            if (length2 == 0) {
                break;
            }
            short ch = readShort();
            this.end += 2;
            if (ch == (short) 0) {
                break;
            }
            string.append((char) ch);
            length2 = length;
        }
        if (fixed) {
            skipBytes(length * 2);
            this.end += length * 2;
        }
        return string.toString();
    }

    public int read(byte[] b, int a, int len) throws IOException {
        return this.dis.read(b, a, len);
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public final void readFully(byte[] ba) throws IOException {
        this.dis.readFully(ba, 0, ba.length);
    }

    public final void readFully(byte[] ba, int off, int len) throws IOException {
        this.dis.readFully(ba, off, len);
    }

    public final int readInt() throws IOException {
        this.dis.readFully(this.work, 0, 4);
        return (((this.work[3] << 24) | ((this.work[2] & 255) << 16)) | ((this.work[1] & 255) << 8)) | (this.work[0] & 255);
    }

    public final String readLine() throws IOException {
        return this.dis.readLine();
    }

    public final long readLong() throws IOException {
        this.dis.readFully(this.work, 0, 8);
        return (long) ((((((((this.work[7] << 56) | ((this.work[6] & 255) << 48)) | ((this.work[5] & 255) << 40)) | ((this.work[4] & 255) << 32)) | ((this.work[3] & 255) << 24)) | ((this.work[2] & 255) << 16)) | ((this.work[1] & 255) << 8)) | (this.work[0] & 255));
    }

    public final short readShort() throws IOException {
        this.dis.readFully(this.work, 0, 2);
        return (short) (((this.work[1] & 255) << 8) | (this.work[0] & 255));
    }

    public final String readUTF() throws IOException {
        return this.dis.readUTF();
    }

    public final int readUnsignedByte() throws IOException {
        return this.dis.readUnsignedByte();
    }

    public final int readUnsignedShort() throws IOException {
        this.dis.readFully(this.work, 0, 2);
        return ((this.work[1] & 255) << 8) | (this.work[0] & 255);
    }

    public final int skipBytes(int n) throws IOException {
        return this.dis.skipBytes(n);
    }
}
