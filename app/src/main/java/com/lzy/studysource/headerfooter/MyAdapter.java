package com.lzy.studysource.headerfooter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mList;

    public MyAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_rv, null));
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
            mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("cyli8", "onClick: " + pos);
                }
            });
        }
    }

}

