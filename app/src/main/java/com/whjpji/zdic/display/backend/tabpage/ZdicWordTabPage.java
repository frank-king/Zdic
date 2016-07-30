package com.whjpji.zdic.display.backend.tabpage;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by whjpji on 16-7-28.
 */
public class ZdicWordTabPage extends ZdicTabPage {
    protected static final String TAB_PAGE_SELECTOR = "[class=tab-page]";
    protected static final String IMAGE_TEXT_CONVERTER_SELECTOR = "div[class=hsan]";

    public ZdicWordTabPage(String html, String title) {
        super(html, title);
    }

    @Override
    protected Element selectTabPage(Element content) {
        return content.select(TAB_PAGE_SELECTOR).first();
    }

    protected static ZdicWordTabPage newTabPage(String html, String title) {
        if (!hasImageText(html))
            return new ZdicWordTabPage(html, title);
        else
            return new ZdicWordTabPageWithImageText(html, title);
    }

    protected static boolean hasImageText(String html) {
        try {
            Document document = Jsoup.parse(html);
            return !document.select(IMAGE_TEXT_CONVERTER_SELECTOR).isEmpty();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
