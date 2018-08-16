package co.id.wargamandiri.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileUtil {
    private FileUtil() {
    }

    public static byte[] readFile(String fileName) throws IOException {
        return readFile(new File(fileName));
    }

    public static byte[] readFile(File file) throws IOException {
        return readFile(file, 0, -1);
    }

    public static byte[] readFile(File file, int offset, int length) throws IOException {
        if (!file.exists()) {
            throw new RuntimeException(file + ": file not found");
        } else if (!file.isFile()) {
            throw new RuntimeException(file + ": not a file");
        } else if (file.canRead()) {
            long longLength = file.length();
            int fileLength = (int) longLength;
            if (((long) fileLength) != longLength) {
                throw new RuntimeException(file + ": file too long");
            }
            if (length == -1) {
                length = fileLength - offset;
            }
            if (offset + length > fileLength) {
                throw new RuntimeException(file + ": file too short");
            }
            FileInputStream in = new FileInputStream(file);
            int at = offset;
            while (at > 0) {
                long amt = in.skip((long) at);
                if (amt == -1) {
                    throw new RuntimeException(file + ": unexpected EOF");
                }
                at = (int) (((long) at) - amt);
            }
            byte[] result = readStream(in, length);
            in.close();
            return result;
        } else {
            throw new RuntimeException(file + ": file not readable");
        }
    }

    public static byte[] readStream(InputStream in, int length) throws IOException {
        byte[] result = new byte[length];
        int at = 0;
        while (length > 0) {
            int amt = in.read(result, at, length);
            if (amt == -1) {
                throw new RuntimeException("unexpected EOF");
            }
            at += amt;
            length -= amt;
        }
        return result;
    }
}
