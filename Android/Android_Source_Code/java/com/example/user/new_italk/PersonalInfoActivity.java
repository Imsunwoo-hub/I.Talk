package com.example.user.new_italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PersonalInfoActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ImageView goBackImageView = (ImageView) findViewById(R.id.goBackImageView);
        Button agreeButton = (Button) findViewById(R.id.agreeButton);
        Button disagreeButton = (Button) findViewById(R.id.disagreeButton);

        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent agreeIntent = new Intent(PersonalInfoActivity.this, RegisterActivity.class);
                PersonalInfoActivity.this.startActivity(agreeIntent);
                finish();
            }
        });

        disagreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this);
                dialog = builder.setMessage("개인정보 수집 및 활용 거부 시 회원가입을\n진행할 수 없습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        goBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this);
                dialog = builder.setMessage("개인정보 수집 및 활용 거부 시 회원가입을\n진행할 수 없습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .create();
                dialog.show();
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
