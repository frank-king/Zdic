package com.whjpji.zdic.display.frontend;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;
import com.whjpji.zdic.display.backend.tabpage.ZdicTabPage;
import com.whjpji.zdic.search.backend.ZdicParser;

import java.util.ArrayList;

/**
 * Created by whjpji on 16-7-27.
 */
public class ZdicTabPagesAdapter
        extends FragmentStatePagerAdapter
        implements ZdicEntry.OnTabPageChangedListener {
    // private static final ZdicParser.Dictionary [] DICTIONARIES = {
    //         ZdicParser.Dictionary.HAN_DIAN,
    //         ZdicParser.Dictionary.HAN_DIAN_DETAIL,
    //         ZdicParser.Dictionary.KANG_XI,
    //         ZdicParser.Dictionary.SHUO_WEN,
    //         ZdicParser.Dictionary.YIN_YUN,
    //         // ZdicParser.Dictionary.ZI_YUAN,
    //         // ZdicParser.Dictionary.DISCUSS,
    // };
    // private static final int NUM_PAGES = DICTIONARIES.length;
    // private ZdicTabPageFragment[] mZdicTabPageFragments;
    private ArrayList <ZdicTabPageFragment> mTabPageFragments = new ArrayList <>();
    private ArrayList <ZdicTabPage> mTabPages = new ArrayList <>();
    // private ZdicEntry mEntry;
    // private String mContent;

    public ZdicTabPagesAdapter(FragmentManager fm) {
        super(fm);
    }

    // public void setContent(String content) {
    //     this.mContent = content;
    //     for (ZdicTabPageFragment fragment : mZdicTabPageFragments) {
    //         if (fragment != null)
    //             fragment.setContent(content);
    //     }
    // }

    @Override
    public Fragment getItem(int position) {
        return mTabPageFragments.get(position);
        // return mEntry.getTabPageOfIndex(position).getFragment();
        // if (mZdicTabPageFragments[position] == null) {
        //     mZdicTabPageFragments[position] =
        //             ZdicTabPageFragment.newInstance(mContent, DICTIONARIES[position]);
        // }
        // return mZdicTabPageFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // return TITLES[position];
        // System.out.println(mTabPageFragments.get(position).getTitle());
        return mTabPages.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mTabPageFragments.size();
        // return mEntry.getTabPagesCount();
    }


    @Override
    public void onTabPagePush(ZdicTabPage tabPage) {
        mTabPageFragments.add(ZdicTabPageFragment.newInstance(tabPage.getParsedHtml()));
        mTabPages.add(tabPage);
        notifyDataSetChanged();
    }

    @Override
    public void onTabPagePush(ArrayList<ZdicTabPage> tabPages) {
        for (ZdicTabPage tabPage : tabPages) {
            onTabPagePush(tabPage);
        }
    }

    @Override
    public void onTabPageClear() {
        mTabPageFragments.clear();
        notifyDataSetChanged();
    }
}
