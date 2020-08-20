package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateGroupRequest extends StringRequest {

    final static private String URL = "http://tkdl2401.cafe24.com/CreateGroup.php";
    private Map<String, String> parameters;
    private String groupMember = "";

    public CreateGroupRequest(String userid, String groupName, String[] groupMemberArray, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userid", userid);
        parameters.put("groupName", groupName);
        for(int i=0; i<groupMemberArray.length; i++) {
            if(i==0){
                groupMember += groupMemberArray[i];
            }
            else{
                groupMember += ',' + groupMemberArray[i] ;
            }
            parameters.put("groupMember", groupMember);
        }
    }
    public Map<String, String> getParams() {
        return parameters;
    }
}

