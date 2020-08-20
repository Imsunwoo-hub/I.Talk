package com.example.user.new_italk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Request.LoginRequest;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView registerButton;
    EditText loginIDText;
    EditText loginPWText;
    Button loginButton;
    private AlertDialog dialog;
    String autoLoginId, autoLoginPassword;
    CheckBox autoLoginCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = (TextView) findViewById(R.id.registerTextButton);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginIDText = (EditText)findViewById(R.id.loginIdText);
        loginPWText = (EditText)findViewById(R.id.loginPwText);
        autoLoginCheckBox = (CheckBox)findViewById(R.id.autoLoginCheckBox);


        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLoginId = auto.getString("inputId",null);
        autoLoginPassword = auto.getString("inputPwd",null);

        if(autoLoginId != null && autoLoginPassword != null){

            final Response.Listener<String> reponseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            String userid = jsonResponse.getString("userid");
                            String username = jsonResponse.getString("username");
                            String useridentity = jsonResponse.getString("useridentity");
                            String usergrade = jsonResponse.getString("usergrade");
                            String usermajor = jsonResponse.getString("usermajor");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userId", userid);
                            intent.putExtra("userName", username);
                            intent.putExtra("userIdentity", useridentity);
                            intent.putExtra("userGrade", usergrade);
                            intent.putExtra("userMajor", usermajor);
                            LoginActivity.this.startActivity(intent);
                            finish();
                        }
                        else{
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = auto.edit();
                            editor.clear();
                            editor.commit();
                            AlertDialog.Builder bulilder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = bulilder.setMessage("로그인에 실패했습니다.")
                                    .setNegativeButton("다시시도", null)
                                    .create();
                            dialog.show();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(autoLoginId, autoLoginPassword, reponseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }



        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, PersonalInfoActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userid = loginIDText.getText().toString();
                final String userpassword = loginPWText.getText().toString();

                if (userid.equals("")) {
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = bulilder.setMessage("아이디를 입력해주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (userpassword.equals("")) {
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = bulilder.setMessage("비밀번호를 입력해주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                final Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                if(autoLoginCheckBox.isChecked() == true){
                                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor autoLogin = auto.edit();
                                    autoLogin.putString("inputId", userid);
                                    autoLogin.putString("inputPwd", userpassword);
                                    autoLogin.commit();
                                    String userid = jsonResponse.getString("userid");
                                    String username = jsonResponse.getString("username");
                                    String useridentity = jsonResponse.getString("useridentity");
                                    String usergrade = jsonResponse.getString("usergrade");
                                    String usermajor = jsonResponse.getString("usermajor");
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userId", userid);
                                    intent.putExtra("userName", username);
                                    intent.putExtra("userIdentity", useridentity);
                                    intent.putExtra("userGrade", usergrade);
                                    intent.putExtra("userMajor", usermajor);
                                    LoginActivity.this.startActivity(intent);
                                    finish();
                                }
                                else {
                                    String userid = jsonResponse.getString("userid");
                                    String username = jsonResponse.getString("username");
                                    String useridentity = jsonResponse.getString("useridentity");
                                    String usergrade = jsonResponse.getString("usergrade");
                                    String usermajor = jsonResponse.getString("usermajor");
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userId", userid);
                                    intent.putExtra("userName", username);
                                    intent.putExtra("userIdentity", useridentity);
                                    intent.putExtra("userGrade", usergrade);
                                    intent.putExtra("userMajor", usermajor);
                                    LoginActivity.this.startActivity(intent);
                                    finish();
                                }
                            }
                            else{
                                AlertDialog.Builder bulilder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = bulilder.setMessage("로그인에 실패했습니다.")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userid, userpassword, reponseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
    protected void onStop(){
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}

