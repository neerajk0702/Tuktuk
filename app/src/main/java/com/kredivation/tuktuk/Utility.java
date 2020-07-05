package com.kredivation.tuktuk;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Neeraj on 7/28/2016.
 */
public class Utility {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    //set languagae
    public static void setLanguageIntoSharedPreferences(Context context, int id, String LanguageCode, int languagepos) {
        SharedPreferences prefs = context.getSharedPreferences("LanguagePreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("languageId", id);
        editor.putString("LanguageCode", LanguageCode);
        editor.putInt("Position", languagepos);
        editor.commit();
        setLangRecreate(context, LanguageCode);
    }

    //get language id
    public static int getLanguageIdFromSharedPreferences(Context context) {
        int LanguageId = 0;
        SharedPreferences prefs = context.getSharedPreferences("LanguagePreferences", Context.MODE_PRIVATE);
        if (prefs != null) {
            LanguageId = prefs.getInt("languageId", 1);
        }
        return LanguageId;
    }
   /* //set languagae
    public static void setLanguageIntoSharedPreferences(Context context, EnumLanguage language) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("EnumLanguage", language.name()).commit();
        setLangRecreate(context, language);
    }*/

    //set language into locale
    public static void setLangRecreate(Context context, String LanguageName) {
        Configuration config = new Configuration(context.getResources().getConfiguration());
        Locale locale = new Locale(LanguageName);
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    //get language position for set default value in spinner
    public static int getLanguagePositionFromSharedPreferences(Context context) {
        int position = 0;
        SharedPreferences prefs = context.getSharedPreferences("LanguagePreferences", Context.MODE_PRIVATE);
        if (prefs != null) {
            position = prefs.getInt("Position", 0);
        }
        return position;
    }

    //get language code hi,en etc
    public static String getLanguageCodeFromSharedPreferences(Context context) {
        String languageCode = null;
        SharedPreferences prefs = context.getSharedPreferences("LanguagePreferences", Context.MODE_PRIVATE);
        if (prefs != null) {
            languageCode = prefs.getString("LanguageCode", "en");
        }
        return languageCode;
    }
  /*  private void setLanguageFromSharedPreferencesOnContext(Context context) {
        //getting language from SharedPreferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Configuration config = context.getResources().getConfiguration();

        String lang = settings.getString("EnumLanguage", EnumLanguage.en.name());
        if (!lang.equals("") && !config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }*/

   /* public static EnumLanguage getLanguageIdFromSharedPreferences(Context context) {
        //getting language from SharedPreferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Configuration config = context.getResources().getConfiguration();

        String lang = settings.getString("EnumLanguage", EnumLanguage.en.name());
        if (!lang.equals("")) {
            return Enum.valueOf(EnumLanguage.class, lang);
        }
        return EnumLanguage.en;
    }*/

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static int getAppVersion(Context context) {
        int version = 1;
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            // throw new RuntimeException("Could not get package name: " + e);
        }
        return version;
    }

    //get app version name
    public static String getAppVersionName(Context context) {
        String version = "";
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            // throw new RuntimeException("Could not get package name: " + e);

        }
        return version;
    }

    //get app package name
    public static String getAppPackageName(Context context) {
        String pkName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            pkName = packageInfo.packageName;

        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            // throw new RuntimeException("Could not get package name: " + e);

        }
        return pkName;
    }


    public static Date parseDateFromString(String strDate) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = format.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static Date parseDateFromStringWithLilisec(String strDate) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS Z");
        try {
            date = format.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    //convert date for gate day month year hours min am/pm
    public static String convertDate(String date) {
        StringBuilder sb = new StringBuilder();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date dat = sdf.parse(date);
            String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", dat);//Thursday
            String stringMonth = (String) android.text.format.DateFormat.format("MMM", dat); //Jun
            String intMonth = (String) android.text.format.DateFormat.format("MM", dat); //06
            String year = (String) android.text.format.DateFormat.format("yyyy", dat); //2013
            String day = (String) android.text.format.DateFormat.format("dd", dat); //20
            String hours = (String) android.text.format.DateFormat.format("h", dat); //20
            String min = (String) android.text.format.DateFormat.format("mm", dat); //20
            String ampm = (String) android.text.format.DateFormat.format("aa", dat); //am/pm
            //Log.d(Contants.LOG_TAG, "dayOfTheWeek*********" + dayOfTheWeek);
            //Log.d(Contants.LOG_TAG, "year*********" + year);
            //Log.d(Contants.LOG_TAG, "stringMonth*********" + stringMonth+"  "+intMonth);
           /* Log.d(Contants.LOG_TAG, "day*********" + day);
            Log.d(Contants.LOG_TAG, "hours*********" + hours);
            Log.d(Contants.LOG_TAG, "min*********" + min);
            Log.d(Contants.LOG_TAG, "ampm*********" + ampm);*/
            List<String> list = new ArrayList<String>();
            list.add(dayOfTheWeek);
            list.add(day);
            list.add(stringMonth);
            list.add(hours);
            list.add(min);
            list.add(ampm);
            list.add(year);
            list.add(intMonth);
            String delim = "";
            for (String i : list) {
                sb.append(delim).append(i);
                delim = ",";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //calculate total days between to days
    public static int daysBetween(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa", Locale.ENGLISH);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(startDate);
            date2 = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
    }

    //check online
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager;
        boolean connected = false;
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            //System.out.println("CheckConnectivity Exception: " + e.getMessage());
            //Log.v("connectivity", e.toString());
        }
        return connected;
    }

    /**
     * Determine if the device is a tablet (i.e. it has a large screen).
     *
     * @param context The calling context.
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) > Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //set select folder path
    public static void setSelectFolderPathIntoSharedPreferences(Context context, String path) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("folderPath", path).commit();
    }

    //get selcet folder path
    public static String getSelectFolderPathFromSharedPreferences(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String folderPath = settings.getString("folderPath", null);
        return folderPath;
    }


    //set user server id
    public static void setUserServerIdIntoSharedPreferences(Context context, int serverId) {
        SharedPreferences prefs = context.getSharedPreferences("UserServerId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("ServerId", serverId);
        editor.commit();
    }

    //get selcet folder path
    public static int getUserServerIdFromSharedPreferences(Context context) {
        int ServerId = 0;
        SharedPreferences prefs = context.getSharedPreferences("UserServerId", Context.MODE_PRIVATE);
        if (prefs != null) {
            ServerId = prefs.getInt("ServerId", 0);
        }
        return ServerId;
    }

    //set device id for  GCM
    public static void setDeviceIDIntoSharedPreferences(Context context, String device_token) {
        SharedPreferences prefs = context.getSharedPreferences("FCMDeviceId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("device_token", device_token);
        editor.commit();
    }

    //get device id for  GCM
    public static String getDeviceIDFromSharedPreferences(Context context) {
        String device_token = null;
        SharedPreferences prefs = context.getSharedPreferences("GCMDeviceId", Context.MODE_PRIVATE);
        if (prefs != null) {
            device_token = prefs.getString("device_token", null);
        }
        return device_token;
    }

    //listview scroll disable
    public static void justifyListViewHeightBasedOnChildrenForDisableScrool(ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    public static String getCurrentDateTime() {
        String currentTime = Calendar.getInstance().getTime().toString();
        return currentTime;
    }
    public static void hideKeyboard(Activity activity) {
        hideKeyBoard(activity, activity.getCurrentFocus());
    }
    public static void hideKeyBoard(Context context, View v) {
        if (v == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    public static void showToast(Context context, String msg) {
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1500);
    }
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        Uri uri = null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        if (path != null) {
            uri = Uri.parse(path);
        }
        return uri;
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
    //to show keyboard
    public static void showKeyboard(Context context, EditText yourEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    //to hide keyboard
    public static void hideKeyboard(Context context, EditText yourEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(yourEditText.getWindowToken(), 0);
    }

    //This works for using it in a dialog
    public static void showKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
