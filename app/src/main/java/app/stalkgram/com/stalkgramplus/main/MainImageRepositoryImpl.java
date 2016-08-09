package app.stalkgram.com.stalkgramplus.main;

import android.os.AsyncTask;

import app.stalkgram.com.stalkgramplus.domain.DownloadFileFromURL;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public class MainImageRepositoryImpl implements MainRepository {

    private DownloadFileFromURL downloadFileFromURL;
    private EventBus eventBus;
    private MainEvent event;

    public MainImageRepositoryImpl(DownloadFileFromURL downloadFileFromURL, EventBus eventBus, MainEvent event) {
        this.downloadFileFromURL = downloadFileFromURL;
        this.eventBus = eventBus;
        this.event = event;
    }

    @Override
    public void downloadFile(String username, String imageUrl) {
        if (downloadFileFromURL.getStatus() == AsyncTask.Status.FINISHED) {
            downloadFileFromURL = new DownloadFileFromURL(eventBus, event);
        }
        downloadFileFromURL.setEventType(MainEvent.onDownloadImage);
        downloadFileFromURL.execute(imageUrl);
    }
}
