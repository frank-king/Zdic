package com.whjpji.zdic;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.whjpji.zdic.util.ZdicParser;

import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    WebView mWebView = null;
    TextView mTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.editText);
        mWebView = (WebView) findViewById(R.id.webView);
        // mWebView.getSettings().setJavaScriptEnabled(true);

        // 按下「搜索」按鈕後，送出查詢內容，顯示查詢結果
        Button button = (Button) findViewById(R.id.btn_search);
        button.setOnClickListener(new ButtonOnClickListener());
    }

    private final class ButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            new SearchTask().execute(mTextView.getText().toString());
        }
    }

    private final class SearchTask extends AsyncTask <String, Void, String> {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
