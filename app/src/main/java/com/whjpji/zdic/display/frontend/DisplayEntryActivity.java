package com.whjpji.zdic.display.frontend;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.whjpji.zdic.R;
import com.whjpji.zdic.display.backend.entry.ZdicEntry;
import com.whjpji.zdic.display.backend.tabpage.ZdicTabPage;
import com.whjpji.zdic.search.backend.explorer.ZdicExplorer;
import com.whjpji.zdic.search.frontend.MainActivity;

public class DisplayEntryActivity extends AppCompatActivity {
    public static final String HTML_MIME_TYPE = "text/html; charset=utf-8";
    private ZdicExplorer mExplorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entry);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ZdicTabPagesAdapter adapter = new ZdicTabPagesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        Intent intent = getIntent();
        String html = intent.getStringExtra(MainActivity.HTML_ARG);
        ZdicEntry.EntryType entryType = ZdicEntry.EntryType.valueOf(
                intent.getStringExtra(MainActivity.ENTRY_TYPE_ARG)
        );
        ZdicEntry entry = ZdicEntry.newEntry(html, entryType);
        if (entry != null) {
            WebView headerWebView = (WebView) findViewById(R.id.header_webView);
            headerWebView.getSettings().setJavaScriptEnabled(true);
            headerWebView.loadDataWithBaseURL(
                    ZdicEntry.ZDIC_BASE_URL, entry.getHeaderHtml(), HTML_MIME_TYPE, null, null
            );
            entry.setOnTabPageChangedListener(adapter);
            entry.addAllTabPages();
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // setHtml(getIntent().getStringExtra(MainActivity.HTML_ARG));

    }

    /*
    public void setIndexString(String indexString) {
        this.mIndexString = indexString;
        mExplorer = new ZdicWordExplorer(DisplayEntryActivity.this, mIndexString);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mEntry = mExplorer.getEntry();
                mEntry.addAllTabPages();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mEntry.display();
            }
        }.execute();

    }
    */

    // public void setHtml(String html) {
    //     mHtml = html;
    //     new AsyncTask<Void, Void, Void>() {
    //         @Override
    //         protected Void doInBackground(Void... voids) {
    //             mEntry = new ZdicEntry(mHtml);
    //             mEntry.addAllTabPages();
    //             return null;
    //         }

    //         @Override
    //         protected void onPostExecute(Void aVoid) {
    //             super.onPostExecute(aVoid);
    //             mEntry.display();
    //         }
    //     }.execute();
    // }

}
