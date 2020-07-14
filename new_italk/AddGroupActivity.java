package com.example.user.new_italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Adapter.CheckedUserListAdapter;
import com.example.user.new_italk.Adapter.MemberListAdapter;
import com.example.user.new_italk.JAVA_Bean.Member;
import com.example.user.new_italk.Request.CreateGroupRequest;

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

public class AddGroupActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ListView listView;
    private MemberListAdapter memberListAdapter;
    private CheckedUserListAdapter checkedUserListAdapter;
    private List<Member> userList;
    private List<Member> saveList;
    private List<Member> checkedUserList, checkedUsersaveList;
    private boolean checkedUser;
    private EditText groupNameTextView, searchUserEditText;
    private Button createButton;
    private String[] groupMemberArray;
    private AlertDialog dialog;
    RecyclerView mRecyclerView;
    GroupActivity GroupActivity = (GroupActivity) com.example.user.new_italk.GroupActivity.GroupActivity;


    String userid, username, usergrade, usermajor, useridentity, loginUserid, type, userstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        loginUserid = intent.getStringExtra("loginUserid");
        type = intent.getStringExtra("type");

        searchUserEditText = (EditText)findViewById(R.id.searchUserEditText);
        groupNameTextView = (EditText)findViewById(R.id.groupNameTextView);
        TextView createGroup = (TextView)findViewById(R.id.createGroup);

        CheckBox checkedAll = (CheckBox)findViewById(R.id.checkedAll);

        checkedAll.setOnCheckedChangeListener(this);

        listView = (ListView) findViewById(R.id.userListView);
        userList = new ArrayList<Member>();
        saveList = new ArrayList<Member>();
        checkedUserList = new ArrayList<Member>();
        checkedUsersaveList = new ArrayList<Member>();

        memberListAdapter = new MemberListAdapter(getApplicationContext(), userList, saveList, this, loginUserid);
        listView.setAdapter(memberListAdapter);

        checkedUserListAdapter = new CheckedUserListAdapter(getApplicationContext(), checkedUserList, checkedUsersaveList, userList, memberListAdapter);
        mRecyclerView.setAdapter(checkedUserListAdapter);

        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                userid = object.getString("userid");
                username = object.getString("username");
                usergrade = object.getString("usergrade");
                usermajor = object.getString("usermajor");
                useridentity = object.getString("useridentity");
                userstate = object.getString("userstate");

                Member member = new Member(userid, username, usergrade, usermajor, useridentity, checkedUser, userstate);

                if(!(loginUserid.equals(userid))) {
                    userList.add(member);
                    saveList.add(member);
                }
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = groupNameTextView.getText().toString();
                groupMemberArray = new String[checkedUserList.size()];

                for (int i=0; i<groupMemberArray.length; i++){
                    groupMemberArray[i] = checkedUserList.get(i).memberid;
                }

                if (groupName.equals("")) {
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(AddGroupActivity.this);
                    dialog = bulilder.setMessage("그룹명을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if (groupMemberArray.length == 0) {
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(AddGroupActivity.this);
                    dialog = bulilder.setMessage("그룹에 속할 맴버를 지정해주세요.")
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
                            if (success) {
                                AlertDialog.Builder bulilder = new AlertDialog.Builder(AddGroupActivity.this);
                                dialog = bulilder.setMessage("그룹생성이 완료되었습니다.")
                                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if(type.equals("addGroup")) {
                                                    GroupActivity.finish();
                                                    finish();
                                                    new BackgroundTask().execute();
                                                }
                                                else if(type.equals("b")){
                                                    finish();
                                                    new BackgroundTask().execute();
                                                }
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
                CreateGroupRequest createGroupRequest = new CreateGroupRequest(loginUserid, groupName, groupMemberArray, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddGroupActivity.this);
                queue.add(createGroupRequest);
            }
        });

        listView.setOnItemClickListener(itemClickListener);

        searchUserEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text =s.toString();
                searchUser(text);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                checkedUserList.clear();
                checkedUsersaveList.clear();
                for(int i=0; i<memberListAdapter.getCount(); i++){
                    userList.get(i).setCheckMember(isChecked);
                    userid = userList.get(i).getMemberid();
                    username = userList.get(i).getMembername();
                    usergrade = userList.get(i).getMembergrade();
                    usermajor = userList.get(i).getMembermajor();
                    useridentity = userList.get(i).getMemberidentity();
                    userstate = userList.get(i).getMemberstate();
                    checkedUser = userList.get(i).getChecked();

                    Member member = new Member(userid, username, usergrade, usermajor, useridentity, checkedUser, userstate);

                    checkedUserList.add(member);
                    checkedUsersaveList.add(member);
                    mRecyclerView.scrollToPosition(checkedUserListAdapter.getItemCount() - 1);
                }
                memberListAdapter.notifyDataSetChanged();
                checkedUserListAdapter.notifyDataSetChanged();
            }
            else{
                for(int i=0; i<memberListAdapter.getCount(); i++){
                    userList.get(i).setCheckMember(isChecked);
                }
                checkedUserList.clear();
                checkedUsersaveList.clear();
                memberListAdapter.notifyDataSetChanged();
                checkedUserListAdapter.notifyDataSetChanged();
            }
    }

    public void searchUser(String searchText){
        userList.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if ((saveList.get(i).getMemberid().contains(searchText) || saveList.get(i).getMembername().contains(searchText) || saveList.get(i).getMembermajor().contains(searchText) || (saveList.get(i).getMembergrade()+"학년").equals(searchText)
                    || saveList.get(i).getMemberidentity().equals(searchText) || saveList.get(i).getMemberstate().equals(searchText))) {
                userList.add(saveList.get(i));
            }
            memberListAdapter.notifyDataSetChanged();
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            memberListAdapter.setCheck(i);
            memberListAdapter.notifyDataSetChanged();
            //checkedUser = !checkedUser;

            userid = userList.get(i).getMemberid();
            username = userList.get(i).getMembername();
            usergrade = userList.get(i).getMembergrade();
            usermajor = userList.get(i).getMembermajor();
            useridentity = userList.get(i).getMemberidentity();
            userstate = userList.get(i).getMemberstate();

            checkedUser = userList.get(i).getChecked();

           Member member = new Member(userid, username, usergrade, usermajor, useridentity, checkedUser, userstate);

            if (checkedUser){
                checkedUserList.add(member);
                checkedUsersaveList.add(member);
                checkedUserListAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(checkedUserListAdapter.getItemCount() - 1);


            }
            else{
                for(int j =0; j<checkedUserList.size(); j++){
                    if(checkedUserList.get(j).getMemberid().equals(userList.get(i).getMemberid())){
                        checkedUserList.remove(j);
                        checkedUsersaveList.remove(j);
                        checkedUserListAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(checkedUserListAdapter.getItemCount() - 1);
                    }
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
            Intent intent = new Intent(AddGroupActivity.this, GroupActivity.class);
            intent.putExtra("groupList", result);
            intent.putExtra("loginUserid", loginUserid);
            intent.putExtra("type", "GroupList");
            AddGroupActivity.this.startActivity(intent);
        }
    }
}
