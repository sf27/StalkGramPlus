package app.stalkgram.com.stalkgramplus.main;

import app.stalkgram.com.stalkgramplus.domain.DownloadFileFromURL;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public class MainVideoRepositoryImpl implements MainRepository {

    private DownloadFileFromURL downloadFileFromURL;

    public MainVideoRepositoryImpl(DownloadFileFromURL downloadFileFromURL) {
        this.downloadFileFromURL = downloadFileFromURL;
    }

    @Override
    public void downloadFile(String username, String videoUrl) {
        downloadFileFromURL.setEventType(MainEvent.onDownloadVideo);
        downloadFileFromURL.execute(videoUrl);
    }
}
