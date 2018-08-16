package co.id.wargamandiri.helper;

import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
    private StringUtils() {
    }

    public static String join(Collection<String> collection, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> iter = collection.iterator();
        while (iter.hasNext()) {
            buffer.append((String) iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
}
