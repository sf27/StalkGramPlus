package app.stalkgram.com.stalkgramplus;

import android.app.Application;
import android.content.Context;
import app.stalkgram.com.stalkgramplus.lib.DI.LibsModule;
import app.stalkgram.com.stalkgramplus.main.DI.DaggerMainComponent;
import app.stalkgram.com.stalkgramplus.main.DI.MainComponent;
import app.stalkgram.com.stalkgramplus.main.DI.MainModule;
import app.stalkgram.com.stalkgramplus.main.UI.MainView;

public class StalkgramApp extends Application {

    public MainComponent getMainComponent(MainView view, Context context) {
        return DaggerMainComponent
                       .builder()
                       .libsModule(new LibsModule())
                       .mainModule(new MainModule(view, context))
                       .build();
    }
}
