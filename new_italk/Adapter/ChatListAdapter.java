package com.example.user.new_italk.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.new_italk.JAVA_Bean.Chat;
import com.example.user.new_italk.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private List<Chat> ChatList;
    private List<Chat> ChatsaveList;
    private String loginUsername;

    public ChatListAdapter(Context context, List<Chat> chatList, List<Chat> chatsaveList, String loginUsername) {
        this.context = context;
        this.ChatList = chatList;
        this.ChatsaveList = chatsaveList;
        this.loginUsername = loginUsername;
    }

    @Override
    public int getCount() {
        return ChatList.size();
    }

    @Override
    public Object getItem(int i) {
        return ChatList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v  = View.inflate(context, R.layout.chat, null);

        LinearLayout otherUserLayout = (LinearLayout)v.findViewById(R.id.otherUserLayout);
        LinearLayout myLayout = (LinearLayout)v.findViewById(R.id.myLayout);
        LinearLayout dateLayout = (LinearLayout)v.findViewById(R.id.datelayout);

        TextView usernameTextView = (TextView)v.findViewById(R.id.usernameTextView);
        TextView commentTextView = (TextView)v.findViewById(R.id.commentTextView);
        TextView timeTextView = (TextView)v.findViewById(R.id.timeTextView);
        TextView count = (TextView)v.findViewById(R.id.count);
        TextView mycount = (TextView)v.findViewById(R.id.mycount);
        TextView myCommentTextView = (TextView)v.findViewById(R.id.myCommentTextView);
        TextView mytimeTextView = (TextView)v.findViewById(R.id.mytimeTextView);
        TextView dateTextView =  (TextView)v.findViewById(R.id.datetextview);


        if(ChatList.get(i).getSender().equals(loginUsername)) {
            otherUserLayout.setVisibility(View.GONE);

            StringBuffer sb = new StringBuffer(ChatList.get(i).getComment());
            if (sb.length() >= 20) {
                for (int j = 1; j <= sb.length() / 20; j++) {
                    sb.insert(20 * j, "\n");
                }
            }
            myCommentTextView.setText(sb);

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
            String before_CommentDate;
            try {
                before_CommentDate = df.format(ChatList.get(i - 1).getCommentDateTime());
            } catch (IndexOutOfBoundsException ex) {
                before_CommentDate = df.format(ChatList.get(0).getCommentDateTime());
            }

            String CommentDate = df.format(ChatList.get(i).getCommentDateTime());

            if (Integer.parseInt(before_CommentDate) != Integer.parseInt(CommentDate)) {
                dateTextView.setText(new SimpleDateFormat("yyyy년 MM월 dd일").format(ChatList.get(i).getCommentDateTime()));
                dateLayout.setVisibility(View.VISIBLE);
            } else {

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH", Locale.getDefault());
                String time = timeFormat.format(ChatList.get(i).getCommentDateTime());
                if (Integer.parseInt(time) > 12) {
                    int hour = Integer.parseInt(time) - 12;
                    int minute = Integer.parseInt(new SimpleDateFormat("mm").format(ChatList.get(i).getCommentDateTime()));
                    mytimeTextView.setText("오후 " + hour + ":" + minute);
                } else {
                    int hour = Integer.parseInt(time);
                    int minute = Integer.parseInt(new SimpleDateFormat("mm").format(ChatList.get(i).getCommentDateTime()));
                    mytimeTextView.setText("오전 " + hour + ":" + minute);
                }
            }
        }
        else {
            myLayout.setVisibility(View.GONE);
            usernameTextView.setText(ChatList.get(i).getSender());

            StringBuffer sb = new StringBuffer(ChatList.get(i).getComment());
            if (sb.length() >= 20) {
                for (int j = 1; j <= sb.length() / 20; j++) {
                    sb.insert(20 * j, "\n");
                }
            }
            commentTextView.setText(sb);


                SimpleDateFormat timeFormat = new SimpleDateFormat("HH", Locale.getDefault());
                String time = timeFormat.format(ChatList.get(i).getCommentDateTime());
                if (Integer.parseInt(time) > 12) {
                    int hour = Integer.parseInt(time) - 12;
                    int minute = Integer.parseInt(new SimpleDateFormat("mm").format(ChatList.get(i).getCommentDateTime()));
                    timeTextView.setText("오후 " + hour+ ":" + minute);
                } else {
                    int hour = Integer.parseInt(time);
                    int minute = Integer.parseInt(new SimpleDateFormat("mm").format(ChatList.get(i).getCommentDateTime()));
                    timeTextView.setText("오전 " + hour+ ":" + minute);
                }
            }


        v.setTag(ChatList.get(i).getSender());

        return v;
    }
}
