package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://tkdl2401.cafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userid, String userpassword, String username, String userphonenum, String useremail, String useridentity, String usermajor, String usergrade, String userstate,
                           Response.Listener<String> listener ){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userid", userid);
        parameters.put("userpassword", userpassword);
        parameters.put("username", username);
        parameters.put("userphonenum", userphonenum);
        parameters.put("useremail", useremail);
        parameters.put("userstate", userstate);
        parameters.put("useridentity", useridentity);
        parameters.put("usermajor", usermajor);
        parameters.put("usergrade", usergrade);
    }

    public Map<String, String> getParams(){
        return parameters;
    }

}
