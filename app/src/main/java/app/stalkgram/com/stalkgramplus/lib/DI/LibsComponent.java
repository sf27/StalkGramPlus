package app.stalkgram.com.stalkgramplus.lib.DI;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ykro.
 */

@Singleton
@Component(modules = {LibsModule.class})
public interface LibsComponent {
}

