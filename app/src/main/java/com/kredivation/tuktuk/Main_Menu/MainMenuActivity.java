package com.kredivation.tuktuk.Main_Menu;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kredivation.tuktuk.SimpleClasses.Variables;

import com.kredivation.tuktuk.R;
import com.kredivation.tuktuk.runtimepermission.PermissionResultCallback;
import com.kredivation.tuktuk.runtimepermission.PermissionUtils;

import java.util.ArrayList;


public class MainMenuActivity extends AppCompatActivity implements  ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {
    public static MainMenuActivity mainMenuActivity;
    private MainMenuFragment mainMenuFragment;
    long mBackPressed;


    public static String token;

    public static Intent intent;
    PermissionUtils permissionUtils;
    ArrayList<String> permissions = new ArrayList<>();
    private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mainMenuActivity=this;

        intent=getIntent();

        setIntent(null);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Variables.screen_height= displayMetrics.heightPixels;
        Variables.screen_width= displayMetrics.widthPixels;

        Variables.sharedPreferences=getSharedPreferences(Variables.pref_name,MODE_PRIVATE);

        Variables.user_id=Variables.sharedPreferences.getString(Variables.u_id,"");
        Variables.user_name=Variables.sharedPreferences.getString(Variables.u_name,"");
        Variables.user_pic=Variables.sharedPreferences.getString(Variables.u_pic,"");


        token= FirebaseInstanceId.getInstance().getToken();
        if(token==null || (token.equals("")||token.equals("null")))
            token=Variables.sharedPreferences.getString(Variables.device_token,"null");


        if (savedInstanceState == null) {

            initScreen();

        } else {
            mainMenuFragment = (MainMenuFragment) getSupportFragmentManager().getFragments().get(0);
        }


        runTimePermission();

    }


    private void initScreen() {
        mainMenuFragment = new MainMenuFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mainMenuFragment)
                .commit();

        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }






    @Override
    public void onBackPressed() {
        if (!mainMenuFragment.onBackPressed()) {
            int count = this.getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                if (mBackPressed + 2000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                    return;
                } else {
                    Toast.makeText(getBaseContext(), "Tap Again To Exit", Toast.LENGTH_SHORT).show();
                    mBackPressed = System.currentTimeMillis();

                }
            } else {
                super.onBackPressed();
            }
        }

    }

    private void runTimePermission() {
        permissionUtils = new PermissionUtils(MainMenuActivity.this);
        permissions.add(Manifest.permission.INTERNET);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionUtils.check_permission(permissions, "Storage Services Permissions are required for this App.", REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
    }

    @Override
    public void PermissionGranted(int request_code) {

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
