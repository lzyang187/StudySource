package com.lzy.studysource.snaphelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;

import java.util.List;

/**
 * @author: cyli8
 * @date: 2019/4/4 10:47
 */
public class SnapHelperAdatper extends RecyclerView.Adapter<SnapHelperViewHolder> {
    private List<String> mList;
    private LayoutInflater mInflater;

    public SnapHelperAdatper(Context context, List<String> list) {
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @NonNull
    @Override
    public SnapHelperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SnapHelperViewHolder(mInflater.inflate(R.layout.item_snaphelper, null));
    }

    @Override
    public void onBindViewHolder(@NonNull SnapHelperViewHolder holder, int position) {
        Log.i("cyli8", "onBindViewHolder pos=" + position);
        holder.bindViewHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
