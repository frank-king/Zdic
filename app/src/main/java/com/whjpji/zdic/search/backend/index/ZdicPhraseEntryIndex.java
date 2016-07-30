package com.whjpji.zdic.search.backend.index;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by whjpji on 16-7-29.
 */
public class ZdicPhraseEntryIndex extends ZdicEntryIndex {

    public ZdicPhraseEntryIndex(String indexString, String url) {
        super(indexString, url);
        mEntryType = ZdicEntry.EntryType.PHRASE;
    }

    @Override
    public String getHtml() {
        try {
            return Jsoup.connect(mUrl).get().html();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
