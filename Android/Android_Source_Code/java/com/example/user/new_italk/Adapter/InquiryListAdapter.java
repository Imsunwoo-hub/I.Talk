package com.example.user.new_italk.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.user.new_italk.JAVA_Bean.Inquiry;
import com.example.user.new_italk.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InquiryListAdapter extends BaseAdapter {

    private Context context;
    private List<Inquiry> inquiryList;
    private List<Inquiry> inquirySaveList;
    private String loginUsername, type;

    public InquiryListAdapter(Context context, List<Inquiry> inquiryList, List<Inquiry> inquirySaveList, String loginUsername, String type) {
        this.context = context;
        this.inquiryList = inquiryList;
        this.inquirySaveList = inquirySaveList;
        this.loginUsername = loginUsername;
        this.type = type;
    }

    public  void setCheck(int i){
        inquiryList.get(i).checked = !(inquiryList.get(i).checked);
    }

    @Override
    public int getCount() {
        return inquiryList.size();
    }

    @Override
    public Object getItem(int i) {
        return inquiryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.inquiry, null);

        TextView inquiryNum = (TextView)v.findViewById(R.id.inquiryNum);
        TextView otherUser =  (TextView)v.findViewById(R.id.otherUser);
        TextView lastComment = (TextView)v.findViewById(R.id.lastComment);
        TextView lastCommentDateTime = (TextView)v.findViewById(R.id.lastCommentDateTime);
        CheckBox checked = (CheckBox)v.findViewById(R.id.checked);

        if(type.equals("settingInquiry")){
            lastCommentDateTime.setVisibility(View.GONE);
            checked.setVisibility(View.VISIBLE);
        } else {
            checked.setVisibility(View.GONE);
        }

        inquiryNum.setText(String.valueOf(inquiryList.get(i).getInquiryNum()));

        if(inquiryList.get(i).getUser1().equals(loginUsername)){
            otherUser.setText(inquiryList.get(i).getUser2());
        }

        if(inquiryList.get(i).getUser2().equals(loginUsername)){
            otherUser.setText(inquiryList.get(i).getUser1());
        }

        lastComment.setText(inquiryList.get(i).getLastComment());

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String nowdate = df.format(new Date());
        String lastCommentDate = df.format(inquiryList.get(i).getLastCommentDateTime());


        if(Integer.parseInt(nowdate)> Integer.parseInt(lastCommentDate)){

            lastCommentDateTime.setText(new SimpleDateFormat("yyyy/MM/dd").format(inquiryList.get(i).getLastCommentDateTime()));
        }

        else{
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH", Locale.getDefault());
            String time = timeFormat.format(inquiryList.get(i).getLastCommentDateTime());

            if(Integer.parseInt(time) > 12){
                int hour = Integer.parseInt(time) - 12;
                int minute = Integer.parseInt(new SimpleDateFormat("mm").format(inquiryList.get(i).getLastCommentDateTime()));
                lastCommentDateTime.setText("오후 " + hour+"시 " + minute + "분");
            }
            else {
                int hour = Integer.parseInt(time);
                int minute = Integer.parseInt(new SimpleDateFormat("mm").format(inquiryList.get(i).getLastCommentDateTime()));
                lastCommentDateTime.setText("오전 " + hour+"시 " + minute + "분");
            }
        }


        v.setTag(inquiryList.get(i).getInquiryNum());

        checked.setClickable(false);
        checked.setFocusable(false);
        checked.setChecked(inquiryList.get(i).isChecked());

        return v;
    }
}
