package com.example.user.new_italk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.user.new_italk.Adapter.GroupListAdapter;
import com.example.user.new_italk.JAVA_Bean.Group;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GroupActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static Activity GroupActivity;
    private GridView listView;
    private GroupListAdapter adapter;
    private List<Group> groupList;
    private List<Group> saveList;
    String groupname, groupadministrator, loginUserid, type;
    EditText searchGroupEditText;
    ImageView addGroupImgView, setting;
    int groupnum, choiceGroupNum;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        GroupActivity = com.example.user.new_italk.GroupActivity.this;


        Intent intent = getIntent();
        loginUserid = intent.getStringExtra("loginUserid");
        type = intent.getStringExtra("type");

        searchGroupEditText = (EditText) findViewById(R.id.searchGroupEditText);

        // addGroupImgView = (ImageView)findViewById(R.id.addGroupImgView);

        listView = (GridView) findViewById(R.id.groupListView);
        groupList = new ArrayList<Group>();
        saveList = new ArrayList<Group>();

        setting = (ImageView)findViewById(R.id.setting);


        adapter = new GroupListAdapter(getApplicationContext(), groupList, loginUserid, saveList, this, type);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SettingBackgroundTask().execute();
            }
        });



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

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });


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


    public void searchGroup(String searchText) {
        groupList.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if ((saveList.get(i).getGroupname().contains(searchText))) {
                groupList.add(saveList.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        choiceGroupNum = groupList.get(i).getGroupnum();
        new GroupMemberBackgroundTask().execute();
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/UserList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        public void onPostExecute(String result) {
            Intent intent = new Intent(com.example.user.new_italk.GroupActivity.this, AddGroupActivity.class);
            intent.putExtra("userList", result);
            intent.putExtra("loginUserid", loginUserid);
            intent.putExtra("type", "addGroup");
            com.example.user.new_italk.GroupActivity.this.startActivity(intent);
        }
    }


    class GroupMemberBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/GroupMemberList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        public void onPostExecute(String result) {

            Intent intent = new Intent(com.example.user.new_italk.GroupActivity.this, AlterGroupActivity.class);
            intent.putExtra("groupMemberList", result);
            intent.putExtra("loginUserid", loginUserid);
            intent.putExtra("type", "addGroup");
            intent.putExtra("choiceGroupNum", choiceGroupNum);
            com.example.user.new_italk.GroupActivity.this.startActivity(intent);
        }
    }

    class SettingBackgroundTask extends AsyncTask<Void, Void, String> {
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
            Intent intent = new Intent( com.example.user.new_italk.GroupActivity.this, SettingGroupActivity.class);
            intent.putExtra("groupList", result);
            intent.putExtra("loginUserid", loginUserid);
            intent.putExtra("type", "SettingGroupList");
            com.example.user.new_italk.GroupActivity.this.startActivity(intent);
        }
    }
}
