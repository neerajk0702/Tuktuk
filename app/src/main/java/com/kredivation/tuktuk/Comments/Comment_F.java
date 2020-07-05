package com.kredivation.tuktuk.Comments;


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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kredivation.tuktuk.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.kredivation.tuktuk.SimpleClasses.API_CallBack;
import com.kredivation.tuktuk.SimpleClasses.ApiRequest;
import com.kredivation.tuktuk.SimpleClasses.Fragment_Data_Send;
import com.kredivation.tuktuk.SimpleClasses.Functions;
import com.kredivation.tuktuk.SimpleClasses.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.kredivation.tuktuk.R;
import com.kredivation.tuktuk.Utility;
import com.kredivation.tuktuk.framework.IAsyncWorkCompletedCallback;
import com.kredivation.tuktuk.framework.ServiceCaller;

/**
 * A simple {@link Fragment} subclass.
 */
public class Comment_F extends RootFragment {

    View view;
    Context context;

    RecyclerView recyclerView;

    Comments_Adapter adapter;

    ArrayList<Comment_Get_Set> data_list;

    String video_id;
    String user_id;

    EditText message_edit;
    ImageButton send_btn;
    ProgressBar send_progress;

    TextView comment_count_txt;

    FrameLayout comment_screen;

    public static int comment_count=0;
    public Comment_F() {

    }

    Fragment_Data_Send fragment_data_send;
    @SuppressLint("ValidFragment")
    public Comment_F(int count, Fragment_Data_Send fragment_data_send){
        comment_count=count;
        this.fragment_data_send=fragment_data_send;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_comment, container, false);
        context=getContext();


        comment_screen=view.findViewById(R.id.comment_screen);
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


        Bundle bundle=getArguments();
        if(bundle!=null){
            video_id=bundle.getString("video_id");
            user_id=bundle.getString("user_id");
        }


        comment_count_txt=view.findViewById(R.id.comment_count);

        recyclerView=view.findViewById(R.id.recylerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);


        data_list=new ArrayList<>();
        adapter=new Comments_Adapter(context, data_list, new Comments_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion, Comment_Get_Set item, View view) {


            }
        });

        recyclerView.setAdapter(adapter);


        message_edit=view.findViewById(R.id.message_edit);


        send_progress=view.findViewById(R.id.send_progress);
        send_btn=view.findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message=message_edit.getText().toString();
                if(!TextUtils.isEmpty(message)){
                    if(Variables.sharedPreferences.getBoolean(Variables.islogin,false)){
                    Send_Comments(video_id,message);
                    message_edit.setText(null);
                    send_progress.setVisibility(View.VISIBLE);
                    send_btn.setVisibility(View.GONE);
                    }
                    else {
                        Toast.makeText(context, "Please Login into the app", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });




        Get_All_Comments();


        return view;
    }


    @Override
    public void onDetach() {
        Functions.hideSoftKeyboard(getActivity());

        super.onDetach();
    }

    // this funtion will get all the comments against post
    public void Get_All_Comments(){
        if (Utility.isOnline(getActivity())) {
            JSONObject parameters = new JSONObject();
            try {
                parameters.put("video_id", video_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(getActivity());
            serviceCaller.CallCommanServiceMethod(Variables.showVideoComments, parameters, "Get_All_Comments", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ArrayList<Comment_Get_Set> arrayList = new ArrayList<>();
                        try {
                            JSONObject response = new JSONObject(result);
                            String code = response.optString("code");
                            if (code.equals("200")) {
                                JSONArray msgArray = response.getJSONArray("msg");
                                for (int i = 0; i < msgArray.length(); i++) {
                                    JSONObject itemdata = msgArray.optJSONObject(i);
                                    Comment_Get_Set item = new Comment_Get_Set();
                                    item.fb_id = itemdata.optString("fb_id");

                                    JSONObject user_info = itemdata.optJSONObject("user_info");
                                    item.first_name = user_info.optString("first_name");
                                    item.last_name = user_info.optString("last_name");
                                    item.profile_pic = user_info.optString("profile_pic");


                                    item.video_id = itemdata.optString("id");
                                    item.comments = itemdata.optString("comments");
                                    item.created = itemdata.optString("created");
                                    arrayList.add(item);
                                }
                                for (Comment_Get_Set item : arrayList) {
                                    data_list.add(item);
                                }
                                comment_count_txt.setText(data_list.size() + " comments");
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), "" + response.optString("msg"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Utility.alertForErrorMessage(Contants.Error, MyOrderListActivity.this);
                    }
                }
            });
        } else {
            Toast.makeText(context, Variables.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    // this function will call an api to upload your comment
    public void Send_Comments(String video_id, final String comment){

        if (Utility.isOnline(getActivity())) {
            JSONObject parameters = new JSONObject();
            try {
                parameters.put("fb_id", Variables.sharedPreferences.getString(Variables.u_id,"0"));
                parameters.put("video_id",video_id);
                parameters.put("comment",comment);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(getActivity());
            serviceCaller.CallCommanServiceMethod(Variables.postComment, parameters, "Send_Comments", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ArrayList<Comment_Get_Set> arrayList=new ArrayList<>();
                        try {
                            JSONObject response=new JSONObject(result);
                            String code=response.optString("code");
                            if(code.equals("200")){
                                JSONArray msgArray=response.getJSONArray("msg");
                                for (int i=0;i<msgArray.length();i++) {
                                    JSONObject itemdata = msgArray.optJSONObject(i);
                                    Comment_Get_Set item=new Comment_Get_Set();
                                    item.fb_id=itemdata.optString("fb_id");

                                    JSONObject user_info=itemdata.optJSONObject("user_info");
                                    item.first_name=user_info.optString("first_name");
                                    item.last_name=user_info.optString("last_name");
                                    item.profile_pic=user_info.optString("profile_pic");


                                    item.video_id=itemdata.optString("id");
                                    item.comments=itemdata.optString("comments");
                                    item.created=itemdata.optString("created");


                                    arrayList.add(item);
                                }
                                for(Comment_Get_Set item:arrayList){
                                    data_list.add(0,item);
                                    comment_count++;
                                    SendPushNotification(getActivity(),user_id,comment);
                                    comment_count_txt.setText(comment_count+" comments");
                                    if(fragment_data_send!=null)
                                        fragment_data_send.onDataSent(""+comment_count);

                                }
                                adapter.notifyDataSetChanged();
                                send_progress.setVisibility(View.GONE);
                                send_btn.setVisibility(View.VISIBLE);

                            }else {
                                Toast.makeText(getActivity(), ""+response.optString("msg"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Utility.alertForErrorMessage(Contants.Error, MyOrderListActivity.this);
                    }
                }
            });
        } else {
            Toast.makeText(context, Variables.OFFLINE_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    public  void SendPushNotification(Activity activity,String user_id,String comment){

        JSONObject notimap= new JSONObject();
        try {
            notimap.put("title",Variables.sharedPreferences.getString(Variables.u_name,"")+" Comment on your video");
            notimap.put("message",comment);
            notimap.put("icon",Variables.sharedPreferences.getString(Variables.u_pic,""));
            notimap.put("senderid",Variables.sharedPreferences.getString(Variables.u_id,""));
            notimap.put("receiverid", user_id);
            notimap.put("action_type","comment");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        ApiRequest.Call_Api(context,Variables.sendPushNotificationnew,notimap,null);
        ServiceCaller serviceCaller = new ServiceCaller(getActivity());
        serviceCaller.CallCommanServiceMethod(Variables.sendPushNotificationnew, notimap, "SendPushNotification", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {

                }
            }
        });

    }




}
