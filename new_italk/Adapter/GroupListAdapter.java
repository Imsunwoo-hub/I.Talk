package com.example.user.new_italk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.user.new_italk.JAVA_Bean.Group;
import com.example.user.new_italk.R;

import java.util.List;

public class GroupListAdapter extends BaseAdapter {

    private Context context;
    private  List<Group> groupList;
    private List<Group> saveList;
    private String loginUserid, type;
    int groupNum;
    private Activity parentActivity;
    private AlertDialog dialog;



    public GroupListAdapter(Context context, List<Group> groupList, String loginUserid, List<Group> saveList, Activity parentActivity, String type) {
        this.context = context;
        this.groupList = groupList;
        this.loginUserid = loginUserid;
        this.saveList = saveList;
        this.parentActivity = parentActivity;
        this.type = type;
    }

    public  void setCheck(int i){
        groupList.get(i).checkGroup = !(groupList.get(i).checkGroup);
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int i) {
        return groupList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final View v = View.inflate(context, R.layout.group, null);

        final TextView groupNumTextView = (TextView) v.findViewById(R.id.groupNumTextView);
        final TextView groupNameTextView = (TextView) v.findViewById(R.id.groupNameTextView);
        TextView groupAdministratorTextView = (TextView) v.findViewById(R.id.groupAdministratorTextView);
        CheckBox check = (CheckBox) v.findViewById(R.id.check);

        groupNumTextView.setText(String.valueOf(groupList.get(i).getGroupnum()));
        groupNameTextView.setText(groupList.get(i).getGroupname());
        groupAdministratorTextView.setText(groupList.get(i).getGroupadministrator());


        if(type.equals("SettingGroupList")){
            check.setVisibility(View.VISIBLE);
        }
        else{
            check.setVisibility(View.GONE);
        }


        v.setTag(groupList.get(Integer.valueOf(i)).getGroupname());

        check.setClickable(false);
        check.setFocusable(false);
        check.setChecked(groupList.get(i).getCheckGroup());
        return v;
    }
}