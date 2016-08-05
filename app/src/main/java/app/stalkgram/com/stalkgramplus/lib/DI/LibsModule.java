package app.stalkgram.com.stalkgramplus.lib.DI;

import javax.inject.Singleton;

import app.stalkgram.com.stalkgramplus.lib.GreenRobotEventBus;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ykro.
 */
@Module
public class LibsModule {
    @Provides
    @Singleton
    EventBus provideEventBus() {
        return new GreenRobotEventBus();
    }
}
