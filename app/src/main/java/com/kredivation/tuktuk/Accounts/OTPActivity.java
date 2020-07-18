package com.kredivation.tuktuk.Accounts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chaos.view.PinView;
import com.kredivation.tuktuk.R;


public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    private PinView pinView;
    private Button next;
    private TextView topText, textU;
    private ConstraintLayout second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);*/
        setContentView(R.layout.otp_layout);
        topText = findViewById(R.id.topText);
        pinView = findViewById(R.id.pinView);
        next = findViewById(R.id.button);
        second = findViewById(R.id.secondStep);
        textU = findViewById(R.id.textView_noti);
        next.setText("Verify");
        second.setVisibility(View.VISIBLE);
        topText.setText("I Still don't trust you.\nTell me something that only two of us know.");
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                finish();
                Intent intent = new Intent(OTPActivity.this, NameRegisterActivity.class);
                startActivity(intent);
                break;


        }
    }
}
