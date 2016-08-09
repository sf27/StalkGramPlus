package app.stalkgram.com.stalkgramplus.main.DI;

import android.content.Context;

import javax.inject.Singleton;

import app.stalkgram.com.stalkgramplus.domain.DownloadFileFromURL;
import app.stalkgram.com.stalkgramplus.domain.DownloadPageAndParseHtml;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.MainImageRepositoryImpl;
import app.stalkgram.com.stalkgramplus.main.MainInteractor;
import app.stalkgram.com.stalkgramplus.main.MainInteractorImpl;
import app.stalkgram.com.stalkgramplus.main.MainPresenter;
import app.stalkgram.com.stalkgramplus.main.MainPresenterImpl;
import app.stalkgram.com.stalkgramplus.main.MainVideoRepositoryImpl;
import app.stalkgram.com.stalkgramplus.main.UI.MainView;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;
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
    MainEvent providesMainEvent() {
        return new MainEvent();
    }

    @Provides
    @Singleton
    DownloadPageAndParseHtml providesDownloadPageAndParseHtml() {
        return new DownloadPageAndParseHtml(context);
    }

    @Provides
    @Singleton
    DownloadFileFromURL providesDownloadFileFromURL(EventBus eventBus, MainEvent event) {
        return new DownloadFileFromURL(eventBus, event);
    }

    @Provides
    @Singleton
    MainPresenter providesMainPresenter(Context context, EventBus eventBus, MainView mainView, MainInteractor mainInteractor, DownloadPageAndParseHtml parseHtml) {
        return new MainPresenterImpl(context, eventBus, mainView, mainInteractor, parseHtml);
    }

    @Provides
    @Singleton
    MainInteractor providesMainInteractor(Context context, MainImageRepositoryImpl mainImageRepository, MainVideoRepositoryImpl mainVideoRepository, EventBus eventBus, MainEvent event) {
        return new MainInteractorImpl(context, mainImageRepository, mainVideoRepository, eventBus, event);
    }

    @Provides
    @Singleton
    MainImageRepositoryImpl providesMainImageRepositoryImpl(DownloadFileFromURL downloadFileFromURL, EventBus eventBus, MainEvent event) {
        return new MainImageRepositoryImpl(downloadFileFromURL, eventBus, event);
    }

    @Provides
    @Singleton
    MainVideoRepositoryImpl providesMainVideoRepositoryImpl(DownloadFileFromURL downloadFileFromURL, EventBus eventBus, MainEvent event) {
        return new MainVideoRepositoryImpl(downloadFileFromURL, eventBus, event);
    }

}
