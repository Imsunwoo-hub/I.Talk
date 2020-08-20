package com.example.user.new_italk.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AlterNoticeRequest extends StringRequest {
    final static private String URL = "http://tkdl2401.cafe24.com/alterNotice.php";
    private Map<String, String> parameters;

    public AlterNoticeRequest(String noticeNum, String noticeClassification, String noticeTitle, String noticeContent, String noticeWriter, String noticeDate, String noticeTopFixed, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("NoticeNum", noticeNum);
        parameters.put("NoticeClassification", noticeClassification);
        parameters.put("NoticeTitle", noticeTitle);
        parameters.put("NoticeContent", noticeContent);
        parameters.put("NoticeWriter", noticeWriter);
        parameters.put("NoticeDate", noticeDate);
        parameters.put("NoticeTopFixed", noticeTopFixed);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
