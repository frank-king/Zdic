package com.whjpji.zdic.util;

/**
 * Created by whjpji on 16-7-24.
 * @author whjpji
 * @version 1.0
 */

import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 一個用於處理漢典查詢結果的類
 */
public class ZdicParser {
    // 查詢內容
    private String mContent;
    // 對應的查詢url
    private String mUrl = null;
    // 查詢範圍枚舉値
    public enum Scope {
        ZI_DIAN,            // 字典
        CI_DIAN,            // 詞典
        CHENG_YU,           // 成語
    }
    // 查詢字典枚舉値
    public enum Dictionary {
        HAN_DIAN,           // 基本解釋
        HAN_DIAN_DETAIL,    // 詳細解釋
        KANG_XI,            // 康熙字典
        SHUO_WEN,           // 說文解字
        YIN_YUN,            // 音韻方言
        ZI_YUAN,            // 字源字形
        DISCUSS,            // 網友討論
    }
    // 查詢方式的枚舉値
    public enum LookupType {
        HAN_ZI,             // 漢字
        PIN_YIN,            // 拼音
        WU_BI,              // 五筆
        CANG_JIE,           // 倉頡
        CORNER,             // 四角
        UNICODE,            // unicode
        ASSEMBLE,           // 漢字拆分
    }
    // 查詢範圍
    private Scope mScope;
    // 查詢字典
    private Dictionary mDict;
    // 查詢方式
    private LookupType mLookupType;
    // 漢典網址
    private static final String ZDIC_BASE_URL = "http://www.zdic.net/";
    // 查詢超時設置
    private static final int TIME_OUT = 3000;


    /**
     * 根據指定的查詢內容及查詢區域，構造查詢對象。
     * @param content 所需查詢的內容
     * @param scope 所需查詢的範圍
     * @param dict 所需查詢的字典。
     */
    public ZdicParser(String content, Scope scope, Dictionary dict) {
        // Log.d("debug", "Content: " + content);
        System.out.println("Content: " + content);
        mContent = content;
        mScope = scope;
        mDict = dict;
        mLookupType = LookupType.HAN_ZI;
        _getUrl();
        // _getUrlByZdic();
        // Log.d("debug", "Url: " + getUrl());
        // while (mUrl == null);
        System.out.println("Url: " + mUrl);
    }

    /**
     * 返回解析後的HTML字串以便顯示內容。
     * @return 解析後的HTML字串
     */
    public String getParsedHtml() {
        String result = "";

        try {
            Document document = Jsoup.connect(mUrl).timeout(TIME_OUT).get();
            // System.out.println(document.html());
            Element content = document.getElementById("content");
            Element explain = content.getElementById("jb");
            // Element explain = content.getElementById("kx");
            Element tabPage = explain.getElementsByClass("tab-page").first();
            tabPage.getElementsMatchingText(
                    ".*[漢汉].*典.*[網网]?.*"
            ).remove();
            result = tabPage.html();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return null;
        }
        return result;
    }

    /**
     * 返回查詢內容對應的url。
     * @return 查詢內容對應的url。
     */
    public String getUrl() {
        if (mUrl == null)
            _getUrlByZdic();
        return this.mUrl;
    }

    /**
     * 通過向漢典發送POST請求的方式獲取Url。
     */
    private void _getUrlByZdic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("lb_a", "hp")
                        .add("lb_b", "mh")
                        .add("lb_c", "mh")
                        .add("tp", "tp1")
                        .add("q", mContent)
                        .build();
                Request request = new Request.Builder().url(ZDIC_BASE_URL + "sousuo/").post(formBody).build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        mUrl = response.header("Location");
                    } else if (response.isRedirect()) {
                        System.out.println("Redirected.");
                    } else {
                        System.out.println("Url not found. Code: ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 得查詢內容對應的url。
     */
    private void _getUrl() {
        String dictCode = "";
        switch (mDict) {
            case HAN_DIAN:
                dictCode = "js/";
                break;
            case HAN_DIAN_DETAIL:
                dictCode = "xx/";
                break;
            case KANG_XI:
                dictCode = "kx/";
                break;
            case SHUO_WEN:
                dictCode = "sw/";
                break;
            case YIN_YUN:
                dictCode = "yy/";
                break;
            case ZI_YUAN:
                dictCode = "zy/";
                break;
            case DISCUSS:
                dictCode = "wy/";
                break;
            default:
                dictCode = "js/";
                break;
        }
        String unicode = characterToUnicode(mContent);
        String zipCode = _getZipCode(unicode);
        switch (mLookupType) {
            case HAN_ZI:
                mUrl = ZDIC_BASE_URL + "z/" + zipCode + "/" + dictCode + unicode + ".htm";
                break;
        }
    }

    /**
     * 從unicode字串計算其所在的壓縮區域。
     * @param unicodeStr unicode字串
     * @return unicode字串所在的壓縮區域。
     */
    private static String _getZipCode(String unicodeStr) {
        int unicode = 0;
        for (int i = 0; i < unicodeStr.length(); ++i) {
            char ch = unicodeStr.charAt(i);
            int digit = 0;
            if (Character.isUpperCase(ch) && ch <= 'F')
                digit = ch - 'A' + 10;
            else if (Character.isLowerCase(ch) && ch <= 'f')
                digit = ch - 'a' + 10;
            else if (Character.isDigit(ch))
                digit = ch - '0';
            unicode = (unicode << 4) | digit;
        }
        return Integer.toHexString((unicode - 1) / 1000 + 1);
    }

    /**
     * 判斷字串是否爲漢字
     * @param content 需判斷的字串
     * @return 字串是否爲漢字的布爾値
     */
    public static boolean isChineseCharacter(String content) {
        // TODO: 16-7-24 加入漢字編碼範圍的限定
        return true;
    }

    /**
     * 得到漢字的unicode字串
     * @param character 漢字
     * @return 漢字的unicode字串
     */
    @NonNull
    public static String characterToUnicode(String character) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); ++i) {
            char ch = character.charAt(i);
            buffer.append(Integer.toHexString((int) ch));
        }
        return buffer.toString().toUpperCase();
    }
}
