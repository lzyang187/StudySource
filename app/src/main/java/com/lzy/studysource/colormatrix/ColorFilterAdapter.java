package com.lzy.studysource.colormatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.studysource.R;

import java.util.List;

/**
 * @author: cyli8
 * @date: 2018/5/30 15:23
 */
public class ColorFilterAdapter extends RecyclerView.Adapter<ColorFilterAdapter.MyViewHolder> {

    private Context mContext;
    private List<ColorFilter> mList;
    private Bitmap mOriginBmp;

    public ColorFilterAdapter(Context context, List<ColorFilter> list) {
        mContext = context;
        mList = list;
        mOriginBmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.test_filter);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_color_filter, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).name);
        holder.imageView.setColorFilter(new ColorMatrixColorFilter(mList.get(position).filter));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            imageView = itemView.findViewById(R.id.iv);
        }
    }
}
