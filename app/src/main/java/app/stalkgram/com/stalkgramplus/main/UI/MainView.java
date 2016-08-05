package app.stalkgram.com.stalkgramplus.main.UI;

public interface MainView {
    void enableInputs();
    void disableInputs();

    void showProgress();
    void onProgress(int progress);
    void hideProgress();

    void onDownload();

    void downloadImageSuccess(String imagePath);
    void downloadVideoSuccess(String videoPath);
    void downloadError(String error);
}