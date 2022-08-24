package com.lzy.studysource.headerfooter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<String> mList;

    public MyAdapter(Context context, List<String> list) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mList = list;
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private onItemClickListener mListener;

    public void setListener(onItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.item_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindViewHolder(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);
        }

        public void bindViewHolder(String str, int pos) {
            mTv.setText(str);
            mTv.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClick(pos);
                }
            });
        }
    }

}

