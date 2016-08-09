package app.stalkgram.com.stalkgramplus.main;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;

import app.stalkgram.com.stalkgramplus.BaseTest;
import app.stalkgram.com.stalkgramplus.domain.DownloadFileFromURL;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

import static org.mockito.Mockito.verify;

public class StalkgramMainRepositoriesTest extends BaseTest {
    private static final String URL = "https://www.instagram.com/testurl";
    private static final String USERNAME = "@test";
    private static final String FILE_PATH = "/media/test.jpg";
    @Mock
    private Context context;
    @Mock
    private EventBus eventBus;
    @Mock
    private MainEvent mainEvent;
    @Mock
    private DownloadFileFromURL downloadFileFromURL;
    private MainVideoRepositoryImpl mainVideoRepository;
    private MainImageRepositoryImpl mainImageRepository;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mainVideoRepository = new MainVideoRepositoryImpl(downloadFileFromURL);
        mainImageRepository = new MainImageRepositoryImpl(downloadFileFromURL);
    }

    @Test
    public void testMainVideoRepository_callsDownloadFile() {
        mainVideoRepository.downloadFile(USERNAME, URL);
        verify(downloadFileFromURL).setEventType(MainEvent.onDownloadVideo);
        verify(downloadFileFromURL).execute(URL);
    }

    @Test
    public void testMainImageRepository_callsDownloadFile() {
        mainImageRepository.downloadFile(USERNAME, URL);
        verify(downloadFileFromURL).setEventType(MainEvent.onDownloadImage);
        verify(downloadFileFromURL).execute(URL);
    }

    @Test
    public void testMainImageRepository_callsPostImageSuccess() {
        downloadFileFromURL = new DownloadFileFromURL(eventBus, mainEvent);
        downloadFileFromURL.postImageSuccess(FILE_PATH);
        verify(eventBus).post(mainEvent);
    }

    @Test
    public void testMainImageRepository_callsPostVideoSuccess() {
        downloadFileFromURL = new DownloadFileFromURL(eventBus, mainEvent);
        downloadFileFromURL.postVideoSuccess(FILE_PATH);
        verify(eventBus).post(mainEvent);
    }

    @Test
    public void testMainImageRepository_callsPostError() {
        downloadFileFromURL = new DownloadFileFromURL(eventBus, mainEvent);
        downloadFileFromURL.postError("test error");
        verify(eventBus).post(mainEvent);
    }

    @Test
    public void testMainImageRepository_callsPostProgress() {
        downloadFileFromURL = new DownloadFileFromURL(eventBus, mainEvent);
        downloadFileFromURL.postProgress(50);
        verify(eventBus).post(mainEvent);
    }
}