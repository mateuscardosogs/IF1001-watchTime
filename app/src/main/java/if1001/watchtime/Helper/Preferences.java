package if1001.watchtime.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private Context context;
    private SharedPreferences preferences;
    private String FILE_NAME = "watchTime.preferences";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String IDENTIFIER_KEY = "userLoggedIdentifier";
    private final String NAME_KEY = "userLoggedName";

    public Preferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(FILE_NAME, MODE);

        editor = preferences.edit();
    }

    public void saveUserPreferences(String userIdentifier, String userName) {
        editor.putString(IDENTIFIER_KEY, userIdentifier);
        editor.putString(NAME_KEY, userName);
        editor.commit();
    }

    public String getIdentifier() {
        return preferences.getString(IDENTIFIER_KEY, null);
    }

    public String getName() {
        return preferences.getString(NAME_KEY, null);
    }
}
