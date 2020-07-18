package com.kredivation.tuktuk.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kredivation.tuktuk.Comments.Comment_Get_Set;
import com.kredivation.tuktuk.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.CustomViewHolder> {

    public Context context;
    private ReportAdapter.OnItemClickListener listener;
    private ArrayList<ReportData> dataList;


    // meker the onitemclick listener interface and this interface is impliment in Chatinbox activity
    // for to do action when user click on item
    public interface OnItemClickListener {
        void onItemClick(int positon, ReportData item, View view);
    }

    public ReportAdapter(Context context, ArrayList<ReportData> dataList, ReportAdapter.OnItemClickListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;

    }

    @Override
    public ReportAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_report, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        ReportAdapter.CustomViewHolder viewHolder = new ReportAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    @Override
    public void onBindViewHolder(final ReportAdapter.CustomViewHolder holder, final int i) {
        holder.reporttext.setText(dataList.get(i).getReport());
        holder.bind(i, dataList.get(i), listener);
        if(dataList.get(i).isSelect()){
            holder.report.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_donenew));
        }else {
            holder.report.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_uncheckbox));
        }

    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView report;
        TextView reporttext;


        public CustomViewHolder(View view) {
            super(view);

            report = view.findViewById(R.id.report);
            reporttext=view.findViewById(R.id.reporttext);

        }

        public void bind(final int postion, final ReportData item, final ReportAdapter.OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(postion, item, v);
                }
            });

        }


    }

}
