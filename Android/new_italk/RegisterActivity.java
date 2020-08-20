package com.example.user.new_italk;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Request.RegisterRequest;
import com.example.user.new_italk.Request.StaffRegisterRequest;
import com.example.user.new_italk.Request.ValidateRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter identityAdapter, gradeAdapter, majorAdapter;
    private Spinner identitySpinner, gradeSpinner, majorSpinner;
    private LinearLayout studentInfo;
    private EditText pwText, confirmPwText, idText, nameText, emailText, phonenumText;
    private TextView confirm;
    private Button validateButton, registerButton;
    private boolean pwOk = false,  validate = false;
    private String userid, userpassword, username, userphonenum, useremail, userstatus, usermajor, useridentity, usergrade;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idText = (EditText)findViewById(R.id.idText);
        nameText = (EditText)findViewById(R.id.nameText);
        emailText = (EditText)findViewById(R.id.emText);
        phonenumText = (EditText)findViewById(R.id.pnText);

        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);


        identitySpinner = (Spinner) findViewById(R.id.identitySpinner);
        identityAdapter = ArrayAdapter.createFromResource(this, R.array.identity, R.layout.support_simple_spinner_dropdown_item);

        gradeSpinner = (Spinner) findViewById(R.id.gradeSpinner);
        gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, R.layout.support_simple_spinner_dropdown_item);

        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);
        majorAdapter = ArrayAdapter.createFromResource(this, R.array.major, R.layout.support_simple_spinner_dropdown_item);

        identitySpinner.setAdapter(identityAdapter);
        gradeSpinner.setAdapter(gradeAdapter);
        majorSpinner.setAdapter(majorAdapter);

        studentInfo = (LinearLayout) findViewById(R.id.studentInfo);

        RadioGroup status = (RadioGroup)findViewById(R.id.statusGroup);
        int statusGroupID = status.getCheckedRadioButtonId();
        userstatus = ((RadioButton)findViewById(statusGroupID)).getText().toString();

        status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton statusButton = (RadioButton)findViewById(i);
                userstatus = statusButton.getText().toString();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = idText.getText().toString();
                String userpassword = pwText.getText().toString();
                String username = nameText.getText().toString();
                String userphonenum = phonenumText.getText().toString();
                String useremail = emailText.getText().toString();
                String useridentity = identitySpinner.getSelectedItem().toString();
                String usermajor = majorSpinner.getSelectedItem().toString();
                String usergrade = null;
                String userstate = null;

                if(useridentity.equals("학생")){
                    usergrade = gradeSpinner.getSelectedItem().toString();
                    userstate = userstatus;

                    if(!validate){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("아이디 중복확인을 해주세요.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                    if(!pwText.getText().toString().equals(confirmPwText.getText().toString())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("비밀번호를 확인해주세요.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                    if(userid.equals("") || userpassword.equals("") || username.equals("") || userphonenum.equals("") || useremail.equals("") || usermajor.equals("") || usergrade.equals("") || userstate.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("정보를 빠짐 없이 확인해주세요.")
                                .setPositiveButton("확인", null)
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
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                    finish();
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("회원가입이 실패되었습니다.")
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
                    RegisterRequest registerRequest = new RegisterRequest(userid, userpassword, username, userphonenum, useremail, useridentity,usermajor, usergrade, userstate, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);

                } else{
                    if(!validate){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("아이디 중복확인을 해주세요.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                    if(!pwText.getText().toString().equals(confirmPwText.getText().toString())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("비밀번호를 확인해주세요.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                    if(userid.equals("") || userpassword.equals("") || username.equals("") || userphonenum.equals("") || useremail.equals("") || usermajor.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("정보를 빠짐 없이 확인해주세요.")
                                .setPositiveButton("확인", null)
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
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                    finish();
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("회원가입이 실패되었습니다.")
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
                    StaffRegisterRequest staffregisterRequest = new StaffRegisterRequest(userid, userpassword, username, userphonenum, useremail, useridentity, usermajor, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(staffregisterRequest);

                }
            }
        });

        validateButton = (Button)findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid = idText.getText().toString();
                if(validate){
                    return;
                }
                if(userid.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할수 있는 아이디 입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(Color.GRAY);

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                ValidateRequest validateRequest = new ValidateRequest(userid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });


        pwText = (EditText) findViewById(R.id.pwText);
        confirmPwText = (EditText) findViewById(R.id.confirmPwText);
        confirm = (TextView) findViewById(R.id.confirm);

        pwText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pwText.getText().toString().equals(confirmPwText.getText().toString())) {
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("일치합니다.");
                    confirm.setTextColor(Color.rgb(0, 255, 0));
                    pwOk = true;
                }
                else {
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("일치하지 않습니다.");
                    confirm.setTextColor(Color.rgb(255, 0, 0));
                    pwOk = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPwText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pwText.getText().toString().equals(confirmPwText.getText().toString())) {
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("일치합니다.");
                    confirm.setTextColor(Color.rgb(0, 255, 0));
                    pwOk = true;
                }
                else {
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setText("일치하지 않습니다.");
                    confirm.setTextColor(Color.rgb(255, 0, 0));
                    pwOk = false;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        identitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        studentInfo.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        studentInfo.setVisibility(View.GONE);
                        break;
                    case 2:
                        studentInfo.setVisibility(View.GONE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
