package com.whjpji.zdic.display.backend.tabpage;

import org.jsoup.nodes.Element;

/**
 * Created by whjpji on 16-7-29.
 */
public class ZdicIdoimTabPage extends ZdicTabPage {

    public ZdicIdoimTabPage(String html, String title) {
        super(html, title);
    }

    @Override
    Element selectTabPage(Element content) {
        return null;
    }
}
