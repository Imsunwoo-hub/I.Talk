package com.example.user.new_italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Request.AlterUserInfoRequest;
import com.example.user.new_italk.Request.ValidateRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AlterUserInfoActivity extends AppCompatActivity {

    TextView idTextView, nameTextView, majorTextView,gradeTextView, grade,
            stateTextView, alterMajorCheckTextView, alterGradeCheckTextView, alterStateCheckTextView;
    EditText newidEditText, newNameEditText;
    Button validateButton;
    Spinner majorSpinner, gradeSpinner, stateSpinner;
    ArrayAdapter majorAdapter, gradeAdapter, stateAdapter;
    String authority, userid, username, usermajor, usergrade, userstate, useridentity, userphonenum, useremail, loginUserid;
    Boolean alterMajorCheck = false, alterGradeCheck = false, alterStateCheck = false, validate = false;
    LinearLayout studentOnly;
    AlertDialog dialog;
    UserInfoActivity userInfoActivity = (UserInfoActivity)UserInfoActivity.userInfoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_user_info);

        studentOnly = (LinearLayout)findViewById(R.id.studentOnly);

        idTextView = (TextView)findViewById(R.id.idTextView);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        majorTextView = (TextView)findViewById(R.id.majorTextView);
        gradeTextView = (TextView)findViewById(R.id.gradeTextView);
        stateTextView = (TextView)findViewById(R.id.stateTextView);
        grade = (TextView)findViewById(R.id.grade);
        alterMajorCheckTextView = (TextView)findViewById(R.id.alterMajorCheckTextView);
        alterGradeCheckTextView = (TextView)findViewById(R.id.alterGradeCheckTextView);
        alterStateCheckTextView = (TextView)findViewById(R.id.alterStateCheckTextView);
        newidEditText = (EditText)findViewById(R.id.newidEditText);
        newNameEditText = (EditText)findViewById(R.id.newNameEditText);
        TextView alterButton = (TextView)findViewById(R.id.alterButton);
        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);
        validateButton = (Button)findViewById(R.id.validateButton);

        Intent intent = getIntent();
        loginUserid = intent.getStringExtra("loginUserid");
        authority = intent.getStringExtra("authority");
        userid = intent.getStringExtra("userid");
        username = intent.getStringExtra("username");
        usermajor = intent.getStringExtra("usermajor");
        usergrade = intent.getStringExtra("usergrade");
        userstate = intent.getStringExtra("userstate");
        useridentity = intent.getStringExtra("useridentity");
        userphonenum = intent.getStringExtra("userphonenum");
        useremail = intent.getStringExtra("useremail");

        idTextView.setText(userid);
        nameTextView.setText(username);
        majorTextView.setText(usermajor);
        gradeTextView.setText(usergrade+"학년");
        stateTextView.setText(userstate);

        if(!(useridentity.equals("학생"))){
            grade.setVisibility(View.GONE);
            gradeTextView.setVisibility(View.GONE);
            stateTextView.setVisibility(View.GONE);
            studentOnly.setVisibility(View.GONE);
        }

        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);
        majorAdapter = ArrayAdapter.createFromResource(this, R.array.major, R.layout.support_simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);

        int majorAdapterPosition = majorAdapter.getPosition(usermajor);

        majorSpinner.setSelection(majorAdapterPosition);

        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(majorSpinner.getSelectedItem().toString().equals(usermajor))){
                    alterMajorCheckTextView.setText("수정");
                    alterMajorCheckTextView.setTextColor(Color.rgb(255,0,0));
                    alterMajorCheck = true;
                } else {
                    alterMajorCheckTextView.setText("");
                    alterMajorCheck = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gradeSpinner = (Spinner) findViewById(R.id.gradeSpinner);
        gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, R.layout.support_simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        int gradeAdapterPosition = gradeAdapter.getPosition(usergrade);

        gradeSpinner.setSelection(gradeAdapterPosition);

        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(gradeSpinner.getSelectedItem().toString().equals(usergrade))){
                    alterGradeCheckTextView.setText("수정");
                    alterGradeCheckTextView.setTextColor(Color.rgb(255,0,0));
                    alterGradeCheck = true;
                } else {
                    alterGradeCheckTextView.setText("");
                    alterGradeCheck = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        stateAdapter = ArrayAdapter.createFromResource(this, R.array.state, R.layout.support_simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        int stateAdapterPosition = stateAdapter.getPosition(userstate);

        stateSpinner.setSelection(stateAdapterPosition);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(stateSpinner.getSelectedItem().toString().equals(userstate))){
                    alterStateCheckTextView.setText("수정");
                    alterStateCheckTextView.setTextColor(Color.rgb(255,0,0));
                    alterStateCheck = true;
                } else {
                    alterStateCheckTextView.setText("");
                    alterStateCheck = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUserId = newidEditText.getText().toString();
                if(validate){
                    return;
                }
                if(newUserId.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlterUserInfoActivity.this);
                    dialog = builder.setMessage("아이디를 입력해 주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(AlterUserInfoActivity.this);
                                dialog = builder.setMessage("사용할수 있는 아이디 입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                newidEditText.setEnabled(false);
                                validate = true;
                                newidEditText.setBackgroundColor(Color.GRAY);

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(AlterUserInfoActivity.this);
                                dialog = builder.setMessage("이미 사용중인 아이디 입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(newUserId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AlterUserInfoActivity.this);
                queue.add(validateRequest);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        alterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newid = newidEditText.getText().toString();
                String newname = newNameEditText.getText().toString();
                String newmajor = majorSpinner.getSelectedItem().toString();
                String newgrade, newstate;
                if(useridentity.equals("학생")) {
                    newgrade = gradeSpinner.getSelectedItem().toString();
                    newstate = stateSpinner.getSelectedItem().toString();
                    if(newid.equals("") && newname.equals("") && !alterMajorCheck && !alterGradeCheck && !alterStateCheck){
                        AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterUserInfoActivity.this);
                        dialog = bulilder.setMessage("수정할 정보를 입력해주세요.")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                }
                else{
                    newgrade = "";
                    newstate = "";
                    if(newid.equals("") && newname.equals("") && !alterMajorCheck){
                        AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterUserInfoActivity.this);
                        dialog = bulilder.setMessage("수정할 정보를 입력해주세요.")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                }

                if(!validate && !newid.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlterUserInfoActivity.this);
                    dialog = builder.setMessage("아이디 중복확인을 해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                final String newuserid = jsonResponse.getString("userid");
                                final String newusername = jsonResponse.getString("username");
                                final String newusermajor = jsonResponse.getString("usermajor");
                                final String newusergrade = jsonResponse.getString("usergrade");
                                final String newuserstate = jsonResponse.getString("userstate");
                                AlertDialog.Builder builder = new AlertDialog.Builder(AlterUserInfoActivity.this);
                                builder.setMessage("회원정보 수정이 완료되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if(authority.equals("profile")) {
                                                    Intent intent = new Intent(AlterUserInfoActivity.this, UserInfoActivity.class);
                                                    intent.putExtra("authority", authority);
                                                    intent.putExtra("userid", newuserid);
                                                    // alteruserInfoIntent.putExtra("userpassword", userpassword);
                                                    intent.putExtra("username", newusername);
                                                    intent.putExtra("userphonenum", userphonenum);
                                                    intent.putExtra("useremail", useremail);
                                                    intent.putExtra("usergrade", newusergrade);
                                                    intent.putExtra("usermajor", newusermajor);
                                                    intent.putExtra("useridentity", useridentity);
                                                    intent.putExtra("userstate", newuserstate);
                                                    AlterUserInfoActivity.this.startActivity(intent);
                                                    userInfoActivity.finish();
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(AlterUserInfoActivity.this, UserInfoActivity.class);
                                                    intent.putExtra("authority", authority);
                                                    intent.putExtra("loginUserid", loginUserid);
                                                    intent.putExtra("userid", newuserid);
                                                    // alteruserInfoIntent.putExtra("userpassword", userpassword);
                                                    intent.putExtra("username", newusername);
                                                    intent.putExtra("userphonenum", userphonenum);
                                                    intent.putExtra("useremail", useremail);
                                                    intent.putExtra("usergrade", newusergrade);
                                                    intent.putExtra("usermajor", newusermajor);
                                                    intent.putExtra("useridentity", useridentity);
                                                    intent.putExtra("userstate", newuserstate);
                                                    AlterUserInfoActivity.this.startActivity(intent);
                                                    userInfoActivity.finish();
                                                    finish();
                                                }
                                            }
                                        })
                                        .create()
                                        .show();
                                //Intent intent = new Intent(AlterUserInfoActivity.this, LoginActivity.class);
                                //AlterUserInfoActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(AlterUserInfoActivity.this);
                                builder.setMessage("회원정보 수정에 실패되었습니다.")
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
                AlterUserInfoRequest alterUserInfoRequest = new AlterUserInfoRequest(userid, newid, username, newname, newmajor, newgrade, newstate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AlterUserInfoActivity.this);
                queue.add(alterUserInfoRequest);
            }
        });

    }
}
