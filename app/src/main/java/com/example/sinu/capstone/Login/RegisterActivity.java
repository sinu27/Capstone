package com.example.sinu.capstone.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinu.capstone.MainActivity;
import com.example.sinu.capstone.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by sinu on 2017-10-15.
 */
public class RegisterActivity extends Activity {

    private static String TAG = "php_RegisterActivity";
    private EditText rId;
    private EditText rPass;
    private EditText rPassConfirm;
    private EditText rName;
    private EditText rPhonenumber;
    private Spinner rYear;
    private Spinner rMonth;
    private Spinner rDay;
    private RadioGroup rSex;
    private RadioButton rMale;
    private RadioButton rFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity_register);
        Button btnRegistSign = (Button)findViewById(R.id.regit_sign);
        Button btnRegistCancel = (Button)findViewById(R.id.regit_cancel);

        rId = (EditText)findViewById(R.id.regit_id);
        rPass = (EditText)findViewById(R.id.regit_pass);
        rPassConfirm = (EditText)findViewById(R.id.regit_passConfirm);
        rName = (EditText)findViewById(R.id.regit_name);
        rPhonenumber = (EditText)findViewById(R.id.regit_phonenumber);
        rYear = (Spinner)findViewById(R.id.regit_year);
        rMonth = (Spinner)findViewById(R.id.regit_month);
        rDay = (Spinner)findViewById(R.id.regit_day);
        rSex = (RadioGroup)findViewById(R.id.regit_sex);
        rMale = (RadioButton)findViewById(R.id.regit_male);
        rFemale = (RadioButton)findViewById(R.id.regit_female);

        btnRegistSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = rId.getText().toString();
                String PASSWORD = rPass.getText().toString();
                String PASSCONFIRM = rPassConfirm.getText().toString();
                String NAME = rName.getText().toString();
                String PHONE_NUMBER = rPhonenumber.getText().toString();
                String SEX = "";
                if(rMale.isChecked() == true){
                    SEX = "M";
                }
                else if(rFemale.isChecked() ==true){
                    SEX = "F";
                }
                String BIRTHDAY = rYear.getSelectedItem().toString() + "-" + rMonth.getSelectedItem().toString() + "-" + rDay.getSelectedItem().toString();

                if(PASSWORD.equals(PASSCONFIRM) == true){

                    if(ID.equals("") || PASSWORD.equals("") || PASSCONFIRM.equals("") || NAME.equals("") || PHONE_NUMBER.equals("") ||
                            rYear.toString().equals("") || rMonth.toString().equals("") || rDay.toString().equals("") ||
                            (rMale.isChecked() == false && rFemale.isChecked() == false)){
                        Toast.makeText(getApplicationContext(),"빈칸을 입력하세요.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        InsertData task = new InsertData();
                        task.execute(ID,PASSWORD,PHONE_NUMBER,NAME,SEX,BIRTHDAY);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"비밀번호를 다시 확인해주세요.",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnRegistCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RegisterActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext()," " + result,Toast.LENGTH_LONG).show();
            //mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
            if(result.equals("회원가입 완료")){
                Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
                startActivity(intent);
                finish();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String ID = (String)params[0];
            String PASSWORD = (String)params[1];
            String PHONE_NUMBER = (String)params[2];
            String NAME = (String)params[3];
            String SEX = (String)params[4];
            String BIRTHDAY = (String)params[5];

            String serverURL = "http://ec2-13-125-34-12.ap-northeast-2.compute.amazonaws.com/qble/registerfinal.php";
            String postParameters = "ID=" + ID + "&PASSWORD=" + PASSWORD + "&PHONE_NUMBER=" + PHONE_NUMBER + "&NAME=" + NAME + "&SEX=" + SEX + "&BIRTHDAY=" + BIRTHDAY;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }
        }
    }
}