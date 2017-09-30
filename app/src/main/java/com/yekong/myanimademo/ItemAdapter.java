package com.yekong.myanimademo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xigua on 2017/9/29.
 */

public class ItemAdapter extends BaseAdapter<String> {

    public ItemAdapter(Context context) {
        super(context);
    }

    @Override
    int getHeaderView() {
        return R.layout.text_item;
    }


    @Override
    void onItemClick(String s, int position) {

    }

    @Override
    void itemExPland(int position) {

    }

    @Override
    void itemClose(int position) {

    }

    @Override
    RecyclerView.ViewHolder getChildHolder() {
        return new ChildViewHolder(View.inflate(context,R.layout.inside_item,null));
    }

    @Override
    void bindChildLayout(int groupId, int childId, RecyclerView.ViewHolder groupHolder, RecyclerView.ViewHolder childHolder) {
        ChildViewHolder holder = (ChildViewHolder) childHolder;
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    List getChildList(int position) {
        List<String> list = new ArrayList<>();
        Random r = new Random();
        int y = r.nextInt(10);
        for (int i = 0; i < y; i++) {
            list.add(i + "。。。。。。。");
        }
        return list;
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        public ChildViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item);
        }
    }
}
