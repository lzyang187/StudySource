package com.lzy.studysource.viewpager2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;

import java.util.List;

/**
 * @author: cyli8
 * @date: 2019-10-11 16:43
 */
public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2ViewHolder> {
    private Context mContext;
    private List<String> mList;

    public ViewPager2Adapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewPager2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = View.inflate(parent.getContext(), R.layout.item_rv, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        item.setLayoutParams(lp);
        return new ViewPager2ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPager2ViewHolder holder, int position) {
        holder.bindViewHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
