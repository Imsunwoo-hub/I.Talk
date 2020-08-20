package com.example.user.new_italk.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AlterContactRequest  extends StringRequest {
    final static private String URL = "http://tkdl2401.cafe24.com/AlterContact.php";
    private Map<String, String> parameters;

    public AlterContactRequest(String userid, String userphonenum, String newUserphonenum , String useremail, String newUseremail, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userid", userid);
        parameters.put("userphonenum", userphonenum);
        parameters.put("newUserphonenum", newUserphonenum);
        parameters.put("useremail", useremail);
        parameters.put("newUseremail", newUseremail);



    }

    public Map<String, String> getParams() {
        return parameters;
    }
}

