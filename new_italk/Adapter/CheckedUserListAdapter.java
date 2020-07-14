package com.example.user.new_italk.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.new_italk.JAVA_Bean.Member;
import com.example.user.new_italk.R;

import java.util.List;

public class CheckedUserListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<Member> checkedUserList;
    private List<Member> checkedUsersaveList;
    private List<Member> userList;
    private MemberListAdapter memberListAdapter;

    public CheckedUserListAdapter(Context context, List<Member> checkedUserList, List<Member> checkedUsersaveList, List<Member> userList, MemberListAdapter memberListAdapter) {
        this.context = context;
        this.checkedUserList = checkedUserList;
        this.checkedUsersaveList = checkedUsersaveList;
        this.userList = userList;
        this.memberListAdapter = memberListAdapter;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkeduser, parent, false);
        context = parent.getContext();

        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        holder.userid.setText(checkedUserList.get(position).memberid);
        holder.username.setText(checkedUserList.get(position).membername);


    }

    @Override
    public int getItemCount() {
        return checkedUserList.size();
    }
}
