package co.id.wargamandiri.helper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AXmlDecoder {
    private static final int AXML_CHUNK_TYPE = 524291;
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    private final LEDataInputStream mIn;
    public StringBlock mTableStrings;

    private void readStrings() throws IOException {
        checkChunk(this.mIn.readInt(), AXML_CHUNK_TYPE);
        this.mIn.readInt();
        this.mTableStrings = StringBlock.read(this.mIn);
        byte[] buf = new byte[2048];
        while (true) {
            int num = this.mIn.read(buf, 0, 2048);
            if (num != -1) {
                this.byteOut.write(buf, 0, num);
            } else {
                return;
            }
        }
    }

    private AXmlDecoder(LEDataInputStream in) {
        this.mIn = in;
    }

    public static AXmlDecoder read(InputStream input) throws IOException {
        AXmlDecoder axml = new AXmlDecoder(new LEDataInputStream(input));
        axml.readStrings();
        return axml;
    }

    public void write(List<String> list, OutputStream out) throws IOException {
        write((List) list, new LEDataOutputStream(out));
    }

    public void write(List<String> list, LEDataOutputStream out) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LEDataOutputStream buf = new LEDataOutputStream(baos);
        this.mTableStrings.write(list, buf);
        buf.writeFully(this.byteOut.toByteArray());
        out.writeInt(AXML_CHUNK_TYPE);
        out.writeInt(baos.size() + 8);
        out.writeFully(baos.toByteArray());
    }

    public static void main(String[] args) throws IOException {
        AXmlDecoder axml = read(new FileInputStream("term.xml"));
        List list = new ArrayList();
        axml.mTableStrings.getStrings(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " " + ((String) list.get(i)));
        }
        axml.write(list, new FileOutputStream("test.xml"));
    }

    private void checkChunk(int type, int expectedType) throws IOException {
        if (type != expectedType) {
            throw new IOException(String.format("Invalid chunk type: expected=0x%08x, got=0x%08x", new Object[]{Integer.valueOf(expectedType), Short.valueOf((short) type)}));
        }
    }
}
