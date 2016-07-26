package com.whjpji.zdic;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.whjpji.zdic.util.ZdicParser;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExplanationFragment.OnSearchButtonClickedListener} interface
 * to handle interaction events.
 * Use the {@link ExplanationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplanationFragment extends Fragment {
    private static final String CONTENT_ARG = "CONTENT";
    private String mContent;
    private WebView mWebView;

    private OnSearchButtonClickedListener mListener;

    public ExplanationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param content 所需查詢的內容
     * @return A new instance of fragment ExplanationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExplanationFragment newInstance(String content) {
        ExplanationFragment fragment = new ExplanationFragment();
        Bundle args = new Bundle();
        args.putString(CONTENT_ARG, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContent = getArguments().getString(CONTENT_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout = (FrameLayout)
                inflater.inflate(R.layout.fragment_explanation, container, false);
        mWebView = (WebView) frameLayout.findViewById(R.id.webView);
        new SearchTask().execute(mContent);
        return frameLayout;
    }

    private final class SearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            // 傳入的第一個參數爲所需查詢的內容
            String content = args[0];
            ZdicParser.Scope scope = ZdicParser.Scope.ZI_DIAN;
            ZdicParser.Dictionary dictionary = ZdicParser.Dictionary.HAN_DIAN;
            // 傳入的第二個參數爲所需查詢的範圍
            if (args.length > 1)
                scope = ZdicParser.Scope.valueOf(args[1]);
            // 傳入的第三個參數爲所需查詢的字典
            if (args.length > 2)
                dictionary = ZdicParser.Dictionary.valueOf(args[2]);
            ZdicParser parser = new ZdicParser(content, scope, dictionary);
            return parser.getParsedHtml();
        }

        @Override
        protected void onPostExecute(String htmlData) {
            super.onPostExecute(htmlData);
            if (htmlData != null) {
                System.out.println("Data: " + htmlData);
                mWebView.loadData(htmlData, "text/html; charset=utf-8", null);
            } else {
                mWebView.loadData(getString(R.string.searchFailure), "text/plain; charset=utf-8", null);
                System.out.println("HTML data not found");
            }
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSearchButtonClicked(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchButtonClickedListener) {
            mListener = (OnSearchButtonClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSearchButtonClickedListener {
        // TODO: Update argument type and name
        void onSearchButtonClicked(Uri uri);
    }
}
