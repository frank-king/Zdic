package com.whjpji.zdic.search.backend;

/**
 * Created by whjpji on 16-7-24.
 * @author whjpji
 * @version 1.0
 */

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.whjpji.zdic.search.backend.explorer.ZdicPhraseExplorer;
import com.whjpji.zdic.search.backend.index.ZdicWordEntryIndex;
import com.whjpji.zdic.search.frontend.ResultFragment;
import com.whjpji.zdic.display.backend.entry.ZdicEntry;
import com.whjpji.zdic.search.backend.explorer.ZdicExplorer;
import com.whjpji.zdic.search.backend.index.ZdicEntryIndex;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 一個用於處理漢典查詢結果的類
 */
public class ZdicParser {
    // 查詢內容
    private String mIndexString;
    // 對應的查詢url
    private String mUrl = null;
    // 查詢範圍枚舉値
    public enum Scope {
        WORD,               // 字典
        PHRASE,             // 詞典
        IDOIM,              // 成語
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
    private Dictionary mDictionary;
    // 查詢方式
    private LookupType mLookupType;
    // 漢典網址
    private static final String ZDIC_BASE_URL = "http://www.zdic.net/";
    // 查詢超時設置
    private static final int TIME_OUT = 3000;

    public static final String INDEX_ARG = "INDEX";

    private ZdicExplorer mExplorer;
    private AppCompatActivity mActivity;
    private ZdicEntry mEntry;
    private FragmentManager mManager;
    private ResultFragment mFragment;
    private OnEntryIndexListener mEntryIndexListener;

    public ZdicParser() {}

    /**
     * 根據指定的查詢內容及查詢區域，構造查詢對象。
     * @param indexString 所需查詢的內容。
     * // @param scope 所需查詢的範圍。
     * // @param dict 所需查詢的字典。
     */
    public ZdicParser(AppCompatActivity activity, String indexString) {
        // Log.d("DATA", "Content: " + content);
        // // System.out.println("Content: " + content);
        mIndexString = indexString;
        mActivity = activity;
        // mScope = scope;
        // mDictionary = dict;
        // mLookupType = LookupType.HAN_ZI;
        // setIndexString(indexString);
        // mHtml = mExplorer.

        // _getUrl();
        // _getUrlByZdic();
        // while (mUrl == null);
        // System.out.println("Url: " + mUrl);
        // Log.d("data", "Url: " + getUrl());
    }

    public void parse(String indexString) {
        mIndexString = indexString;
        clearEntryIndices();
        if (indexString.length() == 1)
            pushEntryIndex(new ZdicWordEntryIndex(mIndexString));
        new AsyncTask<String, Void, ArrayList<ZdicEntryIndex>>() {
            @Override
            protected ArrayList<ZdicEntryIndex> doInBackground(String... args) {
                return new ZdicPhraseExplorer(mIndexString).getEntryIndices();
            }

            @Override
            protected void onPostExecute(ArrayList<ZdicEntryIndex> entryIndices) {
                super.onPostExecute(entryIndices);
                pushEntryIndex(entryIndices);
            }
        }.execute(mIndexString);
    }

    private void pushEntryIndex(ZdicEntryIndex entryIndex) {
        mEntryIndexListener.onEntryIndexPush(entryIndex);
    }

    private void pushEntryIndex(ArrayList <ZdicEntryIndex> entryIndices) {
        mEntryIndexListener.onEntryIndexPush(entryIndices);
    }

    private void clearEntryIndices() {
        mEntryIndexListener.onEntryIndexClear();
    }

    /*
    public void setIndexString(String indexString) {
        mIndexString = indexString;
        if (mFragment == null) {
            FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
            mFragment = ResultFragment.newInstance(mIndexString);
            transaction.add(R.id.search_results, mFragment);
            transaction.commit();
        } else {
            mFragment.setIndexString(mIndexString);
        }
        if (mFragment.getView() != null) {
            LinearLayout linearLayout = (LinearLayout) mFragment.getView().findViewById(R.id.search_results);
            // Button button = new Button(mActivity);
            // button.setText(mIndexString);
            // button.setOnClickListener(new View.OnClickListener() {
            //     @Override
            //     public void onClick(View view) {
            //         Intent intent = new Intent(mActivity, DisplayEntryActivity.class);
            //         intent.putExtra(INDEX_ARG, mIndexString);
            //         mActivity.startActivity(intent);

            //     }
            // });
            // gridLayout.addView(button);
        }

    }
    */

    /**
     * 返回解析後的HTML字串以便顯示內容。
     * @return 解析後的HTML字串
     */
    // public String getParsedHtml() {
    //     ZdicEntry entry = mExplorer.getEntry();
    //     /// entry.getTabPages();
    //     return "";
    //     // String result = "";

    //     // try {
    //     //     Document document = Jsoup.connect(mUrl).timeout(TIME_OUT).get();
    //     //     // System.out.println(document.html());
    //     //     Element content = document.getElementById("content");
    //     //     Element explain;
    //     //     Element tabPage = null;
    //     //     switch (mDictionary) {
    //     //         case HAN_DIAN:
    //     //             explain = content.getElementById("jb");
    //     //             tabPage = explain.getElementsByClass("tab-page").first();
    //     //             break;
    //     //         case HAN_DIAN_DETAIL:
    //     //             explain = content.getElementById("xx");
    //     //             tabPage = explain.getElementsByClass("tab-page").first();
    //     //             break;
    //     //         case KANG_XI:
    //     //             explain = content.getElementById("kx");
    //     //             tabPage = explain.getElementsByClass("tab-page").first().getElementById("kxnr");
    //     //             break;
    //     //         case SHUO_WEN:
    //     //             explain = content.getElementById("sw");
    //     //             tabPage = explain.getElementsByClass("tab-page").first().getElementById("swnr");
    //     //             break;
    //     //         case YIN_YUN:
    //     //             explain = content.getElementById("zy");
    //     //             tabPage = explain.getElementsByClass("tab-page").first();
    //     //     }
    //     //     if (tabPage != null) {
    //     //         Elements redundant = tabPage.getElementsMatchingText(".*[漢汉].*典.*[網网]?.*");
    //     //         if (redundant != null) redundant.remove();
    //     //     }
    //     //     result = tabPage.html();
    //     // } catch (IOException e) {
    //     //     e.printStackTrace();
    //     // } catch (NullPointerException e) {
    //     //     return null;
    //     // }
    //     // return result;
    // }

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
                        .add("q", mIndexString)
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
        switch (mDictionary) {
            case HAN_DIAN:
                dictCode = "js/";
                break;
            case HAN_DIAN_DETAIL:
                dictCode = "xs/";
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
        String unicode = characterToUnicode(mIndexString);
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

    public void setOnEntryIndexListener(OnEntryIndexListener context) {
        mEntryIndexListener = context;
    }

    public interface OnEntryIndexListener {
        void onEntryIndexPush(ZdicEntryIndex entryIndex);
        void onEntryIndexPush(ArrayList <ZdicEntryIndex> entryIndices);
        void onEntryIndexClear();
    }
}
