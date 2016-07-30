package com.whjpji.zdic.search.backend.explorer;

import com.whjpji.zdic.display.backend.entry.ZdicWordEntry;

import okhttp3.FormBody;

/**
 * Created by whjpji on 16-7-28.
 */
public class ZdicWordExplorer extends ZdicExplorer {

    public ZdicWordExplorer(String indexString) {
        super(indexString);
    }

    @Override
    protected FormBody makePostFormBody() {
        return new FormBody.Builder()
                .add("lb_a", "hp")
                .add("lb_b", "mh")
                .add("lb_c", "mh")
                .add("tp", "tp1")
                .add("q", mIndexString)
                .build();
    }

    public String getContentHtml() {
        return getResponseHtml();
    }

    // @Override
    // protected void format() {
    //     Document document = Jsoup.parse(mContentHtml);
    //     // System.out.println(document.html());
    //     Element content = document.getElementById("content");
    //     Element explain;
    //     Element tabPage = null;
    //     switch (mDictionary) {
    //         case HAN_DIAN:
    //             explain = content.getElementById("jb");
    //             tabPage = explain.getElementsByClass("tab-page").first();
    //             break;
    //         case HAN_DIAN_DETAIL:
    //             explain = content.getElementById("xx");
    //             tabPage = explain.getElementsByClass("tab-page").first();
    //             break;
    //         case KANG_XI:
    //             explain = content.getElementById("kx");
    //             tabPage = explain.getElementsByClass("tab-page").first().getElementById("kxnr");
    //             break;
    //         case SHUO_WEN:
    //             explain = content.getElementById("sw");
    //             tabPage = explain.getElementsByClass("tab-page").first().getElementById("swnr");
    //             break;
    //         case YIN_YUN:
    //             explain = content.getElementById("zy");
    //             tabPage = explain.getElementsByClass("tab-page").first();
    //     }
    //     if (tabPage != null) {
    //         Elements redundant = tabPage.getElementsMatchingText(".*[漢汉].*典.*[網网]?.*");
    //         if (redundant != null) redundant.remove();
    //     }
    //     result = tabPage.html();
    // }

}
