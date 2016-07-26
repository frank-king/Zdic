package com.whjpji.zdic;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity
        extends AppCompatActivity
        implements ExplanationFragment.OnSearchButtonClickedListener {
    private TextView mTextView;
    private ExplanationFragment mExplanationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.editText);

        // 按下「搜索」按鈕後，送出查詢內容，顯示查詢結果
        Button button = (Button) findViewById(R.id.btn_search);
        button.setOnClickListener(new ButtonOnClickListener());
    }

    @Override
    public void onSearchButtonClicked(Uri uri) {

    }

    private final class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mExplanationFragment = ExplanationFragment
                    .newInstance(mTextView.getText().toString());
            FragmentTransaction fragmentTransaction = getFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.explanation_fragment_viewGroup, mExplanationFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
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
