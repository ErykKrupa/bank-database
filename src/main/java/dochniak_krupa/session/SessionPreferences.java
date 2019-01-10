package dochniak_krupa.session;

import java.util.prefs.Preferences;

public class SessionPreferences {
    public static Preferences pref;

    static{
        pref = Preferences.userRoot();
    }
}
