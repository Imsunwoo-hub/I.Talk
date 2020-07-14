package com.example.user.new_italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Adapter.InquiryListAdapter;
import com.example.user.new_italk.JAVA_Bean.Inquiry;
import com.example.user.new_italk.Request.DeleteGroupsRequest;
import com.example.user.new_italk.Request.DeleteInquirysRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettingInquiryActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ListView inquiryListView;
    private List<Inquiry> inquiryList;
    private List<Inquiry> inquirySaveList;
    private InquiryListAdapter inquiryAdapter;
    String loginUserid, type, loginUserName;
    int checkCount = 0;
    TextView deleteGroups, countCheck;
    CheckBox checkedAll;
    private AlertDialog dialog;
    private String[] checkedInquriryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_inquiry);

        Intent intent = getIntent();
        loginUserid = intent.getStringExtra("loginUserid");
        loginUserName = intent.getStringExtra("loginUsername");
        type = intent.getStringExtra("type");


        inquiryListView = (ListView)findViewById(R.id.inquiryListView);
        inquiryList = new ArrayList<Inquiry>();
        inquirySaveList = new ArrayList<Inquiry>();
        inquiryAdapter = new InquiryListAdapter(getApplicationContext(), inquiryList, inquirySaveList, loginUserName, type);
        inquiryListView.setAdapter(inquiryAdapter);

         deleteGroups = (TextView)findViewById(R.id.deleteGroups);
         countCheck = (TextView)findViewById(R.id.count);
         checkedAll = (CheckBox)findViewById(R.id.checkedAll);

        checkedAll.setOnCheckedChangeListener(this);

        countCheck.setText(" ");
        deleteGroups.setTextColor(Color.GRAY);
        deleteGroups.setEnabled(false);

        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("inquiryList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            inquiryList.clear();
            inquirySaveList.clear();
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                int inquiryNum = object.getInt("inquiryNum");
                String user1 = object.getString("user1");
                String user2 = object.getString("user2");
                String lastComment = object.getString("lastComment");
                Date lastCommentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(object.getString("lastCommentDateTime"));
                if(loginUserName.equals( user1 ) || loginUserName.equals( user2 )) {
                    if(!(lastComment.equals("createChatComment"))) {
                        Inquiry inquiry = new Inquiry(inquiryNum, user1, user2, lastComment, lastCommentDateTime, false);
                        inquiryList.add(inquiry);
                        inquirySaveList.add(inquiry);
                    }
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        inquiryListView.setOnItemClickListener(itemClickListener);

        deleteGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedInquriryArray = new String[checkCount];
                int count = 0;
                for(int i=0; i<inquiryList.size(); i++){
                    if (inquiryList.get(i).isChecked() == true) {
                        checkedInquriryArray[count] = (inquiryList.get(i).getInquiryNum())+"";
                        count++;
                    }
                }
                AlertDialog.Builder bulilder = new AlertDialog.Builder(SettingInquiryActivity.this);
                dialog = bulilder.setMessage(checkCount+"개의 대화방을 나가시겠습니까?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                AlertDialog.Builder bulilder = new AlertDialog.Builder(SettingInquiryActivity.this);
                                                dialog = bulilder.setMessage("대화방 나가기가 완료되었습니다.")
                                                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                finish();
                                                            }
                                                        })
                                                        .create();
                                                dialog.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                DeleteInquirysRequest deleteInquirysRequest = new DeleteInquirysRequest(checkedInquriryArray, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(SettingInquiryActivity.this);
                                queue.add(deleteInquirysRequest);
                            }
                        }).create();
                dialog.show();
            }
        });


    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            for(int i=0; i<inquiryList.size(); i++){
                inquiryList.get(i).setChecked(isChecked);
            }
            checkCount = inquiryList.size();
            countCheck.setText(checkCount+"개방 ");
            deleteGroups.setTextColor(Color.WHITE);
            deleteGroups.setEnabled(true);
            inquiryAdapter.notifyDataSetChanged();
        }else{
            for(int i=0; i<inquiryList.size(); i++){
                inquiryList.get(i).setChecked(isChecked);
            }
            checkCount = 0;
            countCheck.setText(" ");
            deleteGroups.setTextColor(Color.GRAY);
            deleteGroups.setEnabled(false);
            inquiryAdapter.notifyDataSetChanged();
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

            inquiryAdapter.setCheck(i);
            inquiryAdapter.notifyDataSetChanged();

            boolean ck = inquiryList.get(i).isChecked();
            if(ck==true){
                checkCount = checkCount+1;
                countCheck.setText(checkCount+"개방 ");
                deleteGroups.setTextColor(Color.WHITE);
                deleteGroups.setEnabled(true);
            }
            else{
                checkCount = checkCount-1;
                countCheck.setText(checkCount+"개방 ");
                if(checkCount==0){
                    countCheck.setText("");
                    deleteGroups.setTextColor(Color.GRAY);
                    deleteGroups.setEnabled(false);
                }
            }

        }
    };
}
