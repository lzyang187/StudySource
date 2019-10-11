package com.lzy.studysource.viewpager2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;

/**
 * @author: cyli8
 * @date: 2019-10-11 16:43
 */
public class ViewPager2ViewHolder extends RecyclerView.ViewHolder {

    private TextView mTv;

    public ViewPager2ViewHolder(View itemView) {
        super(itemView);
        mTv = itemView.findViewById(R.id.tv);
    }

    public void bindViewHolder(String str) {
        mTv.setText(str);
    }
}
