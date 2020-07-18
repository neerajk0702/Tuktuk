package com.kredivation.tuktuk.Accounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chaos.view.PinView;
import com.kredivation.tuktuk.Main_Menu.MainMenuActivity;
import com.kredivation.tuktuk.R;
import com.kredivation.tuktuk.SoundLists.SoundList_Main_A;

import static com.kredivation.tuktuk.Video_Recording.Video_Recoder_A.Sounds_list_Request_code;


public class NameRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button next;
    private TextView topText;
    EditText firstname_edit,lastNamename_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);*/
        setContentView(R.layout.nameregister_layout);
        topText = findViewById(R.id.topText);
        next = findViewById(R.id.button);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                finish();
                overridePendingTransition(R.anim.in_from_top, R.anim.out_from_bottom);
                startActivity(new Intent(this, MainMenuActivity.class));
                break;


        }
    }

}
