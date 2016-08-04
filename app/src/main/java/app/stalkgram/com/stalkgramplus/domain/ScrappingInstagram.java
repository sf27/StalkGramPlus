package app.stalkgram.com.stalkgramplus.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class ScrappingInstagram {
    public final static String VIDEO_KEY = "video";
    public final static String IMAGE_KEY = "image";
    public final static String USERNAME_KEY = "user";
    private String url;

    public ScrappingInstagram() {
        this.url = null;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getData(String url) {
        setUrl(url);
        return getData();
    }

    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();
        Element head = getHead(url);
        if (head != null) {
            data.put(VIDEO_KEY, getVideoUrl(head));
            data.put(IMAGE_KEY, getImageUrl(head));
            data.put(USERNAME_KEY, getUsername(head));
            return data;
        } else {
            return null;
        }
    }

    private Element getHead(String url) {
        Element head;
        try {
            Document doc = Jsoup.connect(url).get();
            head = doc.head();
        } catch (Exception e) {
            head = null;
        }
        return head;
    }

    private String getUsername(Element head) {
        Elements elements = head.getElementsByAttributeValue("property", "og:description");
        if (elements.size() > 0) {
            String description = elements.get(0).attr("content");
            int indexBefore = description.indexOf("@");
            int indexAfter = description.indexOf(" ", indexBefore);
            return description.substring(indexBefore, indexAfter);
        }
        return null;
    }

    private String getImageUrl(Element head) {
        Elements elements = head.getElementsByAttributeValue("property", "og:image");
        return elements.size() > 0 ? elements.get(0).attr("content") : null;
    }

    private String getVideoUrl(Element head) {
        Elements elements = head.getElementsByAttributeValue("property", "og:video");
        return elements.size() > 0 ? elements.get(0).attr("content") : null;
    }
}
