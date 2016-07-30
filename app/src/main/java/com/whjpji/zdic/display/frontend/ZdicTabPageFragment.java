package com.whjpji.zdic.display.frontend;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.whjpji.zdic.R;
import com.whjpji.zdic.display.backend.entry.ZdicEntry;


/**
 * Use the {@link ZdicTabPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZdicTabPageFragment extends Fragment {
    private static final String PLAIN_MIME_TYPE = "text/plain; charset=utf-8";
    private static final String CONTENT_ARG = "CONTENT";
    private static final String TITLE_ARG = "TITLE";
    private static final String DICTIONARY_ARG = "DICTIONARY";
    private String mContent;
    private String mTitle;
    private String mDictionary;
    private WebView mWebView;
    private String mHtmlData;

    public ZdicTabPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param content 查詢結果
     * @return A new instance of fragment ZdicTabPageFragment.
     */
    public static ZdicTabPageFragment newInstance(String content) {
        ZdicTabPageFragment fragment = new ZdicTabPageFragment();
        Bundle args = new Bundle();
        args.putString(CONTENT_ARG, content);
        // args.putString(TITLE_ARG, title);
        // args.putString(DICTIONARY_ARG, dictionary.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mContent = bundle.getString(CONTENT_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout = (FrameLayout)
                inflater.inflate(R.layout.fragment_tabpage, container, false);
        mWebView = (WebView) frameLayout.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadDataWithBaseURL(
                ZdicEntry.ZDIC_BASE_URL, mContent, DisplayEntryActivity.HTML_MIME_TYPE, null, null
        );
        // if (mHtmlData == null)
        //     new SearchTask().execute(mContent, mDictionary);
        // else {
        //     mWebView.loadData(mHtmlData, HTML_MIME_TYPE, null);
        // }
        return frameLayout;
    }

    // private final class SearchTask extends AsyncTask<String, Void, String> {

    //     @Override
    //     protected String doInBackground(String... args) {
    //         // 傳入的第一個參數爲所需查詢的內容
    //         String content = args[0];
    //         ZdicParser.Dictionary dictionary = ZdicParser.Dictionary.HAN_DIAN;
    //         ZdicParser.Scope scope = ZdicParser.Scope.WORD;
    //         // 傳入的第二個參數爲所需查詢的字典
    //         if (args.length > 1)
    //             dictionary = ZdicParser.Dictionary.valueOf(args[1]);
    //         // 傳入的第三個參數爲所需查詢的範圍
    //         if (args.length > 2)
    //             scope = ZdicParser.Scope.valueOf(args[2]);
    //         // ZdicParser parser = new ZdicParser(content, scope, dictionary);
    //         return parser.getParsedHtml();
    //     }

    //     @Override
    //     protected void onPostExecute(String htmlData) {
    //         super.onPostExecute(htmlData);
    //         mHtmlData = htmlData;
    //         if (htmlData != null) {
    //             Log.d("data", "HTML: " + htmlData);
    //             mWebView.loadData(htmlData, HTML_MIME_TYPE, null);
    //         } else {
    //             mWebView.loadData(getString(R.string.searchFailure), PLAIN_MIME_TYPE, null);
    //             Log.e("error", "HTML data not found");
    //         }
    //     }

    // }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }
}
