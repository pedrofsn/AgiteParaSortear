package agite.para.sortear.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.util.List;

import agite.para.sortear.App;

/**
 * Created by pedrofsn on 09/05/2017.
 */

public class Utils {

    public static boolean isNullOrEmpty(Object o) {
        if (o != null) {
            if (o instanceof String) {
                return ((String) o).isEmpty();
            } else if (o instanceof List && ((List) o).size() == 0) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static boolean isRunningOnDebugMode(Context context) {
        return (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    public static void log(String message) {
        if (message != null && isRunningOnDebugMode(App.getContext())) {
            Log.e(Constantes.APP_NAME, message);
        }
    }

}
