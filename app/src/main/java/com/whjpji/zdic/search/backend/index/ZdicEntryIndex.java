package com.whjpji.zdic.search.backend.index;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;

/**
 * Created by whjpji on 16-7-29.
 */
public abstract class ZdicEntryIndex {
    protected String mIndexString;
    protected String mUrl;
    protected ZdicEntry.EntryType mEntryType;

    public ZdicEntryIndex(String indexString, String url) {
        mUrl = url;
        mIndexString = indexString;
    }

    public String getIndexString() {
        return mIndexString;
    }

    abstract public String getHtml();

    public ZdicEntry.EntryType getEntryType() {
        return mEntryType;
    }

    // abstract protected ZdicEntry getEntry();
}
