package com.example.user.new_italk;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Adapter.InquiryListAdapter;
import com.example.user.new_italk.Adapter.NoticeListAdapter;
import com.example.user.new_italk.Adapter.UserListAdapter;
import com.example.user.new_italk.JAVA_Bean.Inquiry;
import com.example.user.new_italk.JAVA_Bean.Notice;
import com.example.user.new_italk.JAVA_Bean.User;
import com.example.user.new_italk.Request.DeleteNoticeRequest;
import com.example.user.new_italk.Request.FindUserInfoRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String loginUserId, loginUserName, loginUserIdentity, loginUserGrade, loginUserMajor, userid1, userid2, username1, username2;
    int inquiryNum;
    LinearLayout notificationLinearLayout, profileLinearLayout, UserListLinearLayout,inquiryListLinearLayout ;

    AlertDialog dialog;

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    private List<Notice> saveList;

    private ListView userListView;
    private List<User> userList;
    private List<User> userSaveList;
    private UserListAdapter userAdapter;

    private ListView inquiryListView;
    private List<Inquiry> inquiryList;
    private List<Inquiry> inquirySaveList;
    private InquiryListAdapter inquiryAdapter;


    Switch myNoticeToggleSwitch;
    EditText searchNoticeEditText;
    TextView addNoticeTextView;
    Spinner searchByClassificationSpinner;
    ArrayAdapter searchByClassificationAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_people:
                    UserListLinearLayout.setVisibility(View.VISIBLE);
                    notificationLinearLayout.setVisibility(View.GONE);
                    profileLinearLayout.setVisibility(View.GONE);
                    inquiryListLinearLayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_inquiry:
                    UserListLinearLayout.setVisibility(View.GONE);
                    notificationLinearLayout.setVisibility(View.GONE);
                    profileLinearLayout.setVisibility(View.GONE);
                    inquiryListLinearLayout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    UserListLinearLayout.setVisibility(View.GONE);
                    //notificationResume();
                    notificationLinearLayout.setVisibility(View.VISIBLE);
                    profileLinearLayout.setVisibility(View.GONE);
                    inquiryListLinearLayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_setting:
                    UserListLinearLayout.setVisibility(View.GONE);
                    notificationLinearLayout.setVisibility(View.GONE);
                    profileLinearLayout.setVisibility(View.VISIBLE);
                    inquiryListLinearLayout.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };
    public void notificationResume() {
        //userList.clear();
        //new UserListBackgroundTask().execute();
        new InquiryBackgroundTask().execute();
        inquiryAdapter.notifyDataSetChanged();
        //userAdapter.notifyDataSetChanged();
        //inquiryAdapter.notifyDataSetChanged();
        new NoticeBackgroundTask().execute();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // LoginActivity에서 받아온 StringExtra 받아오는 부분
        Intent intent = getIntent();
        loginUserId = intent.getStringExtra("userId");
        loginUserName = intent.getStringExtra("userName");
        loginUserIdentity = intent.getStringExtra("userIdentity");
        loginUserGrade = intent.getStringExtra("userGrade");
        loginUserMajor = intent.getStringExtra("userMajor");




        addNoticeTextView = (TextView)findViewById(R.id.addNoticeTextView);
        addNoticeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNoticeIntent = new Intent(MainActivity.this, AddNoticeActivity.class);
                addNoticeIntent.putExtra("userId", loginUserId);
                addNoticeIntent.putExtra("userName", loginUserName);
                addNoticeIntent.putExtra("callBy", "add");
                MainActivity.this.startActivity(addNoticeIntent);
            }
        });


        searchByClassificationSpinner = (Spinner)findViewById(R.id.searchByClassificationSpinner);
        searchByClassificationAdapter = ArrayAdapter.createFromResource(this, R.array.searchNoticeClassification, R.layout.support_simple_spinner_dropdown_item);
        searchByClassificationSpinner.setAdapter(searchByClassificationAdapter);
        searchByClassificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Notice tempNotice;
                noticeList.clear();
                if (i==0) {
                    noticeList.addAll(saveList);
                    adapter.notifyDataSetChanged();
                }
                else {
                    for(int k=0; k<saveList.size(); k++) {
                        tempNotice = saveList.get(k);
                        if(tempNotice.getClassification().equals(searchByClassificationSpinner.getSelectedItem())) {
                            noticeList.add(tempNotice);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
























        //유저목록 페이지

        new UserListBackgroundTask().execute();

        UserListLinearLayout = (LinearLayout)findViewById(R.id.UserListLinearLayout);
        userListView = (ListView)findViewById(R.id.userListView);
        userList = new ArrayList<User>();
        userSaveList = new ArrayList<User>();
        userAdapter = new UserListAdapter(getApplicationContext(),userList, userSaveList, loginUserIdentity, loginUserId);
        userListView.setAdapter(userAdapter);
        final EditText userSearch = (EditText)findViewById(R.id.userSearch);


        userSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    searchUser(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String userid = userList.get(position).getUserid();
                final String username = userList.get(position).getUsername();
                final String userphonenum = userList.get(position).getUserphonenum();
                final String useridentity = userList.get(position).getUseridentity();

                if(!loginUserIdentity.equals("학생")){
                    final List<String> listItems = new ArrayList<>();
                    listItems.add("사용자 관리");
                    listItems.add("전화 걸기");
                    listItems.add("채팅 걸기");
                    final CharSequence[] items = listItems.toArray(new String[listItems.size()]);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    dialog = builder.setTitle(username)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            Response.Listener<String> reponseListener = new Response.Listener<String>() {


                                                @Override
                                                public void onResponse(String response) {
                                                    try{
                                                        JSONObject jsonResponse = new JSONObject(response);
                                                        boolean success = jsonResponse.getBoolean("success");
                                                        if(success){
                                                            String userid = jsonResponse.getString("userid");
                                                            //String userpassword = jsonResponse.getString("pw");
                                                            String username = jsonResponse.getString("username");
                                                            String userphonenum = jsonResponse.getString("userphonenum");
                                                            String useremail = jsonResponse.getString("useremail");
                                                            String usergrade = jsonResponse.getString("usergrade");
                                                            String usermajor = jsonResponse.getString("usermajor");
                                                            String userstate = jsonResponse.getString("userstate");
                                                            Intent userInfoIntent = new Intent(MainActivity.this, UserInfoActivity.class);
                                                            userInfoIntent.putExtra("authority", "userManagement");
                                                            userInfoIntent.putExtra("loginUserid", loginUserId);
                                                            userInfoIntent.putExtra("userid", userid);
                                                            // userInfoIntent.putExtra("userpassword", userpassword);
                                                            userInfoIntent.putExtra("username", username);
                                                            userInfoIntent.putExtra("userphonenum", userphonenum);
                                                            userInfoIntent.putExtra("useremail", useremail);
                                                            userInfoIntent.putExtra("usergrade", usergrade);
                                                            userInfoIntent.putExtra("usermajor", usermajor);
                                                            userInfoIntent.putExtra("useridentity", useridentity);
                                                            userInfoIntent.putExtra("userstate", userstate);
                                                            MainActivity.this.startActivity(userInfoIntent);
                                                        }
                                                        else{
                                                            AlertDialog.Builder bulilder = new AlertDialog.Builder(MainActivity.this);
                                                            dialog = bulilder.setMessage("정보 조회에 실패하였습니다.")
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
                                            FindUserInfoRequest findUserInfoRequest = new FindUserInfoRequest(userid, reponseListener);
                                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                            queue.add(findUserInfoRequest);

                                            break;
                                        case 1:
                                            /*if (userphonenum.length() == 8) {
                                               userphonenum.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
                                                } else if (userphonenum.length() == 12) {
                                                userphonenum.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
                                                   }
                                                userphonenum.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");*/

                                            String phonenum = "tel:" + userphonenum;
                                            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(phonenum));
                                            MainActivity.this.startActivity(intent);
                                            break;
                                        case 2:
                                            userid1 = loginUserId;
                                            userid2 = userid;
                                            username1 = loginUserName;
                                            username2 = username;
                                            new CreateChatBackgroundTask().execute();
                                            break;
                                    }
                                }
                            })
                            .create();
                    dialog.show();
                } else{
                    final List<String> listItems = new ArrayList<>();
                    listItems.add("전화 문의");
                    listItems.add("채팅 문의");
                    final CharSequence[] items = listItems.toArray(new String[listItems.size()]);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    dialog = builder.setTitle(username)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            /*if (userphonenum.length() == 8) {
                                               userphonenum.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
                                                } else if (userphonenum.length() == 12) {
                                                userphonenum.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
                                                   }
                                                userphonenum.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");*/

                                            String phonenum = "tel:" + userphonenum;
                                            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(phonenum));
                                            MainActivity.this.startActivity(intent);
                                            break;
                                        case 1:
                                            userid1 = loginUserId;
                                            userid2 = userid;
                                            username1 = loginUserName;
                                            username2 = username;
                                            new CreateChatBackgroundTask().execute();
                                            break;
                                    }
                                }
                            })
                            .create();
                dialog.show();
                }
            }
        });


        //채팅목록 페이지
        String type = "inquiry";
        inquiryListLinearLayout = (LinearLayout)findViewById(R.id.inquiryListLinearLayout);
        inquiryListView = (ListView)findViewById(R.id.inquiryListView);
        inquiryList = new ArrayList<Inquiry>();
        inquirySaveList = new ArrayList<Inquiry>();
        inquiryAdapter = new InquiryListAdapter(getApplicationContext(), inquiryList, inquirySaveList, loginUserName, type);
        inquiryListView.setAdapter(inquiryAdapter);

        ImageView inquirySetting = (ImageView)findViewById(R.id.inquirySetting);

        inquirySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<String> listItems = new ArrayList<>();
                listItems.add("채팅방 생성");
                listItems.add("편집");
                final CharSequence[] items = listItems.toArray(new String[listItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                dialog = builder.setTitle("채팅목록 설정")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0:
                                        break;
                                    case 1:
                                        new SettingInquiryBackgroundTask().execute();
                                        break;
                                }
                            }
                        }).create();
                dialog.show();
            }
        });

        new InquiryBackgroundTask().execute();

        EditText inquirySearch = (EditText)findViewById(R.id.inquirySearch);

        inquirySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchInquiry(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        inquiryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                inquiryNum = inquiryList.get(i).getInquiryNum();
                username1 = inquiryList.get( i ).getUser1();
                username2 = inquiryList.get( i ).getUser2();
                new ChatBackgroundTask().execute(  );

            }


        });



        // Activity_main.xml  공지사항 페이지
        notificationLinearLayout = (LinearLayout)findViewById(R.id.notificationLinearLayout);
        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        saveList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList, saveList);
        noticeListView.setAdapter(adapter);


        noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent viewAllIntent = new Intent(MainActivity.this, ViewMoreNoticeActivity.class);
                viewAllIntent.putExtra("noticeNum", noticeList.get(position).getNum());
                viewAllIntent.putExtra("noticeClassification", noticeList.get(position).getClassification());
                viewAllIntent.putExtra("noticeTitle", noticeList.get(position).getTitle());
                viewAllIntent.putExtra("noticeContent", noticeList.get(position).getContent());
                viewAllIntent.putExtra("noticeWriter", noticeList.get(position).getWriter());
                viewAllIntent.putExtra("noticeDate" , noticeList.get(position).getDate());
                viewAllIntent.putExtra("loginUserId", loginUserId);
                viewAllIntent.putExtra("loginUserName", loginUserName);
                viewAllIntent.putExtra("loginUserIdentity", loginUserIdentity);
                MainActivity.this.startActivity(viewAllIntent);
            }
        });

        noticeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int position, long l) {
                Notice notice = noticeList.get(position);
                final String noticeNum, noticeTitle, noticeContent, noticeWriter, noticeDate, noticeTopFixed, noticeClassification;

                noticeNum = notice.getNum();
                noticeClassification = notice.getClassification();
                noticeTitle = notice.getTitle();
                noticeContent = notice.getContent();
                noticeWriter = notice.getWriter();
                noticeDate = notice.getDate();
                noticeTopFixed = notice.getTopFixed();

                final List<String> listItems = new ArrayList<>();
                listItems.add("수정");
                listItems.add("삭제");
                final CharSequence[] items = listItems.toArray(new String[listItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                dialog = builder.setTitle(noticeTitle)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        Intent alterNoticeIntent = new Intent(MainActivity.this, AddNoticeActivity.class);
                                        alterNoticeIntent.putExtra("noticeNum", noticeNum);
                                        alterNoticeIntent.putExtra("noticeClassification", noticeClassification);
                                        alterNoticeIntent.putExtra("noticeTitle", noticeTitle);
                                        alterNoticeIntent.putExtra("noticeContent", noticeContent);
                                        alterNoticeIntent.putExtra("noticeWriter", noticeWriter);
                                        alterNoticeIntent.putExtra("noticeDate" , noticeDate);
                                        alterNoticeIntent.putExtra("noticeTopFixed", noticeTopFixed);
                                        alterNoticeIntent.putExtra("callBy", "alter");
                                        MainActivity.this.startActivity(alterNoticeIntent);
                                        break;
                                    case 1:
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        dialog = builder.setMessage("해당 공지를 정말 삭제하시겠습니까?")
                                                .setNegativeButton("취소", null)
                                                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    JSONObject jsonResponse = new JSONObject(response);
                                                                    boolean success = jsonResponse.getBoolean(response);
                                                                }
                                                                catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        };
                                                        DeleteNoticeRequest deleteNoticeRequest = new DeleteNoticeRequest(noticeNum, responseListener);
                                                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                                        queue.add(deleteNoticeRequest);
                                                        noticeList.remove(position);
                                                        saveList.remove(position);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                })
                                                .create();
                                        dialog.show();
                                        break;
                                }
                            }
                        })
                        .create();
                dialog.show();
                return false;
            }
        });

        myNoticeToggleSwitch = (Switch)findViewById(R.id.myNoticeToggleSwitch);
        myNoticeToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {
                if(switchOn) {
                    if(loginUserIdentity.equals("교직원")||loginUserIdentity.equals("교수")) {
                        noticeList.clear();
                        for(int i=0; i<saveList.size(); i++) {
                            if(saveList.get(i).getWriter().equals(loginUserId))
                                noticeList.add(saveList.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    noticeList.clear();
                    noticeList.addAll(saveList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        searchNoticeEditText = (EditText)findViewById(R.id.searchNoticeEditText);
        searchNoticeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                searchNotice(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        new NoticeBackgroundTask().execute();



        //프로필 페이지
        profileLinearLayout = (LinearLayout)findViewById(R.id.profileLinearLayout);
        LinearLayout lay = (LinearLayout)findViewById(R.id.lay);
        TextView profilText = (TextView)findViewById(R.id.profilText);
        final TextView idText = (TextView)findViewById(R.id.idText);
        TextView idTextView = (TextView)findViewById(R.id.idTextView);
        TextView nameText = (TextView)findViewById(R.id.nameText);
        TextView usergradeText = (TextView)findViewById(R.id.usergradeText);
        TextView gradeTextView = (TextView)findViewById(R.id.gradeTextView);
        TextView majorText = (TextView)findViewById(R.id.majorText);
        TextView logout = (TextView)findViewById(R.id.logout);
        ImageView userInfoImageView = (ImageView)findViewById(R.id.userInfoImageView);

        TextView createGroup = (TextView)findViewById(R.id.createGroup);
        TextView selectMyGroup = (TextView)findViewById(R.id.selectMyGroup);
        TextView call1 = (TextView)findViewById(R.id.call1);
        TextView call2 = (TextView)findViewById(R.id.call2);
        TextView call3 = (TextView)findViewById(R.id.call3);
        TextView call4 = (TextView)findViewById(R.id.call4);
        TextView link1 = (TextView)findViewById(R.id.link1);
        TextView link2 = (TextView)findViewById(R.id.link2);
        TextView link3 = (TextView)findViewById(R.id.link3);
        TextView link4 = (TextView)findViewById(R.id.link4);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder bulilder = new AlertDialog.Builder(MainActivity.this);
                dialog = bulilder.setMessage("로그아웃을 하시면 공지사항이 누락될 수 있습니다. 정말로 로그아웃 하시겠습니까?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = auto.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                MainActivity.this.startActivity(intent);
                                finish();
                            }
                        })
                        .create();
                dialog.show();

            }
        });

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.shinhan.ac.kr/"));
                MainActivity.this.startActivity(intent);
            }
        });
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://stins.shinhan.ac.kr/irj/portal"));
                MainActivity.this.startActivity(intent);
            }
        });
        link3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://ce.shinhan.ac.kr/cse/"));
                MainActivity.this.startActivity(intent);
            }
        });
        link4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://electronics.shinhan.ac.kr/electronic/"));
                MainActivity.this.startActivity(intent);
            }
        });


        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "0318701740";
                String phonenum = "tel:" + num;
                Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(phonenum));
                MainActivity.this.startActivity(intent);
            }
        });
        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "0318701730";
                String phonenum = "tel:" + num;
                Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(phonenum));
                MainActivity.this.startActivity(intent);
            }
        });
        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "031870";
                String phonenum = "tel:" + num;
                Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(phonenum));
                MainActivity.this.startActivity(intent);
            }
        });
        call4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "0318702919";
                String phonenum = "tel:" + num;
                Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(phonenum));
                MainActivity.this.startActivity(intent);
            }
        });


        idText.setText(loginUserId);
        nameText.setText(loginUserName);
        profilText.setText("< I.Talk " + loginUserIdentity + " 프로필 >");
        usergradeText.setText(loginUserGrade+"학년");
        majorText.setText(loginUserMajor);

        if(loginUserIdentity.equals("학생")){
            lay.setVisibility(View.GONE);
        }

        if(!loginUserIdentity.equals("학생")){
            usergradeText.setVisibility(View.GONE);
            gradeTextView.setVisibility(View.GONE);
            idTextView.setText("사번 : ");
        }

        userInfoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String findUserInfoID = idText.getText().toString();

                Response.Listener<String> reponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String userid = jsonResponse.getString("userid");
                                //String userpassword = jsonResponse.getString("pw");
                                String username = jsonResponse.getString("username");
                                String userphonenum = jsonResponse.getString("userphonenum");
                                String useremail = jsonResponse.getString("useremail");
                                String usergrade = jsonResponse.getString("usergrade");
                                String usermajor = jsonResponse.getString("usermajor");
                                String userstate = jsonResponse.getString("userstate");
                                Intent userInfoIntent = new Intent(MainActivity.this, UserInfoActivity.class);
                                userInfoIntent.putExtra("authority", "profile");
                                userInfoIntent.putExtra("userid", userid);
                                // userInfoIntent.putExtra("userpassword", userpassword);
                                userInfoIntent.putExtra("username", username);
                                userInfoIntent.putExtra("userphonenum", userphonenum);
                                userInfoIntent.putExtra("useremail", useremail);
                                userInfoIntent.putExtra("usergrade", usergrade);
                                userInfoIntent.putExtra("usermajor", usermajor);
                                userInfoIntent.putExtra("useridentity", loginUserIdentity);
                                userInfoIntent.putExtra("userstate", userstate);
                                MainActivity.this.startActivity(userInfoIntent);
                            }
                            else{
                                AlertDialog.Builder bulilder = new AlertDialog.Builder(MainActivity.this);
                                dialog = bulilder.setMessage("정보 조회에 실패하였습니다.")
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
                FindUserInfoRequest findUserInfoRequest = new FindUserInfoRequest(findUserInfoID, reponseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(findUserInfoRequest);
            }
        });

        selectMyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GroupListBackgroundTask().execute();

            }
        });

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });
    }

    class ChatBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/Chat.php?inquiryNum=" + inquiryNum ;
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
        public void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra( "Chat", result );
            //intent.putExtra("inquiryNum", inquiryNum);
            intent.putExtra("loginUserid", loginUserId);
            intent.putExtra("loginUsername", loginUserName);
            intent.putExtra( "user1", username1 );
            intent.putExtra( "user2", username2 );
            MainActivity.this.startActivity(intent);

        }
    }

    class CreateChatBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://tkdl2401.cafe24.com/CreateChat.php?user1=" + URLEncoder.encode(userid1, "UTF-8")+ "&user2=" + URLEncoder.encode(userid2, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
        public void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra( "Chat", result );
            //intent.putExtra("inquiryNum", inquiryNum);
            intent.putExtra("loginUserid", loginUserId);
            intent.putExtra("loginUsername", loginUserName);
            intent.putExtra( "user1", username1 );
            intent.putExtra( "user2", username2 );
            MainActivity.this.startActivity(intent);

        }
    }



    class InquiryBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/InquiryList.php";
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
            super.onProgressUpdate();

        }

        @Override
        public void onPostExecute(String result) {


            try {
                JSONObject jsonObject = new JSONObject(result);
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
        }
    }

    class SettingInquiryBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/InquiryList.php";
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
            super.onProgressUpdate();

        }

        @Override
        public void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, SettingInquiryActivity.class);
            intent.putExtra( "inquiryList", result );
            intent.putExtra("loginUserid", loginUserId);
            intent.putExtra("loginUsername", loginUserName);
            intent.putExtra( "type", "settingInquiry" );
            MainActivity.this.startActivity(intent);
        }
    }


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
            super.onProgressUpdate();

        }

        @Override
        public void onPostExecute(String result) {


            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                userList.clear();
                userSaveList.clear();
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    String userid = object.getString("userid");
                    String username = object.getString("username");
                    String usergrade = object.getString("usergrade");
                    String usermajor = object.getString("usermajor");
                    String useridentity = object.getString("useridentity");
                    String userphonenum = object.getString("userphonenum");
                    String useremail = object.getString("useremail");

                    if(loginUserIdentity.equals("학생")){
                        if (!(userid.equals(loginUserId)) && !(useridentity.equals("학생"))){
                            User user = new User(userid, username, usergrade, usermajor, useridentity, userphonenum, useremail);
                            userList.add(user);
                            userSaveList.add(user);
                        }
                    }

                    else {
                        if (!(userid.equals(loginUserId))) {
                            User user = new User(userid, username, usergrade, usermajor, useridentity, userphonenum, useremail);
                            userList.add(user);
                            userSaveList.add(user);
                        }
                    }
                    count++;
                }
                userAdapter.notifyDataSetChanged();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class GroupListBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/GroupList.php";
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
            Intent intent = new Intent(MainActivity.this, GroupActivity.class);
            intent.putExtra("groupList", result);
            intent.putExtra("loginUserid", loginUserId);
            intent.putExtra("type", "GroupList");
            MainActivity.this.startActivity(intent);
        }
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
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
            Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
            intent.putExtra("userList", result);
            intent.putExtra("loginUserid", loginUserId);
            intent.putExtra("type", "b");
            MainActivity.this.startActivity(intent);
        }
    }


    class NoticeBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/noticeList.php";
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
            super.onProgressUpdate();

        }
        @Override
        public  void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String num, classification, title, content, writer, date, topFixed;
                noticeList.clear();
                saveList.clear();
                if(searchByClassificationSpinner.getSelectedItem().equals("전체")) {
                    adapter.notifyDataSetChanged();
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        num = object.getString("NoticeNum");
                        classification = object.getString("NoticeClassification");
                        title = object.getString("NoticeTitle");
                        content = object.getString("NoticeContent");
                        writer = object.getString("NoticeWriter");
                        date = object.getString("NoticeDate");
                        topFixed = object.getString("TopFixed");
                        Notice notice = new Notice(num, classification, title, content, writer, date, topFixed);
                        noticeList.add(notice);
                        saveList.add(notice);
                        count++;
                    }
                }
                else {
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        num = object.getString("NoticeNum");
                        classification = object.getString("NoticeClassification");
                        title = object.getString("NoticeTitle");
                        content = object.getString("NoticeContent");
                        writer = object.getString("NoticeWriter");
                        date = object.getString("NoticeDate");
                        topFixed = object.getString("TopFixed");
                        Notice notice = new Notice(num, classification, title, content, writer, date, topFixed);
                        if(classification.equals(searchByClassificationSpinner.getSelectedItem())) {
                            noticeList.add(notice);
                        }
                        saveList.add(notice);
                        count++;
                    }
                }
                adapter.notifyDataSetChanged();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void searchNotice(String searchText) {
        noticeList.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if(searchByClassificationSpinner.getSelectedItem().equals("전체")) {
                if(saveList.get(i).getTitle().contains(searchText)||saveList.get(i).getContent().contains(searchText))
                    noticeList.add(saveList.get(i));
            }
            else {
                if (((saveList.get(i).getTitle().contains(searchText))||saveList.get(i).getContent().contains(searchText))&&searchByClassificationSpinner.getSelectedItem().equals(saveList.get(i).getClassification())) {
                    noticeList.add(saveList.get(i));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void searchUser(String searchText){
        userList.clear();
        for (int i = 0; i < userSaveList.size(); i++) {
            if (userSaveList.get(i).getUserid().contains(searchText) || userSaveList.get(i).getUsername().contains(searchText)){
                userList.add(userSaveList.get(i));
            }
            userAdapter.notifyDataSetChanged();
        }
    }
    public void searchInquiry(String searchText){
        inquiryList.clear();
        for (int i = 0; i < inquirySaveList.size(); i++) {
            if (inquirySaveList.get(i).getUser1().contains(searchText) || inquirySaveList.get(i).getUser2().contains(searchText)){
                inquiryList.add(inquirySaveList.get(i));
            }
            inquiryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        notificationResume();
    }
}