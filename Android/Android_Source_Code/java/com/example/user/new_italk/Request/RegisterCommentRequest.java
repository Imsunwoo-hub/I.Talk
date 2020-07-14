package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterCommentRequest extends StringRequest {

    final static private String URL = "http://tkdl2401.cafe24.com/registerComment.php";
    private Map<String, String> parameters;

    public RegisterCommentRequest(String type, String noticeNum, String comment, String commentWriterId, String commentWriterName, String writtenDateTime, String repliedCommentNum, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Type", type);
        parameters.put("NoticeNum", noticeNum);
        parameters.put("Comment", comment);
        parameters.put("CommentWriterId", commentWriterId);
        parameters.put("CommentWriterName", commentWriterName);
        parameters.put("WrittenDateTime", writtenDateTime);
        parameters.put("RepliedCommentNum", repliedCommentNum);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
