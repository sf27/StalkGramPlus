package app.stalkgram.com.stalkgramplus.main.UI;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import javax.inject.Inject;

import app.stalkgram.com.stalkgramplus.R;
import app.stalkgram.com.stalkgramplus.StalkgramApp;
import app.stalkgram.com.stalkgramplus.main.DI.MainComponent;
import app.stalkgram.com.stalkgramplus.main.MainPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.imgView)
    ImageView imageView;

    @BindView(R.id.videoView)
    VideoView videoView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.txt_link)
    EditText txtLink;

    @BindView(R.id.btnSetAs)
    LinearLayout btnSetAs;

    @BindView(R.id.btnShare)
    LinearLayout btnShare;

    @BindView(R.id.layoutMainContainer)
    RelativeLayout container;

    @Inject
    MainPresenter mainPresenter;

    private StalkgramApp app;
    private MainComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        app = (StalkgramApp) getApplication();
        setupInjection();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        mainPresenter.onCreate();
        mainPresenter.checkIfStorageIsAvailable();

        setInputs(true);
    }

    private void setupInjection() {
        component = app.getMainComponent(this, this);
//        component.inject(this);
        mainPresenter = getPresenter();
    }

    public MainPresenter getPresenter() {
        return component.getPresenter();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_videos) {
        } else if (id == R.id.nav_images) {
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getClipBoardText() {
        ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (myClipboard.hasPrimaryClip()) {
            ClipData abc = myClipboard.getPrimaryClip();
            ClipData.Item item = abc.getItemAt(0);
            return item.getText().toString();
        }
        return "";
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        runOnUiThread(() -> {
            imageView.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            setInputs(false);
            progressBar.setIndeterminate(false);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.invalidate();
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onProgress(final int progress) {
        runOnUiThread(() -> progressBar.setProgress(progress));
    }

    @Override
    public void hideProgress() {
        runOnUiThread(() -> {
            setInputs(true);
            imageView.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    @OnClick(R.id.fab)
    public void onDownload() {
        onPaste();
    }

    @Override
    public void downloadImageSuccess(final String imagePath) {
        imageView.post(() -> {
            imageView.setImageDrawable(Drawable.createFromPath(imagePath));
            imageView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void downloadVideoSuccess(final String videoPath) {
        this.videoView.post(() -> {
            final MediaController mediaController;
            mediaController = new MediaController(MainActivity.this, true);
            mediaController.setEnabled(false);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(                                         FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            );

            videoView.setLayoutParams(lp);
            videoView.setMediaController(mediaController);
            videoView.setVideoPath(videoPath);
            videoView.setOnPreparedListener(mediaPlayer -> {
                mediaController.setEnabled(true);
                mediaPlayer.start();// starts playing the video
            });

            videoView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void downloadError(final String error) {
        runOnUiThread(() -> {
            txtLink.setText("");
            String msgError = String.format(
                    getString(R.string.main_error_downloading_file), error
            );
            Snackbar.make(
                    container, msgError, Snackbar.LENGTH_SHORT
            ).show();
        });
    }

    private void setInputs(final boolean enabled) {
        runOnUiThread(() -> {
            txtLink.setEnabled(enabled);
            btnSetAs.setEnabled(enabled);
            btnShare.setEnabled(enabled);
        });
    }

    @OnClick(R.id.btnSetAs)
    public void onSetAs() {
        mainPresenter.setAsFile();
    }

    @OnClick(R.id.btnShare)
    public void onShare() {
        mainPresenter.shareFile();
    }

    private void onPaste(){
        String url = getClipBoardText();
        if (url.isEmpty() || !url.contains("https://www.instagram.com/")) {
            Snackbar.make(
                    container, R.string.main_error_empty_clipboard, Snackbar.LENGTH_SHORT
            ).show();
            return;
        }
        txtLink.setText(url);
        mainPresenter.downloadFile(url);
    }
}
