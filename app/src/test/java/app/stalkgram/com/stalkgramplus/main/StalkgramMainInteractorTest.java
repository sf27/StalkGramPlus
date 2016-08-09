package app.stalkgram.com.stalkgramplus.main;

import android.content.Context;

import org.apache.tools.ant.Main;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;

import app.stalkgram.com.stalkgramplus.BaseTest;
import app.stalkgram.com.stalkgramplus.domain.ScrappingInstagram;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.events.MainEvent;

import static org.mockito.Mockito.verify;

public class StalkgramMainInteractorTest extends BaseTest {
    @Mock
    private MainImageRepositoryImpl mainImageRepository;
    @Mock
    private MainVideoRepositoryImpl mainVideoRepository;
    @Mock
    private Context context;
    @Mock
    private EventBus eventBus;
    @Mock
    private MainEvent mainEvent;
    private MainInteractor interactor;
    private static final  String URL = "https://www.instagram.com/testurl";
    private static final  String USERNAME = "@test";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new MainInteractorImpl(context, mainImageRepository, mainVideoRepository, eventBus, mainEvent);
    }

    @Test
    public void testDownloadFile_callsMainImageRepository() {
        HashMap<String, String> data = new HashMap<>();

        data.put(ScrappingInstagram.USERNAME_KEY, USERNAME);
        data.put(ScrappingInstagram.VIDEO_KEY, null);
        data.put(ScrappingInstagram.IMAGE_KEY, URL);
        interactor.downloadFile(data);
        verify(mainImageRepository).downloadFile(USERNAME, URL);
    }

    @Test
    public void testDownloadFile_callsMainVideoRepository() {
        HashMap<String, String> data = new HashMap<>();

        data.put(ScrappingInstagram.USERNAME_KEY, USERNAME);
        data.put(ScrappingInstagram.VIDEO_KEY, URL);
        data.put(ScrappingInstagram.IMAGE_KEY, null);

        interactor.downloadFile(data);
        verify(mainVideoRepository).downloadFile(USERNAME, URL);
    }

    @Test
    public void testDownloadFile_callsEventToReportError() {
        interactor.downloadFile(null);
        verify(eventBus).post(mainEvent);
    }
}