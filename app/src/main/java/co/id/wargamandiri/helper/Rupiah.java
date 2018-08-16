package co.id.wargamandiri.helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Rupiah {
    public String toRupiah(String price) {
        DecimalFormat df = new DecimalFormat("#,##0");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("sv", "SE"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        return "Rp " + df.format(Double.parseDouble(price));
    }

    public String toRibuan(String price) {
        DecimalFormat df = new DecimalFormat("#,##0");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("sv", "SE"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        return df.format(Double.parseDouble(price));
    }
}
