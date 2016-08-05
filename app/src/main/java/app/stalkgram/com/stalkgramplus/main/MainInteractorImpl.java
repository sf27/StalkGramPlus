package app.stalkgram.com.stalkgramplus.main;

import android.content.Context;

import java.util.HashMap;

import app.stalkgram.com.stalkgramplus.R;
import app.stalkgram.com.stalkgramplus.domain.ScrappingInstagram;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public class MainInteractorImpl implements MainInteractor {

    private MainRepository mainImageRepository;
    private MainRepository mainVideoRepository;
    private EventBus eventBus;
    private Context context;

    public MainInteractorImpl(Context context, MainImageRepositoryImpl mainImageRepository, MainVideoRepositoryImpl mainVideoRepository, EventBus eventBus) {
        this.mainImageRepository = mainImageRepository;
        this.mainVideoRepository = mainVideoRepository;
        this.eventBus = eventBus;
        this.context = context;
    }

    @Override
    public void downloadFile(HashMap<String, String> data) {
        if (data != null) {
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
            event.setError(context.getString(R.string.event_error_not_return_data));
            eventBus.post(event);
        }
    }
}
