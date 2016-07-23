package com.whjpji.zdic;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("MainActivity onCreate.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 按下「搜索」按鈕後，送出查詢內容，顯示查詢結果
        Button button = (Button) findViewById(R.id.btn_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) findViewById(R.id.editText);
                ScrollView scroller = (ScrollView) findViewById(R.id.scrollView);
                WebView webView = new WebView(MainActivity.this);
                // webView.loadUrl("http://m.zdic.net/");
                webView.loadData("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></meta></head><body>" +
                        "<div class=\"tab-page\"><p class=\"zdct3\"><strong>基本字义</strong></p>" +
                        "<hr class=\"dichr\">" +
                        "<p class=\"zdct3\">● <strong>人</strong></p>" +
                        "<p class=\"zdct3\"><span class=\"dicpy\">rén <script>spz(\"ren2\");</script><object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\" height=\"20\" width=\"20\"><param name=\"wmode\" value=\"transparent\"><param name=\"movie\" value=\"/images/spft.swf\"><param name=\"quality\" value=\"high\"><param name=\"flashVars\" value=\"lj=ren2\"><embed flashvars=\"lj=ren2\" src=\"/images/spft.swf\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" height=\"20\" width=\"20\"></object> ㄖㄣˊ</span></p>" +
                        "<p class=\"zdct3\">　1. 由类人猿进化而成的能制造和使用工具进行劳动、并能运用语言进行交际的动物：～类。</p>" +
                        "<p class=\"zdct3\">　2. 别人，他人：“～为刀俎，我为鱼肉”。待～热诚。</p>" +
                        "<p class=\"zdct3\">　3. 人的品质、性情、名誉：丢～，文如其～。</p>" +
                        "<p class=\"zdct4\">【漢典‍】</p><p class=\"zdct3\"><strong>汉英互译</strong></p>" +
                        "<hr class=\"dichr\">" +
                        "<p class=\"zdct3\">◎ <strong>人</strong></p>" +
                        "<p class=\"zdct3\"><span class=\"diczx3\">human</span>　　　<span class=\"diczx3\">man</span>　　　<span class=\"diczx3\">people</span>　　　<span class=\"diczx3\">person</span>　　　<span class=\"diczx3\">human being</span>　　　<span class=\"diczx3\">fellow</span>　　　<span class=\"diczx3\">individual</span>　　　<span class=\"diczx3\">soul</span></p>" +
                        "<p class=\"zdct3\"><strong>相关词语</strong></p>" +
                        "<hr class=\"dichr\">" +
                        "<p class=\"zdct3\">◎ <strong>人</strong></p>" +
                        "<p class=\"zdct3\"><span class=\"diczx3\">己</span>　　　<span class=\"diczx3\">我</span></p>" +
                        "<p class=\"zdct3\"></p><h3>English</h3><p></p><hr class=\"dichr\">man; people; mankind; someone else<p></p><p class=\"zdct4\">【漢典‍】</p>" +
                        "<div class=\"hb\"></div></div></body></html>", "text/html; charset=utf-8", null);

                // webView.loadData("人", "text/html", "utf-8");

                scroller.addView(webView);
            }
        });
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
