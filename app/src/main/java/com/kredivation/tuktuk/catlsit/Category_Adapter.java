package com.kredivation.tuktuk.catlsit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.kredivation.tuktuk.R;

import java.util.ArrayList;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.CustomViewHolder> {
    public Context context;

    ArrayList<CateData> datalist;
    ArrayList<CateData> datalist_filter;



    public Category_Adapter(Context context, ArrayList<CateData> arrayList) {
        this.context = context;
        datalist = arrayList;
        datalist_filter = arrayList;
    }


    @Override
    public Category_Adapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_ilist_tem, viewGroup, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        Category_Adapter.CustomViewHolder viewHolder = new Category_Adapter.CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return datalist_filter.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        RecyclerView horizontal_reycerview;

        TextView title;

        public CustomViewHolder(View view) {
            super(view);

            horizontal_reycerview = view.findViewById(R.id.horizontal_recylerview);
            title = view.findViewById(R.id.title);
        }


    }


    @Override
    public void onBindViewHolder(final Category_Adapter.CustomViewHolder holder, final int i) {

        CateData item = datalist_filter.get(i);
        holder.title.setText(item.getCatName());


    }


}