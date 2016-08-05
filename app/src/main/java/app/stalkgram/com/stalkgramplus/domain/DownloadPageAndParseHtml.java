package app.stalkgram.com.stalkgramplus.domain;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import app.stalkgram.com.stalkgramplus.R;

public class DownloadPageAndParseHtml extends AsyncTask<String, Integer, HashMap<String, String>> {

    public interface onCompletedCallback {
        void onComplete(HashMap<String, String> data);
    }

    private Context context;
    private ProgressDialog pDialog;
    private onCompletedCallback onCompleteCallback;

    public DownloadPageAndParseHtml(Context newContext) {
        super();
        this.context = newContext;
    }

    public void setOnCompleteCallback(onCompletedCallback onCompleteCallback){
        this.onCompleteCallback = onCompleteCallback;
    }

    /**
     * Before starting background thread Show Progress Bar Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String msn = this.context.getResources().getString(R.string.download_and_parse_progress);
        pDialog = new ProgressDialog(this.context);
        pDialog.setMessage(msn);
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected HashMap<String, String> doInBackground(String... urls) {

        String url = urls[0];

        if (url.isEmpty()) {
            System.out.println("Return null FileObject, the url is empty");
            return null;
        }

        ScrappingInstagram scrappingInstagram = new ScrappingInstagram();
        HashMap<String, String> data = scrappingInstagram.getData(url);
        return data;
    }

    /**
     * After completing background task Dismiss the progress dialog
     **/
    @Override
    protected void onPostExecute(HashMap<String, String> data) {
        pDialog.dismiss();
        onCompleteCallback.onComplete(data);
    }
}
