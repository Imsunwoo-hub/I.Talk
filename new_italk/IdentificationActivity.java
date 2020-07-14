package com.example.user.new_italk;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Request.DeleteUserInfoRequest;
import com.example.user.new_italk.Request.IdentificationRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class IdentificationActivity extends AppCompatActivity {

    EditText passwordEditText;
    Button certainButton;
    String userid, division,authority;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        certainButton = (Button) findViewById(R.id.certainButton);
        ImageView cancelButton = (ImageView) findViewById(R.id.cancelButton);

        Intent identificationIntent = getIntent();
        division = identificationIntent.getStringExtra("division");
        authority = identificationIntent.getStringExtra("authority");
        userid = identificationIntent.getStringExtra("userid");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        certainButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String userpassword = passwordEditText.getText().toString();

                if (userpassword.equals("")) {
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(IdentificationActivity.this);
                    dialog = bulilder.setMessage("비밀번호를 입력해주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                if (division.equals("비밀번호 변경")) {
                                    Intent alterpasswordIntent = new Intent(IdentificationActivity.this, ALterPasswordActivity.class);
                                    alterpasswordIntent.putExtra("userid", userid);
                                    alterpasswordIntent.putExtra("authority",authority);
                                    IdentificationActivity.this.startActivity(alterpasswordIntent);
                                    finish();
                                } else if (division.equals("회원 탈퇴")) {
                                    AlertDialog.Builder bulilder = new AlertDialog.Builder(IdentificationActivity.this);
                                    dialog = bulilder.setMessage("회원 탈퇴를 진행 하시겠습니까??.")
                                            .setNegativeButton("취소", null)
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    deleteUserInfo(userid);
                                                }
                                            })
                                            .create();
                                    dialog.show();

                                }
                            } else {
                                AlertDialog.Builder bulilder = new AlertDialog.Builder(IdentificationActivity.this);
                                dialog = bulilder.setMessage("본인인증에 실패했습니다.")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                IdentificationRequest identificationRequest = new IdentificationRequest(userid, userpassword, reponseListener);
                RequestQueue queue = Volley.newRequestQueue(IdentificationActivity.this);
                queue.add(identificationRequest);
            }
        });

    }

    private void deleteUserInfo(String userid) {
        Response.Listener<String> reponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(IdentificationActivity.this);
                        builder.setMessage("회원탈퇴가 완료되었습니다.")
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
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(IdentificationActivity.this);
                        builder.setMessage("회원탈퇴가 실패되었습니다.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DeleteUserInfoRequest deleteUserInfoRequest = new DeleteUserInfoRequest(userid, reponseListener);
        RequestQueue queue = Volley.newRequestQueue(IdentificationActivity.this);
        queue.add(deleteUserInfoRequest);
    }
}