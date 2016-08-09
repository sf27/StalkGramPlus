package app.stalkgram.com.stalkgramplus.main;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;

import app.stalkgram.com.stalkgramplus.BaseTest;
import app.stalkgram.com.stalkgramplus.domain.DownloadPageAndParseHtml;
import app.stalkgram.com.stalkgramplus.lib.base.EventBus;
import app.stalkgram.com.stalkgramplus.main.UI.MainView;

import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;

public class StalkgramMainPresenterTest extends BaseTest {
    @Mock
    private Context context;
    @Mock
    private EventBus eventBus;
    @Mock
    private MainView mainView;
    @Mock
    private MainInteractor mainInteractor;
    @Mock
    private DownloadPageAndParseHtml parseHtml;
    private MainPresenterImpl presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new MainPresenterImpl(context, eventBus, mainView, mainInteractor, parseHtml);
    }

    @Test
    public void onCreate_SubscribedToEventBus() {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void onDestroy_UnsubscribedToEventBusAndViewDestroyed() throws NoSuchFieldException {
        presenter.onDestroy();

        assertNull(presenter.getView());
        verify(eventBus).unregister(presenter);
    }

    @Test
    public void downloadFile_executeParseHtmlTask() {
        String url = "https://www.instagram.com/testurl";
        presenter.downloadFile(url);

        verify(mainView).showProgress();
        verify(mainView).disableInputs();
        verify(parseHtml).setOnCompleteCallback(presenter);
        verify(parseHtml).execute(url);
    }

}
