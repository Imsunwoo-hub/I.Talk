package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AlterCommentRequest extends StringRequest {

    final static private String URL = "http://tkdl2401.cafe24.com/alterComment.php";
    private Map<String, String> parameters;

    public AlterCommentRequest(String commentNum, String comment, String writtenDateTime, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("CommentNum", commentNum);
        parameters.put("Comment", comment);
        parameters.put("WrittenDateTime", writtenDateTime);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
