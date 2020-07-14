package com.example.user.new_italk.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddChatRequest extends StringRequest {
    final static private String URL = "http://tkdl2401.cafe24.com/AddChat.php";
    private Map<String, String> parameters;

    public AddChatRequest(int inquiryNum, String userid, String comment, Response.Listener<String> listener){
        super( Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("inquiryNum", inquiryNum +"");
        parameters.put("userid", userid);
        parameters.put("comment", comment);



    }
    public Map<String, String> getParams() {
        return parameters;
    }
}

