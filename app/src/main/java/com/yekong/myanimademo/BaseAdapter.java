package com.yekong.myanimademo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by xigua on 2017/9/29.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    public Context context;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = View.inflate(context,R.layout.item ,null);
        ((FrameLayout)root.findViewById(R.id.hearderview)).addView(View.inflate(context,getHeaderView() ,null));
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder h, final int position) {
            final ViewHolder holder = (ViewHolder) h;
            holder.listview.setLayoutManager(new LinearLayoutManager(context));
            holder.listview.setAdapter(new InsideItemAdapter(context,getChildList(position), new InsideItemAdapter.InsideAdapterAction() {
                @Override
                public RecyclerView.ViewHolder getAdapter() {
                    return getChildHolder();
                }

                @Override
                public void bindHolder(RecyclerView.ViewHolder holder, int p) {
                    bindChildLayout(position,p,h,holder);
                }
            }));//5 本体应为出入的list 子布局layout
            if (holder.getItemHeight() == -1) {
                holder.setItemHeight(unDisplayViewSize(holder.listview)[1]);
            }
            holder.hearderview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
                    anim.setDuration(500);
                    final int height = holder.listview.getHeight();
                    Log.e("TAG", "cuurent height " + height);
                    int m_h = holder.listview.getLayoutParams().height;
                    Log.e("BaseAdapter", "m_h:" + m_h);
                    if (holder.getItemHeight() == -1) {
                        holder.setItemHeight(height);
                    }
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float currentValue = (float) animation.getAnimatedValue();
                            Log.e("TAG", "cuurent value is " + currentValue);
                            ViewGroup.LayoutParams params = holder.listview.getLayoutParams();
                            if (!holder.isExpland) {
                                int y = (int) (holder.getItemHeight() * (currentValue));
                                params.height = y;
                                holder.listview.setLayoutParams(params);
                                holder.listview.setAlpha(currentValue);
                                if (!holder.isExpland) {
                                    holder.listview.setVisibility(View.VISIBLE);
                                }
                                itemExPland(position);
                            } else {
                                int y = (int) (holder.getItemHeight() - (holder.getItemHeight()*currentValue));
                                params.height = y;
                                holder.listview.setLayoutParams(params);
                                holder.listview.setAlpha(1-currentValue);
                                itemClose(position);
                            }
                        }
                    });
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            holder.setExpland(!holder.isExpland());
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    anim.start();
                    onItemClick(null,position);
                }
            });


    }

    public int[] unDisplayViewSize(View view) {
        int size[] = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        Log.e("TAG", "listview_h " + size[1]);
        return size;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView textView;
        private boolean isExpland = false;
        private int itemHeight = -1;
        private RecyclerView listview;
        private FrameLayout hearderview;
        public int getItemHeight() {
            return itemHeight;
        }

        public void setItemHeight(int itemHeight) {
            this.itemHeight = itemHeight;
        }

        public boolean isExpland() {
            return isExpland;
        }

        public void setExpland(boolean expland) {
            isExpland = expland;
        }

        public ViewHolder(View itemView) {
            super(itemView);
//            textView = itemView.findViewById(R.id.item);
            hearderview = itemView.findViewById(R.id.hearderview);
            listview = itemView.findViewById(R.id.listview);
        }
    }

    abstract int getHeaderView ();
    abstract void onItemClick(T t,int position);
    abstract void itemExPland(int position);
    abstract void itemClose(int position);
    abstract RecyclerView.ViewHolder getChildHolder();
    abstract void bindChildLayout(int groupId,int childId,RecyclerView.ViewHolder groupHolder ,RecyclerView.ViewHolder childHolder);
    abstract List getChildList(int position);
}
