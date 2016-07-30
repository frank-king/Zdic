package com.whjpji.zdic.search.frontend;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.whjpji.zdic.R;
import com.whjpji.zdic.display.backend.entry.ZdicEntry;
import com.whjpji.zdic.search.backend.ZdicParser;
import com.whjpji.zdic.search.backend.index.ZdicEntryIndex;
import com.whjpji.zdic.display.frontend.DisplayEntryActivity;

public class MainActivity
        extends AppCompatActivity
        implements ZdicIndicesAdpater.OnEntryIndexButtonClickedListener {
    public static final String HTML_ARG = "HTML";
    public static final String ENTRY_TYPE_ARG = "ENTRY_TYPE";
    private TextView mTextView;
    private Button mButton;
    private ViewPager mViewPager;
    private ResultFragment mFragment;
    private ZdicParser mParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragment = ResultFragment.newInstance();
        attachFragment(mFragment);
        mFragment.setOnEntryIndexButtonClickedListener(this);

        mParser = new ZdicParser();
        mParser.setOnEntryIndexListener(mFragment);

        mTextView = (TextView) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.btn_search);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (mParser == null) {
                //     mParser = new ZdicParser(MainActivity.this, mTextView.getText().toString());
                // } else
                mParser.parse(mTextView.getText().toString());

                // if (mFragment == null) {
                //     mFragment = ResultFragment.newInstance(mTextView.getText().toString());
                //     FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //     transaction.add(R.id.search_results, mFragment);
                //     transaction.commit();
                // } else {
                //     mFragment.setIndexString(mTextView.getText().toString());
                // }

                // Intent intent = new Intent(MainActivity.this, DisplayEntryActivity.class);
                // startActivity(intent);
            }
        });

        // 按下「搜索」按鈕後，送出查詢內容，顯示查詢結果
        // Button button = (Button) findViewById(R.id.btn_search);
        // button.setOnClickListener(new ButtonOnClickListener());
    }

    private void attachFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.search_results, fragment);
        transaction.commit();
    }

    @Override
    public void onEntryButtonClicked(ZdicEntryIndex entryIndex) {
        new AsyncTask<ZdicEntryIndex, Void, Pair <String, ZdicEntry.EntryType>> () {
            @Override
            protected Pair <String, ZdicEntry.EntryType>
                    doInBackground(ZdicEntryIndex... entryIndices) {
                return new Pair<>(entryIndices[0].getHtml(), entryIndices[0].getEntryType());
            }

            @Override
            protected void onPostExecute(Pair <String, ZdicEntry.EntryType> pair) {
                super.onPostExecute(pair);
                String html = pair.first;
                ZdicEntry.EntryType entryType = pair.second;
                Intent intent = new Intent(MainActivity.this, DisplayEntryActivity.class);
                intent.putExtra(HTML_ARG, html);
                intent.putExtra(ENTRY_TYPE_ARG, entryType.toString());
                startActivity(intent);
            }
        }.execute(entryIndex);
    }

    private final class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // new ZdicParser(MainActivity.this, mTextView.getText().toString());

            // if (mExplanationFragmentPagerAdapter == null) {
            //     mExplanationFragmentPagerAdapter = new ZdicTabPagesAdapter(
            //             getSupportFragmentManager(), mTextView.getText().toString()
            //     );
            //     mViewPager = (ViewPager) findViewById(R.id.fragment_pager);
            //     mViewPager.setAdapter(mExplanationFragmentPagerAdapter);
            // } else
            //     mExplanationFragmentPagerAdapter.setContent(mTextView.getText().toString());

            // mZdicTabPageFragment = ZdicTabPageFragment
            //         .newInstance(mTextView.getText().toString());
            // FragmentTransaction fragmentTransaction = getFragmentManager()
            //         .beginTransaction();
            // fragmentTransaction.replace(R.id.explanation_fragment_viewGroup, mZdicTabPageFragment);
            // fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            // fragmentTransaction.commit();
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
