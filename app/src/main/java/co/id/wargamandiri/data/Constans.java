package co.id.wargamandiri.data;

public class Constans {

        public static final String WEB_URL = "http://aplikasimandiri.com/";
        public static final String SUB_WEB_URL = "http://admin.aplikasimandiri.com/";
//        public static final String WEB_URL = "http://192.168.1.4/shope/public/";
        public static final String WEB_URL_API = WEB_URL + "api/";
        public static final String WEB_URL_STORAGE = WEB_URL + "storage/";
        public static final String RESET_PASS = WEB_URL + "password/reset/";
        //    public static final String WEB_URL_API = "http://192.168.1.4/khsonline/public/api/";
        public static final String PRODUK = WEB_URL_API + "backend/produk";
        public static final String KATEGORI = WEB_URL_API + "backend/master/kategori";
        public static final String VOUCHER = WEB_URL_API + "backend/master/voucher";
        public static final String BANK = WEB_URL_API + "backend/master/bank";
        public static final String MEMBER = WEB_URL_API + "backend/member";
        public static final String NEWS = WEB_URL_API + "backend/master/news";
        public static final String MEMBER_STATUS = WEB_URL_API + "backend/member-update_status";
        public static final String SLIDER = WEB_URL_API + "backend/master/slider";
        public static final String LOGIN = WEB_URL_API + "login";
        public static final String TOKEN = WEB_URL_API + "tokenize";
        public static final String DAFTAR_TOKO = WEB_URL_API + "register/toko";
        public static final String PESAN_PEMBUKA = SUB_WEB_URL + "api/backend/pesan-pembuka";
        public static final String PESAN_PENUTUP = SUB_WEB_URL + "api/backend/pesan-penutup";

        //transaksi
        public static final String ORDER = WEB_URL_API + "backend/transaksi/order";
        public static final String ORDER_PARAM = "id_toko";
        public static final String ORDER_RELATION = "detail.produk,address,user,shipping,payment";

        public static final String KONFIRMASI = WEB_URL_API + "backend/transaksi/konfirmasi";
        public static final String KONFIRMASI_RELATION = "user,order";

        public static final String TOPUP = WEB_URL_API + "backend/transaksi/topup";
        public static final String TOPUP_RELATION = "user";

        //chat
        public static final String CHAT_ROOM = WEB_URL_API + "backend/chat/room";
        public static final String CHAT_USER = WEB_URL_API + "backend/chat/send-to-user";

        //save
        public final static String APLIKASI_MANDIRI = "/mandiri_tmp/";
        public final static String UNZIP = APLIKASI_MANDIRI + "unzipped/";
        public final static String APK_DIR = APLIKASI_MANDIRI + "apk/";
        public final static String SAVED_DIR = APLIKASI_MANDIRI + "saved/";
        public final static String DRAFT_DIR = APLIKASI_MANDIRI + "draft/";
        public final static String TEMPLATE_ID = "TEMPLATE_ID";
        public final static String TEMPLATE_OBJECT = "TEMPLATE_OBJECT";
        public final static String SIMULATOR_FILE_PATH = "SIMULATOR_FILE_PATH";
        public final static String PROJECT_FILE_PATH = "PROJECT_FILE_PATH";
        public final static String START_ACTIVITY = "START_ACTIVITY";
        public final static String START_FRAGMENT = "START_FRAGMENT";

}
