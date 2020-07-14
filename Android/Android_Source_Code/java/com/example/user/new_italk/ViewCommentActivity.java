package com.example.user.new_italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Adapter.CommentListAdapter;
import com.example.user.new_italk.JAVA_Bean.NoticeComment;
import com.example.user.new_italk.Request.RegisterCommentRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewCommentActivity extends AppCompatActivity {
    public String loginUserId, loginUserName, noticeNum, repliedCommentNum;
    EditText commentEditText;
    ImageView enrollmentImageView, cancelButton;
    AlertDialog dialog;

    private ListView commentListView;
    private CommentListAdapter commentListAdapter;
    private List<NoticeComment> commentList;
    private List<NoticeComment> replyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment);

        Intent viewCommentIntent = getIntent();
        loginUserId = viewCommentIntent.getStringExtra("loginUserId");
        loginUserName = viewCommentIntent.getStringExtra("loginUserName");
        noticeNum = viewCommentIntent.getStringExtra("noticeNum");

        cancelButton = (ImageView)findViewById(R.id.cancelButton);
        commentEditText = (EditText)findViewById(R.id.commentEditText);
        enrollmentImageView = (ImageView)findViewById(R.id.enrollmentImageView);
        commentListView = (ListView)findViewById(R.id.commentListView);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        enrollmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String comment = commentEditText.getText().toString();
                long mNow = System.currentTimeMillis();
                Date mDate = new Date(mNow);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
                final String writtenDateTime = sdf.format(mDate);
                repliedCommentNum = "NULL";

                if(comment.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewCommentActivity.this);
                    dialog = builder.setMessage("입력된 내용이 없습니다.")
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
                            if(success) {
                                NoticeComment tempComment = new NoticeComment("comment", "temp", noticeNum, comment, loginUserId,
                                        loginUserName, writtenDateTime, repliedCommentNum);
                                commentList.add(tempComment);
                                commentListAdapter.notifyDataSetChanged();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ViewCommentActivity.this);
                                builder.setMessage("댓글 등록에 실패했습니다.")
                                        .setPositiveButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest("comment", noticeNum, comment, loginUserId, loginUserName, writtenDateTime, repliedCommentNum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ViewCommentActivity.this);
                queue.add(registerCommentRequest);
                commentEditText.setText("");

                new CommentBackgroundTask().execute();
            }
        });
        commentList = new ArrayList<NoticeComment>();
        replyList = new ArrayList<NoticeComment>();
        commentListAdapter = new CommentListAdapter(getApplicationContext(), commentList, replyList, loginUserId, loginUserName, noticeNum);

        commentListView.setAdapter(commentListAdapter);

        /*
        commentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(commentList.get(i).getCommentNum().equals("temp")) {
                    new CommentBackgroundTask().execute();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewCommentActivity.this);
                dialog = builder.setMessage(commentList.get(i).getCommentNum())
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        */

        new CommentBackgroundTask().execute();
        commentListAdapter.notifyDataSetChanged();
    }




    class CommentBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        @Override
        protected void onPreExecute() {
            target = "http://tkdl2401.cafe24.com/loadComment.php?noticeNum=" + noticeNum;
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
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String type, commentNum, noticeNum, commentContent, commentWriterId, commentWriterName, writtenDateTime, repliedCommentNum;
                commentList.clear();
                replyList.clear();
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    type = object.getString("Type");
                    commentNum = object.getString("CommentNum");
                    noticeNum = object.getString("NoticeNum");
                    commentContent = object.getString("Comment");
                    commentWriterId = object.getString("CommentWriterId");
                    commentWriterName = object.getString("CommentWriterName");
                    writtenDateTime = object.getString("WrittenDateTime");
                    repliedCommentNum = object.getString("RepliedCommentNum");

                    NoticeComment comment = new NoticeComment(type, commentNum, noticeNum, commentContent, commentWriterId,
                            commentWriterName, writtenDateTime, repliedCommentNum);
                    if(type.equals("comment")) {
                        commentList.add(comment);
                    }
                    else if(type.equals("reply")) {
                        replyList.add(comment);
                    }
                    count++;
                }
                for(int i=0; i<replyList.size()-1; i++) {
                    NoticeComment temp = replyList.get(i);
                    if(Integer.parseInt(replyList.get(i).getCommentNum())>Integer.parseInt(replyList.get(i+1).getCommentNum())) {
                        replyList.remove(i);
                        replyList.add(i, replyList.get(i+1));
                        replyList.add(i+1, temp);
                    }
                }
                for(int i=replyList.size()-1; i>=0; i--) {
                    NoticeComment tempComment = replyList.get(i);
                    for(int j=0; j<commentList.size(); j++) {
                        if(commentList.get(j).getCommentNum().equals(tempComment.getRepliedCommentNum())) {
                            commentList.add(j+1, tempComment);
                        }
                    }
                }
                commentListAdapter.notifyDataSetChanged();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
