package app.stalkgram.com.stalkgramplus.main;

import android.content.Context;
import android.os.AsyncTask;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import app.stalkgram.com.stalkgramplus.domain.DownloadPageAndParseHtml;
import app.stalkgram.com.stalkgramplus.domain.FileUtils;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.UI.MainView;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

/**
 * Created by elio on 8/5/16.
 */
public class MainPresenterImpl implements MainPresenter, DownloadPageAndParseHtml.onCompletedCallback {

    private EventBus eventBus;
    private MainView mainView;
    private MainInteractor mainInteractor;
    private Context context;
    private String filePath;
    private DownloadPageAndParseHtml parseHtml;

    public MainPresenterImpl(Context context, EventBus eventBus, MainView mainView, MainInteractor mainInteractor, DownloadPageAndParseHtml parseHtml) {
        this.context = context;
        this.eventBus = eventBus;
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
        this.parseHtml = parseHtml;
    }

    @Override
    public void onCreate() {
        filePath = null;
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        filePath = null;
        mainView = null;
        eventBus.unregister(this);
    }

    @Override
    public void downloadFile(String url) {
        if (mainView != null) {
            mainView.disableInputs();
            mainView.showProgress();
        }
        if(parseHtml.getStatus() == AsyncTask.Status.FINISHED){
            parseHtml = new DownloadPageAndParseHtml(context);
        }
        parseHtml.setOnCompleteCallback(this);
        parseHtml.execute(url);
    }

    @Override
    public void shareFile() {
        String path = getFilePath();
        if (path != null) {
            FileUtils.shareFile(path, context);
        }
    }

    @Override
    public void setAsFile() {
        String path = getFilePath();
        if (path != null) {
            FileUtils.setImageAs(path, context);
        }
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
    public void checkIfStorageIsAvailable() {}

    @Override
    public MainView getView() {
        return this.mainView;
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
            setFilePath(imagePath);
        }
    }

    private void onDownloadVideoSuccess(String videoPath) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.enableInputs();
            mainView.downloadVideoSuccess(videoPath);
            setFilePath(videoPath);
        }
    }

    private void onProgress(int progress) {
        if (mainView != null) {
            mainView.onProgress(progress);
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void onComplete(HashMap<String, String> data) {
        mainInteractor.downloadFile(data);
    }
}
