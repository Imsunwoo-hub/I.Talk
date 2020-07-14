package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteGroupsRequest extends StringRequest {
    final static private String URL = "http://tkdl2401.cafe24.com/DeleteGroups.php";
    private Map<String, String> parameters;
    private String groups = "";

    public DeleteGroupsRequest(String[] checkdeGroupArray, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        for(int i=0; i<checkdeGroupArray.length; i++) {
            if(i==0){
                groups += checkdeGroupArray[i];
            }
            else{
                groups += ',' + checkdeGroupArray[i] ;
            }
            parameters.put("groups", groups);
        }
    }


    public Map<String, String> getParams() {
        return parameters;
    }
}

