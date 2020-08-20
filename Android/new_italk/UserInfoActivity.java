package com.example.user.new_italk;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Request.DeleteUserInfoRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoActivity extends AppCompatActivity {

    public static Activity userInfoActivity;
    TextView alterUserTextView, idTextView, nameTextView, phonenumTextView, emailTextView, majorTextView,
            gradeTextView, idtext, gradetext, title, stateTextView;
    ImageView alterContactImageView;
    Button alterPasswordButton, deleteUserInfoButton;
    String authority, userid, username, userpassword, userphonenum, useremail, division, usergrade, usermajor, useridentity, loginUserid, userstate;
    private AlertDialog dialog;
    //UserListActivity userListActivity = (UserListActivity)UserListActivity.userListActivity;
    //MainActivity mainActivity = (MainActivity)MainActivity.mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userInfoActivity = UserInfoActivity.this;

        title = (TextView)findViewById(R.id.title);
        alterUserTextView = (TextView)findViewById(R.id.alterUserTextView);
        idTextView = (TextView)findViewById(R.id.idTextView);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        phonenumTextView = (TextView)findViewById(R.id.phonenumTextView);
        emailTextView = (TextView)findViewById(R.id.emailTextView);
        majorTextView = (TextView)findViewById(R.id.majorTextView);
        gradeTextView = (TextView)findViewById(R.id.gradeTextView);
        gradetext = (TextView)findViewById(R.id.grade);
        idtext = (TextView)findViewById(R.id.id);
        stateTextView = (TextView)findViewById(R.id.stateTextView);
        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);

        Intent userInfoIntent = getIntent();
        authority = userInfoIntent.getStringExtra("authority");
        loginUserid = userInfoIntent.getStringExtra("loginUserid");
         userid = userInfoIntent.getStringExtra("userid");
        username = userInfoIntent.getStringExtra("username");
        //userpassword = userInfoIntent.getStringExtra("userpassword");
         userphonenum = userInfoIntent.getStringExtra("userphonenum");
        useremail = userInfoIntent.getStringExtra("useremail");
        usergrade = userInfoIntent.getStringExtra("usergrade");
        usermajor = userInfoIntent.getStringExtra("usermajor");
        useridentity = userInfoIntent.getStringExtra("useridentity");
        userstate = userInfoIntent.getStringExtra("userstate");

        idTextView.setText(userid);
        nameTextView.setText(username);
        phonenumTextView.setText(userphonenum);
        emailTextView.setText(useremail);
        gradeTextView.setText(usergrade+"학년");
        majorTextView.setText(usermajor);
        stateTextView.setText("(" + userstate +")");

        if(authority.equals("userManagement")){
            title.setText("사용자 관리");
            alterUserTextView.setText("수정");
            alterUserTextView.setTextColor(Color.rgb(0,0,0));
            alterUserTextView.setPaintFlags(alterUserTextView.getPaintFlags());
        }

        if(!useridentity.equals("학생")){
            gradetext.setVisibility(View.GONE);
            gradeTextView.setVisibility(View.GONE);
            idtext.setText("사번 : ");
            stateTextView.setVisibility(View.GONE);
            alterUserTextView.setText("수정");
            alterUserTextView.setTextColor(Color.rgb(0,0,0));
            alterUserTextView.setPaintFlags(alterUserTextView.getPaintFlags());
        }

        if(alterUserTextView.getText().toString().equals("수정")){
            alterUserTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent alterUserInfoIntent = new Intent(UserInfoActivity.this, AlterUserInfoActivity.class);
                    alterUserInfoIntent.putExtra("authority", authority);
                    alterUserInfoIntent.putExtra("loginUserid", loginUserid);
                    alterUserInfoIntent.putExtra("userid", userid);
                    alterUserInfoIntent.putExtra("username", username);
                    alterUserInfoIntent.putExtra("userphonenum", userphonenum);
                    alterUserInfoIntent.putExtra("useremail", useremail);
                    alterUserInfoIntent.putExtra("usermajor", usermajor);
                    alterUserInfoIntent.putExtra("usergrade", usergrade);
                    alterUserInfoIntent.putExtra("userstate",userstate);
                    alterUserInfoIntent.putExtra("useridentity", useridentity);
                    UserInfoActivity.this.startActivity(alterUserInfoIntent);
                }
            });
        }

        alterContactImageView = (ImageView) findViewById(R.id.alterContactImageView);
        alterPasswordButton = (Button)findViewById(R.id.alterPasswordButton);
        deleteUserInfoButton = (Button)findViewById(R.id.deleteUserInfoButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        alterContactImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alterContactIntent = new Intent(UserInfoActivity.this, AlterContactActivity.class);
                alterContactIntent.putExtra("authority", authority);
                alterContactIntent.putExtra("userid", userid);
                alterContactIntent.putExtra("username", username);
                //alterUserInfoIntent.putExtra("userpassword", userpassword);
                alterContactIntent.putExtra("userphonenum", userphonenum);
                alterContactIntent.putExtra("useremail", useremail);
                alterContactIntent.putExtra("usergrade", usergrade);
                alterContactIntent.putExtra("usermajor", usermajor);
                alterContactIntent.putExtra("useridentity", useridentity);
                alterContactIntent.putExtra("userstate", userstate);
                UserInfoActivity.this.startActivity(alterContactIntent);
            }
        });

        alterPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (authority.equals("userManagement")) {
                    Intent alterPasswordIntent = new Intent(UserInfoActivity.this, ALterPasswordActivity.class);
                    alterPasswordIntent.putExtra("authority", authority);
                    alterPasswordIntent.putExtra("userid", userid);
                    UserInfoActivity.this.startActivity(alterPasswordIntent);
                } else {
                    division = "비밀번호 변경";
                    Intent alterPasswordIntent = new Intent(UserInfoActivity.this, IdentificationActivity.class);
                    alterPasswordIntent.putExtra("authority", authority);
                    alterPasswordIntent.putExtra("division", division);
                    alterPasswordIntent.putExtra("userid", userid);
                    UserInfoActivity.this.startActivity(alterPasswordIntent);
                }
            }
        });

        deleteUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(authority.equals("userManagement")){
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(UserInfoActivity.this);
                    dialog = bulilder.setMessage(username + " 회원을 탈퇴시키겠습니까??")
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
                else {
                    division = "회원 탈퇴";
                    Intent deleteUserInfoIntent = new Intent(UserInfoActivity.this, IdentificationActivity.class);
                    deleteUserInfoIntent.putExtra("division", division);
                    deleteUserInfoIntent.putExtra("userid", userid);
                    UserInfoActivity.this.startActivity(deleteUserInfoIntent);
                }
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
                        builder.setMessage("회원탈퇴가 완료되었습니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                        //userListActivity.finish();
                                        finish();
                                        //new BackgroundTask().execute();
                                        //startActivity(intent);
                                    }
                                })
                                .create()
                                .show();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
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
        RequestQueue queue = Volley.newRequestQueue(UserInfoActivity.this);
        queue.add(deleteUserInfoRequest);
    }

    /*class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/UserList.php";
        }

        @Override
        protected  String doInBackground(Void... voids){
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }
        public  void onPostExecute(String result) {
            Intent intent = new Intent(UserInfoActivity.this, UserListActivity.class);
            intent.putExtra("userList", result);
            intent.putExtra("type", "userManagement");
            intent.putExtra("loginUserid", loginUserid);
            UserInfoActivity.this.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(authority.equals("profile")){
            Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
            intent.putExtra("userid", userid);
            intent.putExtra("username", username);
            intent.putExtra("useridentity", useridentity);
            intent.putExtra("usergrade", usergrade);
            intent.putExtra("usermajor", usermajor);
            UserInfoActivity.this.startActivity(intent);
            mainActivity.finish();
            finish();
        } else{
            userListActivity.finish();
            finish();
            new BackgroundTask().execute();
        }

    }*/

}
