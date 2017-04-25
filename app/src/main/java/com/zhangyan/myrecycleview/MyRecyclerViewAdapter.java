package com.zhangyan.myrecycleview;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/21  9:18
 * @desc ${TODD}
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mArrayList;

    public MyRecyclerViewAdapter(ArrayList<String> arrayList) {
        this.mArrayList = arrayList;
    }

    //ListView  getView方法  有两段逻辑

    //第一段 viewHolder
    //判断缓存view是否为null
        //为null   填充一个view出来,创建一个viewHolder 通过setTag的形式把holder绑定给缓存view 找控件
//    holder.textView = view.findView
        //不为null 从缓存view,通过getTag获取viewHolder,从holder当中直接取控件


    //第二段
    //给viewHolder中的控件设置数据

    //return

    //对应getView的第一段逻辑,准备一个ViewHolder出来
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建一个ViewHolder
        //填充一个view出来,将这个view作为itemView,将其跟holder绑定
//        View.inflate()
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    //声明一个接口
    private OnItemClickListener mOnItemClickListener = null;

    //提供一个方法去赋值
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    //添加一个item
    public void addItem(String str,int position){
        mArrayList.add(position,str);
        //notify    缺点:没有动画效果的
//        notifyDataSetChanged();
        notifyItemInserted(position);
    }

    public void deleteItem(int position){
        mArrayList.remove(position);
//        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    //对应getView的第二段逻辑, 给viewHolder中的控件设置数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        holder.textView.setText
        ((MyViewHolder)holder).mTextView.setText(mArrayList.get(position));
        //给holder中的控件设置一个点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件直接不来做,通过接口的形式暴露给外部去实现
//                listener.onItemClick();
                //为了健壮性
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v,position);
                }
            }
        });
    }

    //getCount  指定控件有多少个item条目
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        //构造方法需要传一个view
        public MyViewHolder(View itemView) {
            super(itemView);
            //findViewById 赋值
            mTextView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(View itemView, int positon);
    }
}

