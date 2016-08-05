package app.stalkgram.com.stalkgramplus.domain;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import app.stalkgram.com.stalkgramplus.lib.GreenRobotEventBus;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

import static app.stalkgram.com.stalkgramplus.main.events.MainEvent.onDownloadImage;
import static app.stalkgram.com.stalkgramplus.main.events.MainEvent.onDownloadVideo;

public class DownloadFileFromURL extends AsyncTask<String, Integer, String> {

//    private ProgressBar progress = null;

    //    public DownloadFileFromURL(ProgressBar progress, String type) {
//        this.progress = progress;
//    }
//    private String absPath;
    private int event;
    private EventBus eventBus;
//    public DownloadFileFromURL(String absPath) {
//        this.absPath = absPath;
//    }

    public DownloadFileFromURL(int event) {
        this.event = event;
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    /**
     * Before starting background thread Show Progress Bar Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        this.progress.setVisibility(View.VISIBLE);
//        this.progress.setIndeterminate(false);
//        this.progress.setProgress(0);
//        this.progress.setMax(100);
//        this.progress.invalidate();
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        String absPath = null;
        String name, path = null;
        try {
            String url_str = f_url[0];
            System.out.println("URL: " + url_str);

            URL url = new URL(url_str);
            URLConnection connection = url.openConnection();
            connection.connect();
            // getting file length
            int lengthOfFile = connection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            switch (event) {
                case onDownloadImage:
                    name = "image_" + System.currentTimeMillis() + ".jpg";
                    path = AppConstant.DOWNLOAD_DIRECTORY_IMAGES + "/";
                    absPath = path + "/" + name;
                    break;
                case onDownloadVideo:
                    name = "video_" + System.currentTimeMillis() + ".mp4";
                    path = AppConstant.DOWNLOAD_DIRECTORY_VIDEOS + "/";
                    absPath = path + "/" + name;
                    break;
            }

            File f = new File(path + "/");
            if (!f.exists()) {
                boolean mkdirs = f.mkdirs();
            }

            OutputStream output = new FileOutputStream(absPath);
            byte data[] = new byte[1024];
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress((int) ((total * 100) / lengthOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();
            // closing streams
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            postError(e.getLocalizedMessage());
            return null;
        }
        return absPath;
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(Integer... prog) {
//        this.progress.setProgress(prog[0]);
        postProgress(prog[0]);
    }

    /**
     * After completing background task Dismiss the progress dialog
     **/
    @Override
    protected void onPostExecute(String filePath) {
        if (filePath != null) {
            switch (event) {
                case onDownloadImage:
                    postImageSuccess(filePath);
                    break;

                case onDownloadVideo:
                    postVideoSuccess(filePath);
                    break;
            }
        }
//        this.progress.setProgress(0);
//        this.progress.setVisibility(View.INVISIBLE);

    }

    private void postImageSuccess(String filePath) {
        post(filePath, MainEvent.onDownloadImageSuccess, null, 0);
    }

    private void postVideoSuccess(String filePath) {
        post(filePath, MainEvent.onDownloadVideoSuccess, null, 0);
    }

    private void postError(String error) {
        post(null, MainEvent.onDownloadError, error, 0);
    }

    private void postProgress(int progress) {
        post(null, MainEvent.onProgress, null, progress);
    }

    private void post(String filePath, int type, String error, int progress) {
        MainEvent event = new MainEvent();
        event.setType(type);
        event.setError(error);
        event.setFilePath(filePath);
        event.setProgress(progress);
        eventBus.post(event);
    }

}