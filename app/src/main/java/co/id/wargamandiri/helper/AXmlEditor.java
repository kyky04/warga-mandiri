package co.id.wargamandiri.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AXmlEditor implements Edit {
    private AXmlDecoder axml;

    public void read(List<String> data, byte[] input) throws IOException {
        this.axml = AXmlDecoder.read(new ByteArrayInputStream(input));
        this.axml.mTableStrings.getStrings(data);
    }

    public void write(String data, OutputStream out) throws IOException {
        String[] strings = data.split("\n");
        List list = new ArrayList(strings.length);
        for (String str : strings) {
            list.add(str);
        }
        this.axml.write(list, out);
    }
}
