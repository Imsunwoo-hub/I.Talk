package com.example.user.new_italk.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AlterUserInfoRequest extends StringRequest {
    final static private String URL = "http://tkdl2401.cafe24.com/AlterUserInfo.php";
    private Map<String, String> parameters;

    public AlterUserInfoRequest(String userid, String newid, String username, String newname, String newmajor , String newgrade, String newstate, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userid", userid);
        parameters.put("newuserid", newid);
        parameters.put("username", username);
        parameters.put("newusername", newname);
        parameters.put("usermajor", newmajor);
        parameters.put("usergrade", newgrade);
        parameters.put("userstate", newstate);



    }

    public Map<String, String> getParams() {
        return parameters;
    }
}