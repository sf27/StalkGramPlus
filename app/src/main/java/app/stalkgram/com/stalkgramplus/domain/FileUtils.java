package app.stalkgram.com.stalkgramplus.domain;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import app.stalkgram.com.stalkgramplus.R;

/**
 * Created by elio on 8/5/16.
 */
public class FileUtils {

    public static void shareFile(String filePath, Context context) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        File file = new File(filePath);
        Uri imageUri = Uri.fromFile(file);

        shareIntent.setData(imageUri);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(
                Uri.fromFile(file).toString()
        );
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                extension
        );
        shareIntent.setType(mimetype);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        String msn = context.getResources().getString(R.string.action_settings);
        context.startActivity(Intent.createChooser(shareIntent, msn));
    }

    public static void setImageAs(String filePath, Context context) {
        Intent setAsIntent = new Intent(Intent.ACTION_ATTACH_DATA);

        File file = new File(filePath);
        Uri imageUri = Uri.fromFile(file);

        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(
                Uri.fromFile(file).toString()
        );
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                extension
        );

        setAsIntent.setDataAndType(imageUri, mimetype);
        setAsIntent.putExtra("mimeType", mimetype);
        setAsIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        setAsIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        String msn = context.getResources().getString(R.string.action_settings);
        context.startActivity(Intent.createChooser(setAsIntent, msn));
    }
}
