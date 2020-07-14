package com.example.user.new_italk.Adapter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.JAVA_Bean.NoticeComment;
import com.example.user.new_italk.MainActivity;
import com.example.user.new_italk.R;
import com.example.user.new_italk.Request.AlterCommentRequest;
import com.example.user.new_italk.Request.DeleteCommentRequest;
import com.example.user.new_italk.Request.DeleteNoticeRequest;
import com.example.user.new_italk.Request.RegisterCommentRequest;
import com.example.user.new_italk.ViewCommentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private List<NoticeComment> commentList;
    private List<NoticeComment> replyList;
    private String loginUserId, loginUserName;
    private String noticeNum;

    public CommentListAdapter(Context context, List<NoticeComment> commentList, List<NoticeComment> replyList, String loginUserId, String loginUserName, String noticeNum) {
        this.context = context;
        this.commentList = commentList;
        this.replyList = replyList;
        this.loginUserId = loginUserId;
        this.loginUserName = loginUserName;
        this.noticeNum = noticeNum;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view.inflate(context, R.layout.comment_sample, null);

        final TextView commentWriterIdTextView = (TextView)v.findViewById(R.id.commentWriterIdTextView);
        final TextView commentWriterNameTextView = (TextView)v.findViewById(R.id.commentWriterNameTextView);
        final TextView writtenDateTimeTextView = (TextView)v.findViewById(R.id.writtenDateTimeTextView);
        final TextView commentTextView = (TextView)v.findViewById(R.id.commentTextView);
        final TextView alterCommentTextView = (TextView)v.findViewById(R.id.alterCommentTextView);
        final TextView deleteCommentTextView = (TextView)v.findViewById(R.id.deleteCommentTextView);
        final TextView enrollReplyTextView = (TextView)v.findViewById(R.id.enrollReplyTextView);
        final LinearLayout replyLinearLayout = (LinearLayout)v.findViewById(R.id.replyLinearLayout);
        final EditText replyEditText = (EditText)v.findViewById(R.id.replyEditText);
        final ImageView enrollReplyImageView = (ImageView)v.findViewById(R.id.enrollReplyImageView);
        final ImageView replyImageView = (ImageView)v.findViewById(R.id.replyImageView);
        String type;

        alterCommentTextView.setVisibility(View.GONE);
        deleteCommentTextView.setVisibility(View.GONE);

        commentWriterIdTextView.setText(commentList.get(i).getCommentWriterId());
        commentWriterNameTextView.setText(commentList.get(i).getCommentWriterName());
        writtenDateTimeTextView.setText(commentList.get(i).getWrittenDateTime());
        commentTextView.setText(commentList.get(i).getComment());

        type = commentList.get(i).getType();
        if(type.equals("reply")) {
            enrollReplyTextView.setVisibility(View.GONE);
            replyImageView.setVisibility(View.VISIBLE);

        }

        if(loginUserId.equals(commentList.get(i).getCommentWriterId())) {
            alterCommentTextView.setVisibility(View.VISIBLE);
            deleteCommentTextView.setVisibility(View.VISIBLE);
        }

        enrollReplyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(commentList.get(i).getCommentNum().equals("temp")) {
                    new CommentBackgroundTask().execute();
                    return;
                }
                if(enrollReplyTextView.getText().toString().equals("답글 달기")) {
                    replyLinearLayout.setVisibility(View.VISIBLE);
                    enrollReplyTextView.setText("취소");
                    alterCommentTextView.setText("수정");
                    replyEditText.setHint("댓글을 입력하세요.");
                    replyEditText.setText("@"+ commentList.get(i).getCommentWriterId() + " " + commentList.get(i).getCommentWriterName());
                }
                else {
                    replyLinearLayout.setVisibility(View.GONE);
                    enrollReplyTextView.setText("답글 달기");
                    alterCommentTextView.setText("수정");
                }
            }
        });
        alterCommentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(commentList.get(i).getCommentNum().equals("temp")) {
                    new CommentBackgroundTask().execute();
                    return;
                }
                if(alterCommentTextView.getText().toString().equals("수정")) {
                    replyLinearLayout.setVisibility(View.VISIBLE);
                    alterCommentTextView.setText("취소");
                    enrollReplyTextView.setText("답글 달기");
                    replyEditText.setHint("수정할 댓글을 입력하세요.");
                    replyEditText.setText(commentList.get(i).getComment());
                }
                else {
                    replyLinearLayout.setVisibility(View.GONE);
                    alterCommentTextView.setText("수정");
                    enrollReplyTextView.setText("답글 달기");
                }
            }
        });

        enrollReplyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enrollReplyTextView.getText().toString().equals("취소")&&alterCommentTextView.getText().toString().equals("수정")) {
                    final String comment = replyEditText.getText().toString();

                    if(comment.equals("")) {
                        return;
                    }
                    else {
                        long mNow = System.currentTimeMillis();
                        Date mDate = new Date(mNow);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
                        final String writtenDateTime = sdf.format(mDate);
                        final String repliedCommentNum = commentList.get(i).getCommentNum();
                        final String noticeNum = commentList.get(i).getNoticeNum();
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success) {
                                        NoticeComment tempComment = new NoticeComment("reply", "temp", noticeNum, comment, loginUserId,
                                                loginUserName, writtenDateTime, repliedCommentNum);
                                        if(i==commentList.size()-1)
                                            commentList.add(tempComment);
                                        else {
                                            for(int j=i+1; j<commentList.size(); j++) {
                                                if(!commentList.get(j).getType().equals("reply")) {
                                                    commentList.add(j, tempComment);
                                                    break;
                                                }
                                                if(j==commentList.size()-1) {
                                                    commentList.add(tempComment);
                                                    break;
                                                }
                                            }
                                        }
                                        notifyDataSetChanged();
                                    }
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest("reply", noticeNum, comment, loginUserId, loginUserName, writtenDateTime, repliedCommentNum, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(registerCommentRequest);
                        new CommentBackgroundTask().execute();
                        replyEditText.setText("");
                    }
                }
                if(alterCommentTextView.getText().toString().equals("취소")&&enrollReplyTextView.getText().toString().equals("답글 달기")) {
                    final String comment = replyEditText.getText().toString();

                    if(comment.equals("")) {
                        return;
                    }
                    else {
                        long mNow = System.currentTimeMillis();
                        Date mDate = new Date(mNow);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
                        final String writtenDateTime = sdf.format(mDate);
                        final String repliedCommentNum = commentList.get(i).getRepliedCommentNum();
                        final String commentNum = commentList.get(i).getCommentNum();
                        final String noticeNum = commentList.get(i).getNoticeNum();
                        final String type = commentList.get(i).getType();
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success) {
                                        commentList.remove(i);
                                        NoticeComment tempComment = new NoticeComment(type, "temp", noticeNum, comment, loginUserId,
                                                loginUserName, writtenDateTime, repliedCommentNum);
                                        commentList.add(i, tempComment);
                                        notifyDataSetChanged();
                                    }
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        AlterCommentRequest alterCommentRequest = new AlterCommentRequest(commentNum, comment, writtenDateTime, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(alterCommentRequest);
                        new CommentBackgroundTask().execute();
                        replyEditText.setText("");
                    }
                }
            }
        });
        deleteCommentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean(response);
                            /*
                            if(success) {
                                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_LONG).show();
                            }
                            */
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest(commentList.get(i).getCommentNum(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteCommentRequest);
                commentList.remove(i);
                notifyDataSetChanged();
            }
        });

        return v;
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
                notifyDataSetChanged();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
