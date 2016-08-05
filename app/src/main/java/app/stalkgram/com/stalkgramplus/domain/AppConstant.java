package app.stalkgram.com.stalkgramplus.domain;

import android.os.Environment;

import app.stalkgram.com.stalkgramplus.BuildConfig;

public class AppConstant {

    public static final String ROOT_PATH = BuildConfig.ROOT_PATH;

    public static final String IMAGES_PATH = "/" + "images";

    public static final String VIDEOS_PATH = "/" + "videos";

    public static final String DOWNLOAD_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ROOT_PATH;

    public static final String DOWNLOAD_DIRECTORY_IMAGES = DOWNLOAD_DIRECTORY + "/" + IMAGES_PATH;

    public static final String DOWNLOAD_DIRECTORY_VIDEOS = DOWNLOAD_DIRECTORY + "/" + VIDEOS_PATH;

}
