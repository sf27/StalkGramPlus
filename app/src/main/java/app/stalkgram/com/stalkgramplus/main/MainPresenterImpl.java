package app.stalkgram.com.stalkgramplus.main;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;

import app.stalkgram.com.stalkgramplus.lib.GreenRobotEventBus;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.UI.MainView;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public class MainPresenterImpl implements MainPresenter {

    private EventBus eventBus;
    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.mainInteractor = new MainInteractorImpl(context);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        mainView = null;
        eventBus.unregister(this);
    }

    @Override
    public void downloadPhoto(String url) {
        if (mainView != null) {
            mainView.disableInputs();
            mainView.showProgress();
        }
        mainInteractor.downloadFile(url);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MainEvent event) {
        switch (event.getType()) {
            case MainEvent.onProgress:
                onProgress(event.getProgress());
                break;
            case MainEvent.onDownloadError:
                onDownloadError(event.getError());
                break;
            case MainEvent.onDownloadImageSuccess:
                onDownloadImageSuccess(event.getFilePath());
                break;
            case MainEvent.onDownloadVideoSuccess:
                onDownloadVideoSuccess(event.getFilePath());
                break;
        }
    }

    @Override
    public void checkIfStorageIsAvailable() {

    }

    private void onDownloadError(String error) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.enableInputs();
            mainView.downloadError(error);
        }
    }

    private void onDownloadImageSuccess(String imagePath) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.enableInputs();
            mainView.downloadImageSuccess(imagePath);
        }
    }

    private void onDownloadVideoSuccess(String videoPath) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.enableInputs();
            mainView.downloadVideoSuccess(videoPath);
        }
    }

    private void onProgress(int progress) {
        if (mainView != null) {
            mainView.onProgress(progress);
        }
    }

}
