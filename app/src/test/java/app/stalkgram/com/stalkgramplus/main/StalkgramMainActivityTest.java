package app.stalkgram.com.stalkgramplus.main;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.VideoView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import app.stalkgram.com.stalkgramplus.BaseTest;
import app.stalkgram.com.stalkgramplus.BuildConfig;
import app.stalkgram.com.stalkgramplus.R;
import app.stalkgram.com.stalkgramplus.main.UI.MainActivity;
import app.stalkgram.com.stalkgramplus.main.UI.MainView;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class StalkgramMainActivityTest extends BaseTest {
    @Mock
    private MainPresenter presenter;
    private MainView view;
    private MainActivity activity;
    private ActivityController<MainActivity> controller;
    private static String URL = "https://www.instagram.com/testurl";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MainActivity mainActivity = new MainActivity() {

            @Override
            public MainPresenter getPresenter() {
                return presenter;
            }

            @Override
            public String getClipBoardText() {
                return URL;
            }
        };

        controller = ActivityController.of(
                Robolectric.getShadowsAdapter(), mainActivity
        ).create().visible();
        activity = (MainActivity) controller.get();
        view = (MainView) activity;
    }

    @Test
    public void onActivityCreated_getsNextRecipe() {
        verify(presenter).onCreate();
    }

    @Test
    public void onActivityDestroyed_destroyPresenter() {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void showProgress_progressBarVisible() {
        view.showProgress();

        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.progress);
        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void showProgress_videoViewInvisible() {
        view.showProgress();

        VideoView videoView = (VideoView) activity.findViewById(R.id.videoView);
        assertEquals(View.INVISIBLE, videoView.getVisibility());
    }

    @Test
    public void showProgress_imageViewInvisible() {
        view.showProgress();

        ImageView imageView = (ImageView) activity.findViewById(R.id.imgView);
        assertEquals(View.INVISIBLE, imageView.getVisibility());
    }

    @Test
    public void showProgress_buttonsDisabled() {
        view.showProgress();

        LinearLayout btnSetAs = (LinearLayout) activity.findViewById(R.id.btnSetAs);
        LinearLayout btnShare = (LinearLayout) activity.findViewById(R.id.btnShare);
        assertEquals(false, btnSetAs.isEnabled());
        assertEquals(false, btnShare.isEnabled());
    }

    @Test
    public void hideProgress_buttonsEnabled() {
        view.hideProgress();

        LinearLayout btnSetAs = (LinearLayout) activity.findViewById(R.id.btnSetAs);
        LinearLayout btnShare = (LinearLayout) activity.findViewById(R.id.btnShare);
        assertEquals(true, btnSetAs.isEnabled());
        assertEquals(true, btnShare.isEnabled());
    }

    @Test
    public void pasteAnddownloadButtonClicked_downloadFile() {
        FloatingActionButton btnFab = (FloatingActionButton) activity.findViewById(R.id.fab);
        btnFab.performClick();

        verify(presenter).downloadFile(URL);
    }
}
