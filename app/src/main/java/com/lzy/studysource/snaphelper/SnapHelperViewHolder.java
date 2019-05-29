package com.lzy.studysource.snaphelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lzy.studysource.R;

/**
 * @author: cyli8
 * @date: 2019/4/4 10:47
 */
public class SnapHelperViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public SnapHelperViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tv);
    }

    public void bindViewHolder(String name) {
        textView.setText(name);
    }
}
