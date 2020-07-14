package com.example.user.new_italk;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Request.AlterPasswordRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ALterPasswordActivity extends AppCompatActivity {

    EditText password,passwordconfirmEditText;
    TextView confirm;

    String userid, authority;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_password);

        Intent alterPasswordIntent = getIntent();
        authority = alterPasswordIntent.getStringExtra("authority");
        userid = alterPasswordIntent.getStringExtra("userid");

        confirm = (TextView)findViewById(R.id.confirm);

        password = (EditText)findViewById(R.id.password);
        passwordconfirmEditText = (EditText)findViewById(R.id.passwordconfirmEditText);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(password.getText().toString().equals(passwordconfirmEditText.getText().toString())){
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("일치합니다.");
                    confirm.setTextColor(Color.rgb(0, 255, 0));
                } else {
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("일치하지 않습니다.");
                    confirm.setTextColor(Color.rgb(255, 0, 0));
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordconfirmEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(passwordconfirmEditText.getText().toString().equals(password.getText().toString())){
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("일치합니다.");
                    confirm.setTextColor(Color.rgb(0, 255, 0));
                } else {
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("일치하지 않습니다.");
                    confirm.setTextColor(Color.rgb(255, 0, 0));
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

         ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);
         TextView alterButton = (TextView)findViewById(R.id.alterButton);

        alterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userpassword = password.getText().toString();
                String passwordconfirm = passwordconfirmEditText.getText().toString();

                if(userpassword.equals("") || passwordconfirm.equals("")){
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(ALterPasswordActivity.this);
                    dialog = bulilder.setMessage("비밀번호를 입력해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if(!userpassword.equals(passwordconfirm)){
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(ALterPasswordActivity.this);
                    dialog = bulilder.setMessage("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                if(authority.equals("userManagement")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ALterPasswordActivity.this);
                                    builder.setMessage("비밀번호 변경이 완료되었습니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    finish();
                                                }
                                            })
                                            .create()
                                            .show();

                                }else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ALterPasswordActivity.this);
                                    builder.setMessage("비밀번호 변경이 완료되었습니다. 다시 로그인 해주세요.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = auto.edit();
                                                    editor.clear();
                                                    editor.commit();
                                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    finishAffinity();
                                                    startActivity(intent);
                                                }
                                            })
                                            .create()
                                            .show();
                                }

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ALterPasswordActivity.this);
                                builder.setMessage("비밀번호 변경이 실패되었습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                };
                AlterPasswordRequest alterPasswordRequest = new AlterPasswordRequest(userid, userpassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ALterPasswordActivity.this);
                queue.add(alterPasswordRequest);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
