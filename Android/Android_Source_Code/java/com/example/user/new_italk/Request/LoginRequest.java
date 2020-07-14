package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "http://tkdl2401.cafe24.com/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userid, String userpassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userid", userid);
        parameters.put("userpassword", userpassword);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
