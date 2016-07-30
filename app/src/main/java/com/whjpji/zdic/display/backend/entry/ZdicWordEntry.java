package com.whjpji.zdic.display.backend.entry;

import com.whjpji.zdic.display.backend.tabpage.ZdicWordTabPage;

/**
 * Created by whjpji on 16-7-28.
 */
public class ZdicWordEntry extends ZdicEntry {
    public ZdicWordEntry(String responseHtml) {
        super(responseHtml);
        mEntryType = EntryType.WORD;
    }

}
