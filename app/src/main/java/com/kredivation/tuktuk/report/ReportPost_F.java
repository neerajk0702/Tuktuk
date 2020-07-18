package com.kredivation.tuktuk.report;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kredivation.tuktuk.Comments.Comment_Get_Set;
import com.kredivation.tuktuk.Comments.Comments_Adapter;
import com.kredivation.tuktuk.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.kredivation.tuktuk.R;
import com.kredivation.tuktuk.SimpleClasses.Fragment_Data_Send;
import com.kredivation.tuktuk.SimpleClasses.Functions;
import com.kredivation.tuktuk.SimpleClasses.Variables;
import com.kredivation.tuktuk.Utility;
import com.kredivation.tuktuk.framework.IAsyncWorkCompletedCallback;
import com.kredivation.tuktuk.framework.ServiceCaller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportPost_F extends RootFragment {

    View view;
    Context context;

    RecyclerView recyclerView;

    ReportAdapter adapter;

    ArrayList<ReportData> data_list;

    String video_id;
    String user_id;


    FrameLayout comment_screen;

    public ReportPost_F() {

    }

    Fragment_Data_Send fragment_data_send;

    @SuppressLint("ValidFragment")
    public ReportPost_F(Fragment_Data_Send fragment_data_send) {
        this.fragment_data_send = fragment_data_send;
    }
int reportTypeID=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reportpost, container, false);
        context = getContext();


        comment_screen = view.findViewById(R.id.comment_screen);
        comment_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });

        view.findViewById(R.id.Goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);


        data_list = new ArrayList<ReportData>();
        ReportData data= new ReportData();
        data.setId(1);
        data.setReport("Self Injury");
        data_list.add(data);
        ReportData data2= new ReportData();
        data2.setId(2);
        data2.setReport("Adult / Abuse");
        data_list.add(data2);
        ReportData data3= new ReportData();
        data3.setId(3);
        data3.setReport("Fake News");
        data_list.add(data3);
        ReportData data4= new ReportData();
        data4.setId(4);
        data4.setReport("Spam");
        data_list.add(data4);
        ReportData data5= new ReportData();
        data5.setId(5);
        data5.setReport("Violence or Harm");
        data_list.add(data5);
        ReportData data6= new ReportData();
        data6.setId(6);
        data6.setReport("Hat Speech");
        data_list.add(data6);
        ReportData data7= new ReportData();
        data7.setId(7);
        data7.setReport("Terrorism");
        data_list.add(data7);
        ReportData data8= new ReportData();
        data8.setId(8);
        data8.setReport("Promotion of Drugs or Weapons");
        data_list.add(data8);
        ReportData data9= new ReportData();
        data9.setId(9);
        data9.setReport("Other");
        data_list.add(data9);
        adapter = new ReportAdapter(context, data_list, new ReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion, ReportData item, View view) {
                reportTypeID=data_list.get(postion).getId();
                if(data_list.get(postion).isSelect()){
                    data_list.get(postion).setSelect(false);
                }else {
                    data_list.get(postion).setSelect(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportTypeID>0) {
                    reportPost(reportTypeID);
                }else{
                    Toast.makeText(context, "Please select anyone option!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    @Override
    public void onDetach() {
        Functions.hideSoftKeyboard(getActivity());

        super.onDetach();
    }

    public void reportPost(int reportTypeId) {
        if (Utility.isOnline(getActivity())) {
            JSONObject parameters = new JSONObject();
            try {
                parameters.put("video_id", video_id);
                parameters.put("fb_id", Variables.sharedPreferences.getString(Variables.u_id, "0"));
                parameters.put("reportTypeId", reportTypeId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(getActivity());
            serviceCaller.CallCommanServiceMethod(Variables.reportPost, parameters, "reportPost", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    Toast.makeText(context, "Reported successfully", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            });
        } else {
            Toast.makeText(context, Variables.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

}

