package co.id.wargamandiri.helper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class LEDataOutputStream {
    protected DataOutputStream dos;

    public LEDataOutputStream(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public final void writeShort(short s) throws IOException {
        this.dos.writeByte(s & 255);
        this.dos.writeByte((s >>> 8) & 255);
    }

    public int size() {
        return this.dos.size();
    }

    public final void writeChar(char c) throws IOException {
        this.dos.writeByte(c & 255);
        this.dos.writeByte((c >>> 8) & 255);
    }

    public final void writeCharArray(char[] c) throws IOException {
        for (char writeChar : c) {
            writeChar(writeChar);
        }
    }

    public final void writeByte(byte b) throws IOException {
        this.dos.writeByte(b);
    }

    public final void writeFully(byte[] b) throws IOException {
        this.dos.write(b, 0, b.length);
    }

    public final void writeFully(byte[] b, int a, int len) throws IOException {
        this.dos.write(b, a, len);
    }

    public final void writeInt(int i) throws IOException {
        this.dos.writeByte(i & 255);
        this.dos.writeByte((i >>> 8) & 255);
        this.dos.writeByte((i >>> 16) & 255);
        this.dos.writeByte((i >>> 24) & 255);
    }

    public final void writeIntArray(int[] buf, int s, int end) throws IOException {
        int i = s;
        while (s < end) {
            writeInt(buf[s]);
            s++;
        }
    }

    public final void writeIntArray(int[] buf) throws IOException {
        writeIntArray(buf, 0, buf.length);
    }

    public final void writeNulEndedString(String string, int length, boolean fixed) throws IOException {
        char[] ch = string.toCharArray();
        int j = 0;
        while (j < ch.length && length != 0) {
            int j2 = j + 1;
            writeChar(ch[j]);
            length--;
            j = j2;
        }
        if (fixed) {
            for (int i = 0; i < length * 2; i++) {
                this.dos.writeByte(0);
            }
        }
    }
}
