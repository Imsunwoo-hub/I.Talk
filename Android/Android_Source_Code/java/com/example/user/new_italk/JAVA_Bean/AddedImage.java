package com.example.user.new_italk.JAVA_Bean;

import android.graphics.Bitmap;

public class AddedImage {
    Bitmap bitmap;
    public AddedImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
