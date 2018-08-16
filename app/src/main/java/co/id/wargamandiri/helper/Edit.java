package co.id.wargamandiri.helper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface Edit {
    void read(List<String> list, byte[] bArr) throws IOException;

    void write(String str, OutputStream outputStream) throws IOException;
}
