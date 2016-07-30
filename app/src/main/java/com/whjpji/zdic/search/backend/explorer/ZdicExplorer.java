package com.whjpji.zdic.search.backend.explorer;

import android.util.Log;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by whjpji on 16-7-28.
 */
abstract public class ZdicExplorer {
    protected static final String ZDIC_SEARCH_URL = "http://www.zdic.net/sousuo/";
    protected String mIndexString;
    // protected String mUrl;
    protected String mResponseHtml;
    protected ZdicEntry mEntry;
    // protected Element mContentElement;

    public enum IndexType {
        WORD,           // 漢字
        PHRASE,         // 詞組
        IDOIM,          // 成語

        STROKES,        // 筆順
        ASSEMBLE,       // 漢字拆分

        PINYIN,         // 拼音
        WUBI,           // 五筆
        CANGJIE,        // 倉頡
        CORNERS,        // 四角
        UNICODE,        // unicode
    }

    protected IndexType mIndexType;

    public ZdicExplorer(String indexString) {
        mIndexString = indexString;
        mIndexType = IndexType.WORD;
    }

    public void setIndexString(String indexString) {
        this.mIndexString = indexString;
    }

    public String getIndexString() {
        return mIndexString;
    }

    // public Element getContentElement() {
    //     if (search())
    //         filterContentElement();
    //     return mContentElement;
    // }

    // public ZdicEntry getEntry() {
    //     if (mEntry == null)
    //         newEntry(getResponseHtml());
    //     return mEntry;
    // }

    protected String getResponseHtml() {
        search();
        return mResponseHtml;
    }

    protected boolean search() {
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = makePostFormBody();
        Request request = new Request.Builder().url(ZDIC_SEARCH_URL).post(formBody).build();
        Response response = null;
        boolean success = false;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                success = true;
                // mUrl = response.url;
                // Log.d("data", "Url: " + mUrl);
                mResponseHtml = response.body().string();
                Log.d("data", "Content: " + mResponseHtml);
            } else {
                Log.e("error", "Url not found. Code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", "Url not found.");
        } finally {
            if (response != null)
                response.close();
        }
        return success;
    }

    abstract protected FormBody makePostFormBody();
}

