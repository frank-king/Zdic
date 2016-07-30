package com.whjpji.zdic.search.backend.explorer;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;
import com.whjpji.zdic.search.backend.index.ZdicEntryIndex;
import com.whjpji.zdic.search.backend.index.ZdicPhraseEntryIndex;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import okhttp3.FormBody;

/**
 * Created by whjpji on 16-7-29.
 */
public class ZdicPhraseExplorer extends ZdicExplorer {
    protected static final String LIST_SELECTOR = "li > a[href]";

    public ZdicPhraseExplorer(String indexString) {
        super(indexString);
    }

    @Override
    protected FormBody makePostFormBody() {
        return new FormBody.Builder()
                .add("lb_a", "hp")
                .add("lb_b", "mh")
                .add("lb_c", "mh")
                .add("tp", "tp3")
                .add("q", mIndexString)
                .build();
    }

    public ArrayList <ZdicEntryIndex> getEntryIndices() {
        search();
        Document document = Jsoup.parse(mResponseHtml);
        Element content = document.getElementById(ZdicEntry.CONTENT_ID);
        Elements elements = content.select(LIST_SELECTOR);
        ArrayList <ZdicEntryIndex> entryIndices = new ArrayList <>();
        for (Element element : elements) {
            entryIndices.add(new ZdicPhraseEntryIndex(
                    element.text(), ZdicEntry.ZDIC_BASE_URL + element.attr(ZdicEntry.HREF_ATTR)
            ));
        }
        return entryIndices;
    }
}
