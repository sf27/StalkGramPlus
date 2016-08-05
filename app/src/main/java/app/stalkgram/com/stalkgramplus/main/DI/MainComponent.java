package app.stalkgram.com.stalkgramplus.main.DI;

import javax.inject.Singleton;

import app.stalkgram.com.stalkgramplus.lib.DI.LibsModule;
import app.stalkgram.com.stalkgramplus.main.UI.MainActivity;
import dagger.Component;

/**
 * Created by elio on 8/5/16.
 */
@Singleton
@Component(modules = {MainModule.class, LibsModule.class})
public interface MainComponent {

    void inject(MainActivity activity);
}