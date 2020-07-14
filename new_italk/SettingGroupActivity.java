package com.example.user.new_italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Adapter.GroupListAdapter;
import com.example.user.new_italk.JAVA_Bean.Group;
import com.example.user.new_italk.Request.DeleteGroupsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SettingGroupActivity extends AppCompatActivity  implements CompoundButton.OnCheckedChangeListener {

    private GridView listView;
    private GroupListAdapter adapter;
    private List<Group> groupList;
    private List<Group> saveList;
    String groupname, groupadministrator, loginUserid, type;
    private String[] checkdeGroupArray;
    EditText searchGroupEditText;
    ImageView addGroupImgView, setting;
    TextView deleteGroups, count;
    CheckBox allCheck;
    int groupnum, choiceGroupNum, checkCount;
    com.example.user.new_italk.GroupActivity GroupActivity = (com.example.user.new_italk.GroupActivity) com.example.user.new_italk.GroupActivity.GroupActivity;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_group);

        deleteGroups = (TextView)findViewById(R.id.deleteGroups);
        count = (TextView)findViewById(R.id.count);
        allCheck = (CheckBox)findViewById(R.id.allCheck);

        allCheck.setOnCheckedChangeListener(this);


        count.setText(" ");
        deleteGroups.setTextColor(Color.GRAY);
        deleteGroups.setEnabled(false);


        listView = (GridView) findViewById(R.id.groupListView);
        groupList = new ArrayList<Group>();
        saveList = new ArrayList<Group>();

        Intent intent = getIntent();
        loginUserid = intent.getStringExtra("loginUserid");
        type = intent.getStringExtra("type");

        adapter = new GroupListAdapter(getApplicationContext(), groupList, loginUserid, saveList, this, type);
        listView.setAdapter(adapter);

        searchGroupEditText = (EditText) findViewById(R.id.searchGroupEditText);

        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("groupList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                groupnum = object.getInt("groupnum");
                groupname = object.getString("groupname");
                groupadministrator = object.getString("groupadministrator");
                Group group = new Group(groupnum, groupname, groupadministrator, false);
                if (groupadministrator.equals(loginUserid)) {
                    groupList.add(group);
                    saveList.add(group);
                }
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdeGroupArray = new String[checkCount];
                int count = 0;
                for(int i=0; i<groupList.size(); i++){
                        if (groupList.get(i).getCheckGroup() == true) {
                            checkdeGroupArray[count] = (groupList.get(i).getGroupnum())+"";
                            count++;
                        }
                }
                AlertDialog.Builder bulilder = new AlertDialog.Builder(SettingGroupActivity.this);
                dialog = bulilder.setMessage(checkCount+"개의 그룹을 삭제하시겠습니까?")
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
                                                AlertDialog.Builder bulilder = new AlertDialog.Builder(SettingGroupActivity.this);
                                                dialog = bulilder.setMessage("그룹 삭제가 완료되었습니다.")
                                                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                GroupActivity.finish();
                                                                finish();
                                                                new SettingGroupActivity.BackgroundTask().execute();
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
                                DeleteGroupsRequest deleteGroupsRequest = new DeleteGroupsRequest(checkdeGroupArray, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(SettingGroupActivity.this);
                                queue.add(deleteGroupsRequest);
                            }
                        }).create();
                        dialog.show();
            }
        });

        listView.setOnItemClickListener(itemClickListener);


        searchGroupEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                searchGroup(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            for(int i=0; i<groupList.size(); i++){
                groupList.get(i).setCheckGroup(isChecked);
            }
            checkCount = groupList.size();
            count.setText(checkCount+"그룹 ");
            deleteGroups.setTextColor(Color.WHITE);
            deleteGroups.setEnabled(true);
            adapter.notifyDataSetChanged();
        }else{
            for(int i=0; i<groupList.size(); i++){
                groupList.get(i).setCheckGroup(isChecked);
            }
            checkCount = 0;
            count.setText(" ");
            deleteGroups.setTextColor(Color.GRAY);
            deleteGroups.setEnabled(false);
            adapter.notifyDataSetChanged();
        }
    }



    public void searchGroup(String searchText) {
        groupList.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if ((saveList.get(i).getGroupname().contains(searchText))) {
                groupList.add(saveList.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

            adapter.setCheck(i);
            adapter.notifyDataSetChanged();

            boolean ck = groupList.get(i).getCheckGroup();
            if(ck==true){
                checkCount = checkCount+1;
                count.setText(checkCount+"그룹 ");
                deleteGroups.setTextColor(Color.WHITE);
                deleteGroups.setEnabled(true);
            }
            else{
                checkCount = checkCount-1;
                count.setText(checkCount+"그룹 ");
                if(checkCount==0){
                    count.setText("");
                    deleteGroups.setTextColor(Color.GRAY);
                    deleteGroups.setEnabled(false);
                }
            }

        }
    };

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/GroupList.php";
        }

        @Override
        protected String doInBackground(Void... voids){
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
            Intent intent = new Intent(SettingGroupActivity.this, com.example.user.new_italk.GroupActivity.class);
            intent.putExtra("groupList", result);
            intent.putExtra("loginUserid", loginUserid);
            intent.putExtra("type", "GroupList");
            SettingGroupActivity.this.startActivity(intent);
        }
    }


}
