package app.stalkgram.com.stalkgramplus.main;

import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public interface MainPresenter {
    void onCreate();
    void onDestroy();

    void downloadPhoto(String url);
    void onEventMainThread(MainEvent event);

    void checkIfStorageIsAvailable();
}
