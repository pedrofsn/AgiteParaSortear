package agite.para.sortear;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context CONTEXT;

    public static Context getContext() {
        return CONTEXT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
    }
}
