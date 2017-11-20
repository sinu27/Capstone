package com.example.sinu.capstone.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sinu.capstone.MainActivity;
import com.example.sinu.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sinu on 2017-10-15.
 */
public class LoginMainActivity extends Activity {
    //DB
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_PW = "pw";

    JSONArray peoples = null;

    ArrayList al_id = new ArrayList();
    ArrayList al_pw = new ArrayList();

    //DB

    EditText etID,etPW;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity_main);

        //DB
        getData("http://ec2-13-124-225-240.ap-northeast-2.compute.amazonaws.com/qble/login.php");

        etID = (EditText) findViewById(R.id.Login_ID);
        etPW = (EditText) findViewById(R.id.Login_password);

        signin = (Button) findViewById(R.id.Login_sign);

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
    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String pw = c.getString(TAG_PW);
                al_id.add(id);
                al_pw.add(pw);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i <al_id.size();i++){
                    if ((etID.getText().toString().equals(al_id.get(i))) && (etPW.getText().toString().equals(al_pw.get(i)))) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(), "아이디 = " + etID.getText() + "패스 = " + etPW.getText(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}