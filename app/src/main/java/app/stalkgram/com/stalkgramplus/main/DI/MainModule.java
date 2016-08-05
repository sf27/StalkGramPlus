package app.stalkgram.com.stalkgramplus.main.DI;

import android.content.Context;

import javax.inject.Singleton;

import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.MainImageRepositoryImpl;
import app.stalkgram.com.stalkgramplus.main.MainInteractorImpl;
import app.stalkgram.com.stalkgramplus.main.MainPresenter;
import app.stalkgram.com.stalkgramplus.main.MainPresenterImpl;
import app.stalkgram.com.stalkgramplus.main.MainVideoRepositoryImpl;
import app.stalkgram.com.stalkgramplus.main.UI.MainView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by elio on 8/5/16.
 */
@Module
public class MainModule {

    MainView view;
    Context context;

    public MainModule(MainView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return this.context;
    }

    @Provides
    @Singleton
    MainView providesMainView() {
        return view;
    }

    @Provides
    @Singleton
    MainPresenter providesMainPresenter(Context context, EventBus eventBus, MainView mainView, MainInteractorImpl mainInteractor) {
        return new MainPresenterImpl(context, eventBus, mainView, mainInteractor);
    }

    @Provides
    @Singleton
    MainInteractorImpl providesMainInteractor(Context context, MainImageRepositoryImpl mainImageRepository, MainVideoRepositoryImpl mainVideoRepository, EventBus eventBus) {
        return new MainInteractorImpl(context, mainImageRepository, mainVideoRepository, eventBus);
    }

    @Provides
    @Singleton
    MainImageRepositoryImpl providesMainImageRepositoryImpl(EventBus eventBus) {
        return new MainImageRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    MainVideoRepositoryImpl providesMainVideoRepositoryImpl(EventBus eventBus) {
        return new MainVideoRepositoryImpl(eventBus);
    }

}
