package com.example.user.new_italk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Request.AlterContactRequest;

import org.json.JSONObject;

public class AlterContactActivity extends AppCompatActivity {

    TextView phoneNumTextView, emailTextView;
    EditText newPhoneNumEditText, newEmailEditText;

    String userid, username, userpassword, userphonenum, useremail, usergrade, usermajor, useridentity, userstate, authority;
    private AlertDialog dialog;
    UserInfoActivity userInfoActivity = (UserInfoActivity)UserInfoActivity.userInfoActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_contact);

        phoneNumTextView = (TextView)findViewById(R.id.phoneNumTextView);
        emailTextView = (TextView)findViewById(R.id.emailTextView);

        newPhoneNumEditText = (EditText)findViewById(R.id.newPhoneNumButton);
        newEmailEditText = (EditText)findViewById(R.id.newEmailEditText);

        TextView alterButton = (TextView) findViewById(R.id.alterButton);
        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);

        Intent userInfoIntent = getIntent();
        authority = userInfoIntent.getStringExtra("authority");
        userid = userInfoIntent.getStringExtra("userid");
        username = userInfoIntent.getStringExtra("username");
        //userpassword = userInfoIntent.getStringExtra("userpassword");
        userphonenum = userInfoIntent.getStringExtra("userphonenum");
        useremail = userInfoIntent.getStringExtra("useremail");
        usergrade = userInfoIntent.getStringExtra("usergrade");
        usermajor = userInfoIntent.getStringExtra("usermajor");
        useridentity = userInfoIntent.getStringExtra("useridentity");
        userstate = userInfoIntent.getStringExtra("userstate");


        phoneNumTextView.setText(userphonenum);
        emailTextView.setText(useremail);

        alterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newPhoneNum = newPhoneNumEditText.getText().toString();
                String newEmail = newEmailEditText.getText().toString();

                if(newPhoneNum.equals("") && newEmail.equals("")){
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterContactActivity.this);
                    dialog = bulilder.setMessage("수정할 정보를 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String userid = jsonResponse.getString("userid");
                                //String userpassword = jsonResponse.getString("password");
                                String userphonenum = jsonResponse.getString("userphonenum");
                                String useremail = jsonResponse.getString("useremail");
                                Intent alteruserInfoIntent = new Intent(AlterContactActivity.this, UserInfoActivity.class);
                                alteruserInfoIntent.putExtra("authority", authority);
                                alteruserInfoIntent.putExtra("userid", userid);
                               // alteruserInfoIntent.putExtra("userpassword", userpassword);
                                alteruserInfoIntent.putExtra("username", username);
                                alteruserInfoIntent.putExtra("userphonenum", userphonenum);
                                alteruserInfoIntent.putExtra("useremail", useremail);
                                alteruserInfoIntent.putExtra("usergrade", usergrade);
                                alteruserInfoIntent.putExtra("usermajor", usermajor);
                                alteruserInfoIntent.putExtra("useridentity", useridentity);
                                alteruserInfoIntent.putExtra("userstate", userstate);
                                AlterContactActivity.this.startActivity(alteruserInfoIntent);
                                userInfoActivity.finish();
                                finish();
                            }
                            else{
                                AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterContactActivity.this);
                                dialog = bulilder.setMessage("정보 변경에 실패하였습니다.")
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
                AlterContactRequest alterContactRequest = new AlterContactRequest(userid, userphonenum, newPhoneNum, useremail, newEmail, reponseListener);
                RequestQueue queue = Volley.newRequestQueue(AlterContactActivity.this);
                queue.add(alterContactRequest);
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
