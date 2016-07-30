package com.whjpji.zdic.search.frontend;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.whjpji.zdic.R;
import com.whjpji.zdic.search.backend.ZdicParser;
import com.whjpji.zdic.search.backend.index.ZdicEntryIndex;

import java.util.ArrayList;

/**
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment implements ZdicParser.OnEntryIndexListener {
    private static final String INDEX_ARG = "INDEX";

    private String mIndexString;

    private ZdicIndicesAdpater mAdapter;
    private ZdicIndicesAdpater.OnEntryIndexButtonClickedListener mButtonListner;

    private ZdicParser mParser;

    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResultFragment.
     */
    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        // args.putString(INDEX_ARG, indexString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // setIndexString(getArguments().getString(INDEX_ARG));
        }
        mAdapter = new ZdicIndicesAdpater(getContext());
        mAdapter.setOnEntryIndexButtonClickedListener(mButtonListner);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout = (FrameLayout)
            inflater.inflate(R.layout.fragment_search_results, container, false);

        GridView gridView = (GridView) frameLayout.findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);
        // Button button = new Button(getActivity());
        // button.setText(mIndexString);
        // button.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View view) {
        //         Intent intent = new Intent(getActivity(), DisplayEntryActivity.class);
        //         intent.putExtra(INDEX_ARG, mIndexString);
        //         getActivity().startActivity(intent);
        //     }
        // });
        // gridView.addView(button);
        return frameLayout;
    }

    public void setOnEntryIndexButtonClickedListener
            (ZdicIndicesAdpater.OnEntryIndexButtonClickedListener listener) {
        mButtonListner = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnEntryIndexButtonClickedListener) {
            mListener = (OnEntryIndexButtonClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEntryIndexButtonClickedListener");
        }
        */
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onEntryIndexPush(ZdicEntryIndex entryIndex) {
        if (mAdapter != null)
            mAdapter.onEntryIndexPush(entryIndex);
    }

    @Override
    public void onEntryIndexPush(ArrayList<ZdicEntryIndex> entryIndices) {
        if (mAdapter != null)
            mAdapter.onEntryIndexPush(entryIndices);
    }

    @Override
    public void onEntryIndexClear() {
        if (mAdapter != null)
            mAdapter.onEntryIndexClear();
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
    // public interface OnEntryIndexButtonClickedListener {
    //     // TODO: Update argument type and name
    //     void onEntryIndexButtonClicked(Uri uri);
    // }
}
