package com.whjpji.zdic.display.backend.entry;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;

import com.whjpji.zdic.display.backend.tabpage.ZdicTabPage;
import com.whjpji.zdic.display.frontend.ZdicTabPagesAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by whjpji on 16-7-28.
 */
abstract public class ZdicEntry {
    public static final String CONTENT_ID = "content";
    public static final String ZDIC_BASE_URL = "http://www.zdic.net";
    public static final String HREF_ATTR = "href";
    protected static final String SHARE_ID = "bdsh";
    protected static final String TAB_SELECTOR = "h2[class=tab] > a[href]";
    protected static final String HEADER_SELECTOR = "[class=notice]";
    protected static final String FLASH_SELECTOR = "td";
    protected static final String CURRENT_TAB_SELECTOR = "h2[class=tab selected]";
    protected static final String UNIQUE_TAB_SELECTOR = "h2[class=tab]";
    public enum EntryType {
        WORD,               // 字
        PHRASE,             // 詞
        IDOIM,              // 成語
    }
    protected ZdicTabPagesAdapter mAdapter;
    protected String mIndexString;
    protected String mHtml;
    // protected Document mDocument;
    // protected ArrayList <ZdicTabPage> mTabPages;
    protected ViewPager mViewPager;
    protected EntryType mEntryType;
    protected OnTabPageChangedListener mListener;

    public ZdicEntry(String html) {
        mHtml = html;
        // mTabPages = new ArrayList<>();
        // addTabPage(mHtml);
        // new AsyncTask <Void, Void, Void>() {

        //     @Override
        //     protected Void doInBackground(Void... voids) {
        //         addAllTabPages();
        //         return null;
        //     }

        //     @Override
        //     protected void onPostExecute(Void aVoid) {
        //         super.onPostExecute(aVoid);
        //         display();
        //     }
        // };
        // getTabPages();
        // display();
    }

    public void setOnTabPageChangedListener(OnTabPageChangedListener listener) {
        mListener = listener;
    }

    public String getHeaderHtml() {
        Document document = Jsoup.parse(mHtml);
        Element content = document.getElementById(CONTENT_ID);
        Element header = content.select(HEADER_SELECTOR).first();
        header.getElementById(SHARE_ID).remove();
        if (mEntryType == EntryType.WORD)
            header.getElementsByTag(FLASH_SELECTOR).first().remove();
        return header.html();
    }

    // public void display() {
    //     if (mAdapter == null) {
    //         mAdapter = new ZdicTabPagesAdapter(
    //                 mActivity.getSupportFragmentManager(), this
    //         );
    //         mViewPager = (ViewPager) mActivity.findViewById(R.id.viewPager);
    //         mViewPager.setAdapter(mAdapter);
    //     } else {
    //         mAdapter.notifyDataSetChanged();
    //         // mAdapter.startUpdate(mViewPager);
    //     }
    //         // mAdapter.setContent(mIndexString);
    // }

    public void addAllTabPages() {
        Document document = Jsoup.parse(mHtml);
        Element content = document.getElementById(CONTENT_ID);
        // String currentTitle = content.select(CURRENT_TAB_SELECTOR).first().text();
        Elements tabs = content.select(TAB_SELECTOR);
        Element currentTab = content.select(CURRENT_TAB_SELECTOR).first();
        if (currentTab == null)
            currentTab = content.select(UNIQUE_TAB_SELECTOR).first();
        // Element currentTab = tabs.first().parent().firstElementSibling();
        // System.out.println(currentTab.ownText());
        addTabPage(mHtml, currentTab.ownText(), mEntryType);
        // mTabPages.add(ZdicTabPage.newInstance(mHtml));
        for (final Element tab : tabs) {
            String url = ZDIC_BASE_URL + tab.attr(HREF_ATTR);
            // System.out.println(url);
            new AsyncTask <String, Void, String> () {
                @Override
                protected String doInBackground(String... args) {
                    try {
                        return Jsoup.connect(args[0]).get().html();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String html) {
                    super.onPostExecute(html);
                    if (html != null) {
                        // System.out.println(tab.ownText());
                        addTabPage(html, tab.ownText(), mEntryType);
                    }
                }
            }.execute(url);
        }
        // System.out.println(tabs.html());
    }

    // protected void filterContentElement() {
    //     Document document = Jsoup.parse(mResponseHtml);
    //     mContentElement = document.getElementById("content");
    // }

    public static ZdicEntry newEntry(String html, EntryType entryType) {
        switch (entryType) {
            case WORD:
                return new ZdicWordEntry(html);
            case PHRASE:
                return new ZdicPhraseEntry(html);
            case IDOIM:
                return null; // TODO: 16-7-29
            default:
                return null;
        }
    }

    void addTabPage(String html, String title, EntryType entryType) {
        mListener.onTabPagePush(ZdicTabPage.newTabPage(html, title, entryType));
    }

    // abstract void addTabPageFromUrl(String url);

    public interface OnTabPageChangedListener {
        void onTabPagePush(ZdicTabPage tabPage);
        void onTabPagePush(ArrayList <ZdicTabPage> tabPages);
        void onTabPageClear();
    }
}
