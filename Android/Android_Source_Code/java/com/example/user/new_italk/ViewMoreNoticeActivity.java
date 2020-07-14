package com.example.user.new_italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Adapter.CommentListAdapter;
import com.example.user.new_italk.JAVA_Bean.Notice;
import com.example.user.new_italk.JAVA_Bean.NoticeComment;
import com.example.user.new_italk.Request.RegisterCommentRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewMoreNoticeActivity extends AppCompatActivity {

    String noticeNum, noticeTitle, noticeClassification, noticeContent, noticeWriter, noticeDate, loginUserId, loginUserName, loginUserIdentity, repliedCommentNum;

    TextView noticeWriterTextView, noticeDateTextView, noticeClassificationTextView, noticeTitleTextView, noticeContentTextView;
    ImageView cancelButton;
    EditText commentEditText;
    ImageView enrollmentImageView;
    LinearLayout viewCommentLinearLayout;

    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more_notice);
        Intent intent = getIntent();

        // NoticeComment ListView must initialize here but Not Yet, it needs BackgroundTask and Table, Bean, PHP Source.

        noticeNum = intent.getStringExtra("noticeNum");
        noticeTitle = intent.getStringExtra("noticeTitle");
        noticeClassification = intent.getStringExtra("noticeClassification");
        noticeContent = intent.getStringExtra("noticeContent");
        noticeWriter = intent.getStringExtra("noticeWriter");
        noticeDate = intent.getStringExtra("noticeDate");
        loginUserId = intent.getStringExtra("loginUserId");
        loginUserName = intent.getStringExtra("loginUserName");
        loginUserIdentity = intent.getStringExtra("loginUserIdentity");

        noticeWriterTextView = (TextView)findViewById(R.id.noticeWriterTextView);
        noticeDateTextView = (TextView)findViewById(R.id.noticeDateTextView);
        noticeClassificationTextView = (TextView)findViewById(R.id.noticeClassificationTextView);
        noticeTitleTextView = (TextView)findViewById(R.id.noticeTitleTextView);
        noticeContentTextView = (TextView)findViewById(R.id.noticeContentTextView);
        cancelButton = (ImageView)findViewById(R.id.cancelButton);
        commentEditText = (EditText)findViewById(R.id.commentEditText);
        enrollmentImageView = (ImageView)findViewById(R.id.enrollmentImageView);
        viewCommentLinearLayout = (LinearLayout)findViewById(R.id.viewCommentTextView);


        noticeWriterTextView.setText(noticeWriter);
        noticeDateTextView.setText(noticeDate);
        noticeClassificationTextView.setText("[ " + noticeClassification + " ]");
        noticeTitleTextView.setText(noticeTitle);
        noticeContentTextView.setText(noticeContent);

        viewCommentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewCommentIntent = new Intent(ViewMoreNoticeActivity.this, ViewCommentActivity.class);
                viewCommentIntent.putExtra("loginUserId", loginUserId);
                viewCommentIntent.putExtra("loginUserName", loginUserName);
                viewCommentIntent.putExtra("noticeNum", noticeNum);
                ViewMoreNoticeActivity.this.startActivity(viewCommentIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
