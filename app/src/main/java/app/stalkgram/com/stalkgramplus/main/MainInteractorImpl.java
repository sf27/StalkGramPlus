package app.stalkgram.com.stalkgramplus.main;

import android.content.Context;

import java.util.HashMap;

import app.stalkgram.com.stalkgramplus.domain.DownloadPageAndParseHtml;
import app.stalkgram.com.stalkgramplus.domain.ScrappingInstagram;
import app.stalkgram.com.stalkgramplus.lib.GreenRobotEventBus;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public class MainInteractorImpl implements MainInteractor, DownloadPageAndParseHtml.onCompletedCallback {

    private MainRepository mainImageRepository;
    private MainRepository mainVideoRepository;
    private EventBus eventBus;
    private DownloadPageAndParseHtml parseHtml;

    public MainInteractorImpl(Context context) {
        this.mainImageRepository = new MainImageRepositoryImpl();
        this.mainVideoRepository = new MainVideoRepositoryImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
        this.parseHtml = new DownloadPageAndParseHtml(context);
    }

    @Override
    public void downloadFile(String url) {
        parseHtml.setOnCompleteCallback(this);
        parseHtml.execute(url);
    }

    @Override
    public void onComplete(HashMap<String, String> data) {
        if (data != null){
            String username = data.get(ScrappingInstagram.USERNAME_KEY);
            String videoUrl = data.get(ScrappingInstagram.VIDEO_KEY);
            String imageUrl = data.get(ScrappingInstagram.IMAGE_KEY);

            if (videoUrl != null) {
                mainVideoRepository.downloadFile(username, videoUrl);
            } else {
                mainImageRepository.downloadFile(username, imageUrl);
            }
        } else {
            MainEvent event = new MainEvent();
            event.setType(MainEvent.onDownloadError);
            event.setError("Objeto no retornó data");
            eventBus.post(event);
        }
    }
}
