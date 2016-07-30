package com.whjpji.zdic.search.frontend;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.whjpji.zdic.display.backend.entry.ZdicEntry;
import com.whjpji.zdic.search.backend.index.ZdicEntryIndex;
import com.whjpji.zdic.search.backend.ZdicParser;

import java.util.ArrayList;

/**
 * Created by whjpji on 16-7-29.
 */
public class ZdicIndicesAdpater extends BaseAdapter
        implements ZdicParser.OnEntryIndexListener {
    // ArrayList <ZdicEntryIndex> mEntryIndices = new ArrayList<>();
    ArrayList <Button> mButtons = new ArrayList<>();
    OnEntryIndexButtonClickedListener mListener;
    Context mContext;

    public ZdicIndicesAdpater(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        // if (view != null)
        //     return view;
        // else
            return mButtons.get(index);
    }

    @Override
    public void onEntryIndexPush(ZdicEntryIndex entryIndex) {
        // mEntryIndices.add(entryIndex);
        addButton(entryIndex);
        notifyDataSetChanged();
    }

    @Override
    public void onEntryIndexPush(ArrayList<ZdicEntryIndex> entryIndices) {
        // mEntryIndices.addAll(entryIndices);
        for (final ZdicEntryIndex entryIndex : entryIndices) {
            addButton(entryIndex);
        }
        notifyDataSetChanged();
    }

    private void addButton(final ZdicEntryIndex entryIndex) {
        Button button = new Button(mContext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEntryButtonClicked(entryIndex);
            }
        });
        button.setText(entryIndex.getIndexString());
        button.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        mButtons.add(button);
    }

    @Override
    public void onEntryIndexClear() {
        // mEntryIndices.clear();
        mButtons.clear();
        notifyDataSetChanged();
    }

    public void setOnEntryIndexButtonClickedListener
            (OnEntryIndexButtonClickedListener listener) {
        mListener = listener;
    }

    public void onEntryButtonClicked(ZdicEntryIndex entryIndex) {
        mListener.onEntryButtonClicked(entryIndex);
    }

    public interface OnEntryIndexButtonClickedListener {
        void onEntryButtonClicked(ZdicEntryIndex entryIndex);
    }
}
