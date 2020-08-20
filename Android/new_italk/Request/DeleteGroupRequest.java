package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteGroupRequest extends StringRequest {

    final static private String URL = "http://tkdl2401.cafe24.com/DeleteGroup.php";
    private Map<String, String> parameters;

    public DeleteGroupRequest(int groupnum, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("groupnum", groupnum +"");
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}