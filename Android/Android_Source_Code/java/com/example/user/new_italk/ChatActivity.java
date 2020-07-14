package com.example.user.new_italk;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Adapter.ChatListAdapter;
import com.example.user.new_italk.JAVA_Bean.Chat;
import com.example.user.new_italk.Request.AddChatRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private  TextView otherUser;
    private ImageView addContentButton;
    private EditText contentEditText;
    private  String loginUserid, loginUsername, user1, user2;
    private  int inquiryNum;
    private ListView ChatListView;
    private ChatListAdapter adapter;
    private List<Chat> ChatList;
    private List<Chat> ChatsaveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        //inquiryNum = intent.getIntExtra( "inquiryNum", 0 );
        loginUserid = intent.getStringExtra("loginUserid");
        loginUsername = intent.getStringExtra("loginUsername");
        user1 = intent.getStringExtra( "user1" );
        user2 = intent.getStringExtra( "user2" );


        otherUser = (TextView)findViewById(R.id.otherUser);
        addContentButton = (ImageView)findViewById( R.id.addContentButton );
        contentEditText = (EditText) findViewById( R.id.contentEditText );
        ImageView cancelButton = (ImageView)findViewById(R.id.cancelButton);

        addContentButton.setEnabled(false);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(loginUsername.equals( user1 )){
           otherUser.setText( user2 );
        }
        if(loginUsername.equals( user2 )){
            otherUser.setText( user1 );
        }


        ChatListView = (ListView) findViewById(R.id.ChatListView);
        ChatList = new ArrayList<Chat>();
        ChatsaveList = new ArrayList<Chat>();



        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("Chat"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                inquiryNum = object.getInt("inquiryNum");
                String sender = object.getString("sender");
                String comment = object.getString("comment");
                Date commentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(object.getString("commentDateTime"));
                if(!(comment.equals("createChatComment"))) {
                    Chat chat = new Chat(sender, comment, commentDateTime);
                    ChatList.add(chat);
                    ChatsaveList.add(chat);
                }
                count++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        adapter = new ChatListAdapter(getApplicationContext(), ChatList, ChatsaveList, loginUsername);
        ChatListView.setAdapter(adapter);
        ChatListView.setSelection(adapter.getCount() - 1);

        contentEditText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = contentEditText.getText().toString();
                if(text.equals( "" )){
                    addContentButton.setEnabled( false );
                    return;
                }else{
                    addContentButton.setEnabled( true );
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

        addContentButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    final String comment = contentEditText.getText().toString();
                    long now = System.currentTimeMillis();
                    final Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String getTime = sdf.format(date);
                    Date commentDateTime = null;
                try { 
                    commentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final Date finalCommentDateTime = commentDateTime;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Chat chat = new Chat( loginUsername, comment, finalCommentDateTime);
                                ChatList.add(chat);
                                adapter.notifyDataSetChanged();
                                ChatListView.setSelection(adapter.getCount() - 1);
                                contentEditText.setText( "" );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddChatRequest addChatRequest = new AddChatRequest( inquiryNum , loginUserid, comment,  responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
                queue.add(addChatRequest);
            }
        } );

    }


}
