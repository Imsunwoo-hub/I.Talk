package com.example.user.new_italk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.user.new_italk.AddGroupActivity;
import com.example.user.new_italk.JAVA_Bean.Member;
import com.example.user.new_italk.R;

import java.util.List;

public class MemberListAdapter extends BaseAdapter {

    private Context context;
    private List<Member> userList;
    private List<Member> saveList;
    private AlertDialog dialog;
    private Activity parentActivity;
    private String loginUserid, type;

    public MemberListAdapter(Context context, List<Member> userList, List<Member> saveList, Activity parentActivity, String loginUserid) {
        this.context = context;
        this.userList = userList;
        this.saveList = saveList;
        this.parentActivity = parentActivity;
        this.loginUserid = loginUserid;
    }


    public void setCheck(int i){
        userList.get(i).checkMember = !(userList.get(i).checkMember);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v  = View.inflate(context, R.layout.member, null);

        final TextView useridTextView = (TextView)v.findViewById(R.id.useridTextView);
        TextView idText = (TextView)v.findViewById(R.id.idText);
        TextView usernameTextView = (TextView)v.findViewById(R.id.usernameTextView);
        TextView usergradeTextView = (TextView)v.findViewById(R.id.usergradeTextView);
        TextView usermajorTextView = (TextView)v.findViewById(R.id.usermajorTextView);
        TextView gradeText = (TextView)v.findViewById(R.id.gradeText);
        final TextView useridentityTextView = (TextView)v.findViewById(R.id.useridentityTextView);
        TextView userstateTextView = (TextView)v.findViewById(R.id.userstateTextView);
        CheckBox userCheck = (CheckBox)v.findViewById(R.id.userCheck);

        useridTextView.setText(userList.get(i).getMemberid());
        usernameTextView.setText(userList.get(i).getMembername());
        usergradeTextView.setText(userList.get(i).getMembergrade() + "학년");
        usermajorTextView.setText(userList.get(i).getMembermajor());
        useridentityTextView.setText(userList.get(i).getMemberidentity());
        userstateTextView.setText(userList.get(i).getMemberstate());

        if(!(userList.get(i).getMemberidentity().equals("학생"))){
            idText.setText("사번 : ");
            gradeText.setVisibility(View.GONE);
            usergradeTextView.setVisibility(View.GONE);
        }

        v.setTag(userList.get(i).getMemberid());

        userCheck.setClickable(false);
        userCheck.setFocusable(false);
        userCheck.setChecked(userList.get(i).getChecked());



        return v;
    }
}
