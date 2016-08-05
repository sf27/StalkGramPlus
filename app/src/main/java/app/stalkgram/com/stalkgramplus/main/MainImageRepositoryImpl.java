package app.stalkgram.com.stalkgramplus.main;

import app.stalkgram.com.stalkgramplus.domain.DownloadFileFromURL;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public class MainImageRepositoryImpl implements MainRepository {

    private EventBus eventBus;
    public MainImageRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void downloadFile(String username, String imageUrl) {
        DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL(MainEvent.onDownloadImage, eventBus);
        downloadFileFromURL.execute(imageUrl);
    }
}
