package com.example.sinu.capstone.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sinu.capstone.MainActivity;
import com.example.sinu.capstone.R;

/**
 * Created by sinu on 2017-10-15.
 */
public class LoginMainActivity extends Activity {
    //EditText etID;EditText etPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity_main);
        final EditText etID = (EditText) findViewById(R.id.Login_ID);
        final EditText etPW = (EditText) findViewById(R.id.Login_password);

        Button signin = (Button) findViewById(R.id.Login_sign);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((etID.getText().toString().equals("123")) && (etPW.getText().toString().equals("123"))) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "아이디 = " + etID.getText() + "패스 = " + etPW.getText(), Toast.LENGTH_LONG).show();
                }
            }
        });
        Button regist = (Button) findViewById(R.id.Login_regit);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override public void onBackPressed() {
        //super.onBackPressed(); }
    }
}



