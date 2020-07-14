package com.example.user.new_italk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.JAVA_Bean.User;
import com.example.user.new_italk.R;
import com.example.user.new_italk.Request.FindUserInfoRequest;
import com.example.user.new_italk.UserInfoActivity;

import org.json.JSONObject;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private List<User> userSaveList;
    private  boolean click = false;
    private String userphonenum;
    private AlertDialog dialog;
    private String loginUseridentity, loginUserid;

    public UserListAdapter(Context context, List<User> userList, List<User> userSaveList, String loginUseridentity, String loginUserid) {
        this.context = context;
        this.userList = userList;
        this.userSaveList = userSaveList;
        this.loginUseridentity = loginUseridentity;
        this.loginUserid = loginUserid;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

         View v = View.inflate(context, R.layout.user, null);


        final LinearLayout lay = (LinearLayout)v.findViewById(R.id.lay);
        TextView idText  = (TextView)v.findViewById(R.id.idText);
        final TextView useridTextView  = (TextView)v.findViewById(R.id.useridTextView);
        TextView usernameTextView  = (TextView)v.findViewById(R.id.usernameTextView);
        final TextView userphonenumTextView  = (TextView)v.findViewById(R.id.userphonenumTextView);
        final TextView useridentityTextView = (TextView)v.findViewById(R.id.useridentityTextView);
        TextView usergradeTextView = (TextView)v.findViewById(R.id.usergradeTextView);
        TextView call = (TextView)v.findViewById(R.id.call);
        //ImageView menuImageView = (ImageView)v.findViewById(R.id.menuImageView);
        //ImageView usermanagement = (ImageView)v.findViewById(R.id.usermanagement);
        LinearLayout layout1 = (LinearLayout)v.findViewById(R.id.layout1);
        LinearLayout layout2 = (LinearLayout)v.findViewById(R.id.layout2);
        TextView textView1 = (TextView)v.findViewById(R.id.textview1);
        TextView textView2 = (TextView)v.findViewById(R.id.textview2);


        useridTextView.setText(userList.get(i).getUserid());
        usernameTextView.setText(userList.get(i).getUsername());
        userphonenumTextView.setText(userList.get(i).getUserphonenum());
        useridentityTextView.setText(userList.get(i).getUseridentity());
        usergradeTextView.setText(userList.get(i).getUsergrade() + "학년");

        /*if((loginUseridentity.equals("학생"))){
            //usermanagement.setVisibility(View.GONE);
        }
*/
        if(!(useridentityTextView.getText().toString().equals("학생"))){
            usergradeTextView.setVisibility(View.GONE);
        }


        String before_grade, before_major, before_identity ;
        try {
            before_grade = userList.get(i - 1).getUsergrade();
            before_major = userList.get(i - 1).getUsermajor();
            before_identity = userList.get(i - 1).getUseridentity();

        } catch (IndexOutOfBoundsException ex) {
            before_grade = userList.get(0).getUsergrade();
            before_major = userList.get(0).getUsermajor();
            before_identity = userList.get(0).getUseridentity();
        }

        String grade = userList.get(i).getUsergrade();
        String major = userList.get(i).getUsermajor();
        String identity = userList.get(i).getUseridentity();



        if(i==0){
            if(identity.equals("학생")){
                layout1.setVisibility(View.VISIBLE);
                textView1.setText(userList.get(i).getUseridentity());
                layout2.setVisibility(View.VISIBLE);
                textView2.setText(userList.get(i).getUsergrade()+"학년");
            }
            layout1.setVisibility(View.VISIBLE);
            textView1.setText(userList.get(i).getUseridentity());
        }

        if(!(identity.equals(before_identity))){
            if(identity!=null) {
                layout1.setVisibility(View.VISIBLE);
                textView1.setText(userList.get(i).getUseridentity());
            }
        }

        if(!(grade.equals(before_grade)) && !(grade.equals(""))){
                layout2.setVisibility(View.VISIBLE);
                textView2.setText(userList.get(i).getUsergrade()+"학년");

        }


/*
        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click==false) {
                    lay.setVisibility(View.VISIBLE);
                    click=true;
                }else{
                    lay.setVisibility(View.GONE);
                    click=false;
                }
            }
        });

        v.setTag(userList.get(i).getUserid());

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userphonenum = userphonenumTextView.getText().toString();*/

                /*if (userphonenum.length() == 8) {
                    userphonenum.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
                } else if (userphonenum.length() == 12) {
                    userphonenum.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
                }
                userphonenum.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");*/
/*
                String phonenum = "tel:" + userphonenum;
                Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(phonenum));
                context.startActivity(intent);
            }
        });

        usermanagement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Response.Listener<String> reponseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String userid = jsonResponse.getString("userid");
                                //String userpassword = jsonResponse.getString("pw");
                                String username = jsonResponse.getString("username");
                                String userphonenum = jsonResponse.getString("userphonenum");
                                String useremail = jsonResponse.getString("useremail");
                                String usergrade = jsonResponse.getString("usergrade");
                                String usermajor = jsonResponse.getString("usermajor");
                                String userstate = jsonResponse.getString("userstate");
                                Intent userInfoIntent = new Intent(context, UserInfoActivity.class);
                                userInfoIntent.putExtra("authority", "userManagement");
                                userInfoIntent.putExtra("loginUserid", loginUserid);
                                userInfoIntent.putExtra("userid", userid);
                                // userInfoIntent.putExtra("userpassword", userpassword);
                                userInfoIntent.putExtra("username", username);
                                userInfoIntent.putExtra("userphonenum", userphonenum);
                                userInfoIntent.putExtra("useremail", useremail);
                                userInfoIntent.putExtra("usergrade", usergrade);
                                userInfoIntent.putExtra("usermajor", usermajor);
                                userInfoIntent.putExtra("useridentity", useridentityTextView.getText().toString());
                                userInfoIntent.putExtra("userstate", userstate);
                                context.startActivity(userInfoIntent);
                            }
                            else{
                                AlertDialog.Builder bulilder = new AlertDialog.Builder(context);
                                dialog = bulilder.setMessage("정보 조회에 실패하였습니다.")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                FindUserInfoRequest findUserInfoRequest = new FindUserInfoRequest(useridTextView.getText().toString(), reponseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(findUserInfoRequest);
            }
        });*/

        return v;
    }
}
