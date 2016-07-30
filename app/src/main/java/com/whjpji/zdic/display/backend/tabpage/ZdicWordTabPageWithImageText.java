package com.whjpji.zdic.display.backend.tabpage;

import org.jsoup.nodes.Element;

/**
 * Created by whjpji on 16-7-28.
 */
public class ZdicWordTabPageWithImageText extends ZdicWordTabPage {
    protected static final String TAB_PAGE_SELECTOR = "[class=tab-page] > div";

    public ZdicWordTabPageWithImageText(String html, String title) {
        super(html, title);
    }

    @Override
    protected Element selectTabPage(Element content) {
        return content.select(IMAGE_TEXT_CONVERTER_SELECTOR).first().nextElementSibling();
    }
}
