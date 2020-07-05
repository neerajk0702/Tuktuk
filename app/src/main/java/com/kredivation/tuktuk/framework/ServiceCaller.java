package com.kredivation.tuktuk.framework;

import android.content.Context;
import android.util.Log;


import com.kredivation.tuktuk.SimpleClasses.Variables;

import org.json.JSONObject;

/**
 * Created by Neeraj on 7/25/2017.
 */
public class ServiceCaller {
    Context context;

    public ServiceCaller(Context context) {
        this.context = context;
    }

    //call Commen Method ForCall Servier Data
    public void CallCommanServiceMethod(final String loginUrl, final String methodNmae, final IAsyncWorkCompletedCallback workCompletedCallback) {
        new ServiceHelper().callService(loginUrl, null, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                    Log.d(Variables.tag, methodNmae + "********" + result);
                } else {
                    workCompletedCallback.onDone(methodNmae, false);
                }
            }
        });
    }

    //call Commen Method ForCall Servier Data with json Object
    public void CallCommanServiceMethod(final String loginUrl, JSONObject jsonObject, final String methodNmae, final IAsyncWorkCompletedCallback workCompletedCallback) {
        Log.d(Variables.tag, methodNmae + "Payload********" + jsonObject.toString());
        new ServiceHelper().callService(loginUrl, jsonObject, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                    Log.d(Variables.tag, methodNmae + "********" + result);
                } else {
                    workCompletedCallback.onDone(methodNmae, false);
                }
            }
        });
    }



//    private void signUp() {
//        if (Utility.isOnline(SignUpActivity.this)) {
//            final ASTProgressBar dotDialog = new ASTProgressBar(SignUpActivity.this);
//            dotDialog.show();
//            JSONObject object = new JSONObject();
//            try {
//
//                object.put("api_key", Contants.API_KEY);
//                object.put("first_name", firstNameStr);
//                object.put("last_name", lastNameStr);
//                object.put("username", userStr);
//                object.put("email", emailStr);
//                object.put("password", passwordStr);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            String serviceURL = Contants.BASE_URL + Contants.Registration;
//
//            ServiceCaller serviceCaller = new ServiceCaller(SignUpActivity.this);
//            serviceCaller.CallCommanServiceMethod(serviceURL, object, "SignUp", new IAsyncWorkCompletedCallback() {
//                @Override
//                public void onDone(String result, boolean isComplete) {
//                    if (isComplete) {
//                        final ServiceContentData serviceData = new Gson().fromJson(result, ServiceContentData.class);
//                        if (serviceData != null) {
//                            if (serviceData.isSuccess()) {
//                                Utility.showToast(SignUpActivity.this, serviceData.getMsg());
//                                finish();
//                            } else {
//                                Utility.showToast(SignUpActivity.this, serviceData.getMsg());
//                            }
//                        } else {
//                            Utility.showToast(SignUpActivity.this, Contants.Error);
//                        }
//                    } else {
//                        Utility.alertForErrorMessage(Contants.Error, SignUpActivity.this);
//                    }
//                    if (dotDialog.isShowing()) {
//                        dotDialog.dismiss();
//                    }
//                }
//            });
//        } else {
//            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, SignUpActivity.this);//off line msg....
//        }
//    }

}
