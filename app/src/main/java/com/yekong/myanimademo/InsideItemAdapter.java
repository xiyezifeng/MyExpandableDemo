package com.yekong.myanimademo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

/**
 * Created by xigua on 2017/9/29.
 */

public class InsideItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List childSize;
    private InsideAdapterAction action;
    ExpandableListView
    public InsideItemAdapter(Context context,List size, InsideAdapterAction action) {
        this.context = context;
        this.childSize = size;
        this.action = action;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return action.getAdapter();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder  holder, int position) {
        action.bindHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return childSize.size();
    }

    public interface InsideAdapterAction {
        RecyclerView.ViewHolder getAdapter();
        void bindHolder(RecyclerView.ViewHolder  holder, int position);
    }
}
