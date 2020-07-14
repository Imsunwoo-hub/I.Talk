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
import com.example.user.new_italk.Request.AlterGroupRequest;
import com.example.user.new_italk.Request.DeleteGroupRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AlterGroupActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ListView listView;
    private MemberListAdapter memberListAdapter;
    private CheckedUserListAdapter checkedUserListAdapter;
    private List<Member> userList;
    private List<Member> saveList;
    private List<Member> checkedUserList, checkedUsersaveList;
    private boolean checkedUser = false;
    private EditText groupNameTextView, searchUserEditText;

    private String[] groupMemberArray, newGroupMemberArray;
    private AlertDialog dialog;
    RecyclerView mRecyclerView;
   GroupActivity GroupActivity = (GroupActivity) com.example.user.new_italk.GroupActivity.GroupActivity;

    String loginUserid, userid, username, usergrade, usermajor, useridentity, type, newGroupName, userstate;
    int choiceGroupNum, groupNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_group);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        loginUserid = intent.getStringExtra("loginUserid");
        type = intent.getStringExtra("type");
        choiceGroupNum = intent.getIntExtra("choiceGroupNum", 0);

        searchUserEditText = (EditText) findViewById(R.id.searchUserEditText);
        groupNameTextView = (EditText) findViewById(R.id.groupNameTextView);
        TextView alterButton = (TextView) findViewById(R.id.alterButton);
        TextView deleteButton = (TextView) findViewById(R.id.deleteButton);
        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);

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

        groupMemberArray = new String[checkedUsersaveList.size()];
        for(int i=0; i<groupMemberArray.length; i++){
            groupMemberArray[i] = checkedUsersaveList.get(i).memberid;
        }

        new UserListBackgroundTask().execute();


        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("groupMemberList"));
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
                groupNum = Integer.valueOf(object.getString("groupNum"));

                if (choiceGroupNum == groupNum) {
                    checkedUser = true;
                    Member member = new Member(userid, username, usergrade, usermajor, useridentity, checkedUser, userstate);
                    checkedUserList.add(member);
                    checkedUsersaveList.add(member);
                }

                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(itemClickListener);

        alterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGroupName = groupNameTextView.getText().toString();
                newGroupMemberArray = new String[checkedUserList.size()];
                for(int i=0; i<newGroupMemberArray.length; i++){
                    newGroupMemberArray[i] = checkedUserList.get(i).memberid;
                }

                if(newGroupName.equals("") && newGroupMemberArray.length==0){
                    AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterGroupActivity.this);
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
                                AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterGroupActivity.this);
                                dialog = bulilder.setMessage("그룹정보가 수정되었습니다")
                                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                GroupActivity.finish();
                                                finish();
                                                new GroupListBackgroundTask().execute();
                                            }
                                        })
                                        .create();
                                dialog.show();

                            }
                            else{
                                AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterGroupActivity.this);
                                dialog = bulilder.setMessage("그룹수정에 실패하였습니다.")
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
                AlterGroupRequest alterContactRequest = new AlterGroupRequest(choiceGroupNum, loginUserid, newGroupName, newGroupMemberArray, reponseListener);
                RequestQueue queue = Volley.newRequestQueue(AlterGroupActivity.this);
                queue.add(alterContactRequest);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterGroupActivity.this);
                dialog = bulilder.setMessage("그룹을 삭제하시겠습니까?")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteGroup();
                            }
                        })
                        .create();
                dialog.show();

            }
        });

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

            Member member = new Member(userid, username, usergrade, usermajor, useridentity,checkedUser, userstate);

            if (checkedUser){
                checkedUserList.add(member);
                checkedUserListAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(checkedUserListAdapter.getItemCount() - 1);

            }
            else{
                for(int j =0; j<checkedUserList.size(); j++){
                    if(checkedUserList.get(j).getMemberid().equals(userList.get(i).getMemberid())){
                        checkedUserList.remove(j);
                        checkedUserListAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(checkedUserListAdapter.getItemCount() - 1);
                    }
                }

            }

        }
    };

    class UserListBackgroundTask extends AsyncTask<Void, Void, String> {
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
            try {
                JSONObject jsonObject = new JSONObject(result);
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

                    if (!(loginUserid.equals(userid))) {
                            boolean checkedUser = false;
                            for(int i=0; i<checkedUserListAdapter.getItemCount(); i++){
                                if(userid.equals(checkedUserList.get(i).getMemberid())){
                                    checkedUser = true;
                                }
                            }
                            Member member = new Member(userid, username, usergrade, usermajor, useridentity, checkedUser, userstate);
                            userList.add(member);
                            saveList.add(member);
                        }
                            count++;
                        }


                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
     public void deleteGroup(){
         Response.Listener<String> reponseListener = new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 try {
                     JSONObject jsonResponse = new JSONObject(response);
                     boolean success = jsonResponse.getBoolean("success");
                     if (success) {
                         AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterGroupActivity.this);
                         dialog = bulilder.setMessage("그룹이 삭제되었습니다.")
                                 .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialogInterface, int i) {
                                         GroupActivity.finish();
                                         finish();
                                         new GroupListBackgroundTask().execute();
                                     }
                                 })
                                 .create();
                         dialog.show();
                     } else {
                         AlertDialog.Builder bulilder = new AlertDialog.Builder(AlterGroupActivity.this);
                         dialog = bulilder.setMessage("그룹삭제가 실패되었습니다.")
                                 .setNegativeButton("다시시도", null)
                                 .create();
                         dialog.show();
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                }
             };
            DeleteGroupRequest deleteGroupRequest = new DeleteGroupRequest(choiceGroupNum, reponseListener);
            RequestQueue queue = Volley.newRequestQueue(AlterGroupActivity.this);
            queue.add(deleteGroupRequest);
         }



    class GroupListBackgroundTask extends AsyncTask<Void, Void, String> {
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
            Intent intent = new Intent(AlterGroupActivity.this, GroupActivity.class);
            intent.putExtra("groupList", result);
            intent.putExtra("loginUserid", loginUserid);
            intent.putExtra("type", "GroupList");
            AlterGroupActivity.this.startActivity(intent);
        }
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
}