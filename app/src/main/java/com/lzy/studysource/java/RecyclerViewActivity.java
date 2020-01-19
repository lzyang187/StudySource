package com.lzy.studysource.java;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Button btn = findViewById(R.id.btn);
        final RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        rv.setLayoutManager(layoutManager);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("str：" + i);
        }
        final MyAdapter adapter = new MyAdapter(this, list);
        rv.setAdapter(adapter);

        btn.setOnClickListener(v -> {
            //adapter.notifyItemInserted()的使用
//                list.add(1, "add 1");
//                adapter.notifyItemInserted(1);
//                adapter.notifyItemRangeChanged(1, list.size() - 1);

            //adapter.notifyItemRangeInserted()的使用
//                list.add(0, "add 0");
//                list.add(1, "add 1");
//                list.add(2, "add 2");
//                adapter.notifyItemRangeInserted(0, 3);
//                adapter.notifyItemRangeChanged(3, list.size() - 3);
//                rv.scrollToPosition(0);

            //adapter.notifyItemRemoved()的使用
//                list.remove(1);
//                adapter.notifyItemRemoved(1);
//                adapter.notifyItemRangeChanged(1, list.size() - 1);

            //adapter.notifyItemRangeRemoved()的使用
//                list.remove(1);
//                list.remove(1);
//                list.remove(1);
//                adapter.notifyItemRangeRemoved(1, 3);
//                adapter.notifyItemRangeChanged(1, list.size() - 1);

            //adapter.notifyItemMoved()的使用
            Collections.swap(list, 1, 3);
            adapter.notifyItemMoved(1, 3);
            adapter.notifyItemRangeChanged(1, 3);
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
        holder.bindViewHolder(position, mList.get(position));
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

        public void bindViewHolder(int pos, String str) {
            mTv.setText(str);
            mTv.setOnClickListener(v -> Toast.makeText(mContext, "pos=" + pos + " str=" + str, Toast.LENGTH_SHORT).show());
        }
    }
}

