package com.whjpji.zdic.display.backend.entry;

/**
 * Created by whjpji on 16-7-29.
 */
public class ZdicPhraseEntry extends ZdicEntry {

    public ZdicPhraseEntry(String html) {
        super(html);
        mEntryType = EntryType.PHRASE;
    }

}
