package com.example.user.new_italk.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.new_italk.JAVA_Bean.Notice;
import com.example.user.new_italk.MainActivity;
import com.example.user.new_italk.R;
import com.example.user.new_italk.ViewMoreNoticeActivity;

import org.w3c.dom.Text;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter{

    private Context context;
    private List<Notice> noticeList;
    private List<Notice> saveList;

    public NoticeListAdapter(Context context, List<Notice> noticeList, List<Notice> saveList) {
        this.context = context;
        this.noticeList = noticeList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice_sample, null);

        final TextView noticeTitleTextView = (TextView)v.findViewById(R.id.noticeTitleTextView);
        final TextView noticeContentTextView = (TextView)v.findViewById(R.id.noticeContentTextView);
        final TextView noticeWriterTextView = (TextView)v.findViewById(R.id.noticeWriterTextView);
        final TextView noticeClassification = (TextView)v.findViewById(R.id.noticeClassification);
        TextView noticeDateTextView = (TextView)v.findViewById(R.id.noticeDateTextView);

        noticeTitleTextView.setText(noticeList.get(i).getTitle());
        noticeContentTextView.setText(noticeList.get(i).getContent());
        noticeWriterTextView.setText(noticeList.get(i).getWriter());
        noticeDateTextView.setText(noticeList.get(i).getDate());
        noticeClassification.setText("[ " + noticeList.get(i).getClassification() + " ]");
        final String noticeNum = noticeList.get(i).getNum();
        final String topFixed = noticeList.get(i).getTopFixed();

        v.setTag(noticeList.get(i).getTitle());
        return v;
    }
}