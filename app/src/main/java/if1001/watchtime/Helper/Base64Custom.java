package if1001.watchtime.Helper;

import android.util.Base64;

public class Base64Custom {

    public static String base64Encode(String text) {

        return Base64.encodeToString(text.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String base64Decode(String codedText) {

        return new String(Base64.decode(codedText, Base64.DEFAULT));
    }

}
