package app.stalkgram.com.stalkgramplus.main;

import app.stalkgram.com.stalkgramplus.domain.DownloadFileFromURL;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public class MainImageRepositoryImpl implements MainRepository {

    private DownloadFileFromURL downloadFileFromURL;

    public MainImageRepositoryImpl(DownloadFileFromURL downloadFileFromURL) {
        this.downloadFileFromURL = downloadFileFromURL;
    }

    @Override
    public void downloadFile(String username, String imageUrl) {
        downloadFileFromURL.setEventType(MainEvent.onDownloadImage);
        downloadFileFromURL.execute(imageUrl);
    }
}
