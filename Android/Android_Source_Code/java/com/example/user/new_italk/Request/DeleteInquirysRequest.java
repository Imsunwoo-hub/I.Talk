package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteInquirysRequest extends StringRequest {
    final static private String URL = "http://tkdl2401.cafe24.com/DeleteInquirys.php";
    private Map<String, String> parameters;
    private String inquirys = "";

    public DeleteInquirysRequest(String[] checkdeInquiryArray, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        for(int i=0; i<checkdeInquiryArray.length; i++) {
            if(i==0){
                inquirys += checkdeInquiryArray[i];
            }
            else{
                inquirys += ',' + checkdeInquiryArray[i] ;
            }
            parameters.put("inquirys", inquirys);
        }
    }


    public Map<String, String> getParams() {
        return parameters;
    }
}
