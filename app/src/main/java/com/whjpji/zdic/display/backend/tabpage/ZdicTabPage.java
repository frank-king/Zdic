package com.whjpji.zdic.display.backend.tabpage;

import android.os.DropBoxManager;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;
import com.whjpji.zdic.display.frontend.ZdicTabPageFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by whjpji on 16-7-28.
 */
public abstract class ZdicTabPage {
    // protected static final String REDUNDANT_PATTERN = ".*[漢汉].{0,20}典.{0,20}[網网]?.{0,20}";
    protected static final String REDUNDANT_PATTERN = "([Zz][Dd][Ii][Cc]\\.[Nn][Ee][Tt])|([漢汉] ?典)";
    protected String mTitle;
    protected String mHtml;
    protected String mParsedHtml;
    // protected ZdicTabPageFragment mFragment;

    public ZdicTabPage(String html, String title) {
        mHtml = html;
        mTitle = title;
        mParsedHtml = getParsedHtml();
        // mFragment = ZdicTabPageFragment.newInstance(getParsedHtml());
    }

    public String getTitle() {
        return mTitle;
    }

    public String getParsedHtml() {
        if (mParsedHtml == null) {
            try {
                Document document = Jsoup.parse(mHtml);
                // System.out.println(document.html());
                Element content = document.getElementById(ZdicEntry.CONTENT_ID);
                Element tabPage = selectTabPage(content);
                // switch (mDictionary) {
                //     case HAN_DIAN:
                //         explain = content.getElementById("jb");
                //         tabPage = explain.getElementsByClass("tab-page").first();
                //         break;
                //     case HAN_DIAN_DETAIL:
                //         explain = content.getElementById("xx");
                //         tabPage = explain.getElementsByClass("tab-page").first();
                //         break;
                //     case KANG_XI:
                //         explain = content.getElementById("kx");
                //         tabPage = explain.getElementsByClass("tab-page").first().getElementById("kxnr");
                //         break;
                //     case SHUO_WEN:
                //         explain = content.getElementById("sw");
                //         tabPage = explain.getElementsByClass("tab-page").first().getElementById("swnr");
                //         break;
                //     case YIN_YUN:
                //         explain = content.getElementById("zy");
                //         tabPage = explain.getElementsByClass("tab-page").first();
                // }
                if (tabPage != null) {
                    Elements redundant = tabPage.getElementsMatchingOwnText(REDUNDANT_PATTERN);
                    if (redundant != null) redundant.remove();
                    mParsedHtml = tabPage.html();
                }
            } catch (NullPointerException e) {
                return null;
            }
        }
        System.out.println(mParsedHtml);
        return mParsedHtml;
    }

    public static ZdicTabPage newTabPage(String html, String title, ZdicEntry.EntryType entryType) {
        switch (entryType) {
            case WORD:
                return ZdicWordTabPage.newTabPage(html, title);
            case PHRASE:
                return new ZdicPharseTabPage(html, title);
            case IDOIM:
                return new ZdicIdoimTabPage(html, title);
            default:
                return null;
        }
    }

    abstract Element selectTabPage(Element content);
}
