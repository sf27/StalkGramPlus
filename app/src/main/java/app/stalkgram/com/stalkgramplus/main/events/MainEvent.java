package app.stalkgram.com.stalkgramplus.main.events;

/**
 * Created by ykro.
 */
public class MainEvent {
    public final static int onDownloadError = 1;
    public final static int onDownloadVideoSuccess = 2;
    public final static int onDownloadImageSuccess = 3;
    public final static int onDownloadImage = 4;
    public final static int onDownloadVideo = 5;
    public final static int onProgress = 6;

    private int type;
    private int progress;
    private String error;
    private String filePath;

    public MainEvent() {
        this.type = 0;
        this.progress = 0;
        this.error = null;
        this.filePath = null;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String path) {
        this.filePath = path;
    }
}
