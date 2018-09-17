package com.lzy.studysource.java;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.studysource.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Button btn = findViewById(R.id.btn);
        final RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("str：" + i);
        }
        final MyAdapter adapter = new MyAdapter(this, list);
        rv.setAdapter(adapter);

//        adapter.notifyItemInserted();
//        adapter.notifyItemRangeInserted();
//
//        adapter.notifyItemRemoved();
//        adapter.notifyItemRangeRemoved();
//
//        adapter.notifyItemRangeChanged();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                list.add(0, "add 0");
//                list.add(0, "add 1");
//                list.add(0, "add 2");
//                adapter.notifyItemRangeInserted(0, 3);
//                rv.scrollToPosition(0);

//                list.remove(list.size() - 1);
//                adapter.notifyItemRemoved(list.size());

            }
        });
    }


}

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
        holder.bindViewHolder(mList.get(position));
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

        public void bindViewHolder(String str) {
            mTv.setText(str);
        }
    }
}

