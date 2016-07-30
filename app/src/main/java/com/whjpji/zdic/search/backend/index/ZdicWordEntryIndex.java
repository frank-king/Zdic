package com.whjpji.zdic.search.backend.index;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;
import com.whjpji.zdic.search.backend.explorer.ZdicWordExplorer;

import org.jsoup.Jsoup;

/**
 * Created by whjpji on 16-7-29.
 */
public class ZdicWordEntryIndex extends ZdicEntryIndex {

    public ZdicWordEntryIndex(String indexString) {
        super(indexString, null);
        mEntryType = ZdicEntry.EntryType.WORD;
    }

    @Override
    public String getHtml() {
        return new ZdicWordExplorer(mIndexString).getContentHtml();
    }
}
