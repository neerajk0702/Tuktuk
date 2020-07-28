package com.kredivation.tuktuk.catlsit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kredivation.tuktuk.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.kredivation.tuktuk.R;
import com.kredivation.tuktuk.SimpleClasses.Fragment_Data_Send;
import com.kredivation.tuktuk.SimpleClasses.Functions;

import java.util.ArrayList;

public class Category_F extends RootFragment {

    View view;
    Context context;
    RecyclerView recyclerView;
    Category_Adapter adapter;

    ArrayList<CateData> data_list;

    String video_id;
    String user_id;


    public Category_F() {

    }

    Fragment_Data_Send fragment_data_send;

    @SuppressLint("ValidFragment")
    public Category_F(Fragment_Data_Send fragment_data_send) {
        this.fragment_data_send = fragment_data_send;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        context = getContext();


        view.findViewById(R.id.Goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            video_id = bundle.getString("video_id");
            user_id = bundle.getString("user_id");
        }

        recyclerView = view.findViewById(R.id.recylerview);
        final GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        data_list = new ArrayList<CateData>();
        CateData data = new CateData();
        data.setId(1);
        data.setCatName("Funny Video");
        data_list.add(data);
        CateData data2 = new CateData();
        data2.setId(2);
        data2.setCatName("Love Songs");
        data_list.add(data2);
        CateData data3 = new CateData();
        data3.setId(3);
        data3.setCatName("Reginal Songs");
        data_list.add(data3);
        CateData data4 = new CateData();
        data4.setId(4);
        data4.setCatName("Like Songs");
        data_list.add(data4);
        CateData data5 = new CateData();
        data5.setId(5);
        data5.setCatName("Dance Video");
        data_list.add(data5);
        CateData data6 = new CateData();
        data6.setId(6);
        data6.setCatName("Sad Songs");
        data_list.add(data6);
        CateData data7 = new CateData();
        data7.setId(7);
        data7.setCatName("Des Bhakti Video");
        data_list.add(data7);



        CateData data8 = new CateData();
        data8.setId(8);
        data8.setCatName("Des Bhakti Video");
        data_list.add(data8);


        CateData data9 = new CateData();
        data9.setId(9);
        data9.setCatName("Des Bhakti Video");
        data_list.add(data9);


        CateData data10 = new CateData();
        data10.setId(10);
        data10.setCatName("Sad Video");
        data_list.add(data10);

        CateData data11 = new CateData();
        data11.setId(11);
        data11.setCatName("Love  Video");
        data_list.add(data11);

        CateData data12 = new CateData();
        data12.setId(12);
        data12.setCatName("Bhakti Video");
        data_list.add(data12);

     /*   adapter = new ReportAdapter(context, data_list, new ReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion, CateData item, View view) {
                reportTypeID = data_list.get(postion).getId();
                ImageView report = view.findViewById(R.id.report);
                if (data_list.get(postion).isSelect()) {
                    data_list.get(postion).setSelect(false);
                    report.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_donenew));

                } else {
                    data_list.get(postion).setSelect(true);
                    report.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_uncheckbox));
                }
                adapter.updateData(data_list);
            }
        });*/

        adapter = new Category_Adapter(getContext(), data_list);
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
        return view;
    }


    @Override
    public void onDetach() {
        Functions.hideSoftKeyboard(getActivity());
        super.onDetach();
    }


}

