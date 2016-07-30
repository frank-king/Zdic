package com.whjpji.zdic.display.backend.tabpage;

import org.jsoup.nodes.Element;

/**
 * Created by whjpji on 16-7-29.
 */
public class ZdicPharseTabPage extends ZdicTabPage {
    protected static final String TAB_PAGE_SELECTOR = "[class=tab-page]";

    public ZdicPharseTabPage(String html, String title) {
        super(html, title);
    }

    @Override
    Element selectTabPage(Element content) {
        return content.select(TAB_PAGE_SELECTOR).first();
    }
}
