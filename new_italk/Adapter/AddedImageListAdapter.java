package com.example.user.new_italk.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.user.new_italk.JAVA_Bean.AddedImage;
import com.example.user.new_italk.R;

import java.util.List;

public class AddedImageListAdapter extends BaseAdapter {
    private Context context;
    private List<AddedImage> addedImageList;
    public AddedImageListAdapter(Context context, List<AddedImage> addedImageList) {
        this.context = context;
        this.addedImageList = addedImageList;
    }
    @Override
    public int getCount() {
        return addedImageList.size();
    }

    @Override
    public Object getItem(int i) {
        return addedImageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.added_image_in_notice, null);
        ImageView addedImageView = (ImageView)v.findViewById(R.id.addedImageView);
        addedImageView.setImageBitmap(addedImageList.get(i).getBitmap());
        return v;
    }
}
