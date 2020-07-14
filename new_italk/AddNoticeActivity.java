package com.example.user.new_italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.new_italk.Adapter.AddedImageListAdapter;
import com.example.user.new_italk.JAVA_Bean.AddedImage;
import com.example.user.new_italk.Request.AlterNoticeRequest;
import com.example.user.new_italk.Request.RegisterNoticeRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNoticeActivity extends AppCompatActivity {
    final static int PICK_FROM_ALBUM = 1;
    EditText noticeTitleEditText, noticeContentEditText;
    LinearLayout addNotificationTargetButton, addImageButton, addedImageLinearLayout;
    TextView enrollmentButton;
    ImageView cancelButton;
    CheckBox topFixedCheckBox;
    ListView addedImageListView;
    Spinner noticeClassificationSpinner;
    ArrayAdapter noticeClassificationAdapter;
    List<AddedImage> addedImageList;
    AddedImageListAdapter addedImageListAdapter;

    String loginUserId, noticeNum, noticeClassification, noticeTitle, noticeContent, noticeTopFixed = "0";
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        final Intent intent = getIntent();
        String callBy = getIntent().getStringExtra("callBy");
        noticeTitleEditText = (EditText)findViewById(R.id.noticeTitleEditText);
        noticeContentEditText = (EditText)findViewById(R.id.noticeContentEditText);
        topFixedCheckBox = (CheckBox)findViewById(R.id.topFixedCheckBox);
        addNotificationTargetButton = (LinearLayout)findViewById(R.id.addNotificationTargetButton);
        addImageButton = (LinearLayout)findViewById(R.id.addImageButton);
        cancelButton = (ImageView)findViewById(R.id.cancelButton);
        enrollmentButton = (TextView)findViewById(R.id.enrollmentButton);
        addImageButton = (LinearLayout)findViewById(R.id.addImageButton);
        addedImageLinearLayout = (LinearLayout)findViewById(R.id.addedImageLinearLayout);
        addedImageListView = (ListView)findViewById(R.id.addedImageListView);
        noticeClassificationSpinner = (Spinner)findViewById(R.id.noticeClassificationSpinner);
        noticeClassificationAdapter = ArrayAdapter.createFromResource(this, R.array.noticeClassification, R.layout.support_simple_spinner_dropdown_item);
        noticeClassificationSpinner.setAdapter(noticeClassificationAdapter);

        addedImageList = new ArrayList<AddedImage>();
        addedImageListAdapter = new AddedImageListAdapter(getApplicationContext(), addedImageList);
        addedImageListView.setAdapter(addedImageListAdapter);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addImageIntent = new Intent(Intent.ACTION_PICK);
                addImageIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(addImageIntent, PICK_FROM_ALBUM);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        topFixedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    noticeTopFixed = "1";
                }
                else {
                    noticeTopFixed = "0";
                }
            }
        });
        if(callBy.equals("add")) {

            loginUserId = intent.getStringExtra("userId");

            enrollmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noticeTitle = noticeTitleEditText.getText().toString();
                    noticeClassification = noticeClassificationSpinner.getSelectedItem().toString();
                    noticeContent = noticeContentEditText.getText().toString();
                    long mNow = System.currentTimeMillis();
                    Date mDate = new Date(mNow);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
                    String noticeDate = sdf.format(mDate);

                    if(noticeTitle.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddNoticeActivity.this);
                        dialog = builder.setMessage("공지사항 제목은 공백일 수 없습니다." + noticeClassification)
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNoticeActivity.this);
                                    dialog = builder.setMessage("공지사항 등록이 완료되었습니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    finish();
                                                }
                                            })
                                            .create();
                                    dialog.show();
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNoticeActivity.this);
                                    builder.setMessage("공지사항 등록에 실패했습니다.")
                                            .setPositiveButton("다시 시도", null)
                                            .create()
                                            .show();
                                }

                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    RegisterNoticeRequest registerNoticeRequest = new RegisterNoticeRequest(noticeClassification, noticeTitle, noticeContent, loginUserId, noticeDate, noticeTopFixed, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AddNoticeActivity.this);
                    queue.add(registerNoticeRequest);
                }
            });

        }
        else if(callBy.equals("alter")) {
            TextView writeOrAlter = (TextView)findViewById(R.id.writeOrAlter);
            writeOrAlter.setText("공지 수정하기");
            enrollmentButton.setText("수정");

            noticeClassification = intent.getStringExtra("noticeClassification");
            switch(noticeClassification) {
                case "일반":
                    noticeClassificationSpinner.setSelection(0);
                    break;
                case "학사":
                    noticeClassificationSpinner.setSelection(1);
                    break;
                case "강의":
                    noticeClassificationSpinner.setSelection(2);
                    break;
                case "장학":
                    noticeClassificationSpinner.setSelection(3);
                    break;
                case "학생회":
                    noticeClassificationSpinner.setSelection(4);
                    break;
                case "행사":
                    noticeClassificationSpinner.setSelection(5);
                    break;
                case "기타":
                    noticeClassificationSpinner.setSelection(6);
                    break;
            }

            noticeTitleEditText.setText(intent.getStringExtra("noticeTitle"));
            noticeContentEditText.setText(intent.getStringExtra("noticeContent"));
            if(intent.getStringExtra("noticeTopFixed").equals("1"))
                topFixedCheckBox.setChecked(true);
            else
                topFixedCheckBox.setChecked(false);

            enrollmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noticeNum = intent.getStringExtra("noticeNum");
                    noticeTitle = noticeTitleEditText.getText().toString();
                    noticeContent = noticeContentEditText.getText().toString();
                    noticeClassification = noticeClassificationSpinner.getSelectedItem().toString();
                    loginUserId = intent.getStringExtra("noticeWriter");
                    long mNow = System.currentTimeMillis();
                    Date mDate = new Date(mNow);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
                    final String noticeDate = sdf.format(mDate);
                    if(topFixedCheckBox.isChecked()) {
                        noticeTopFixed = "1";
                    }
                    else {
                        noticeTopFixed = "0";
                    }

                    if(noticeTitle.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddNoticeActivity.this);
                        dialog = builder.setMessage("공지사항 제목은 공백일 수 없습니다." + noticeClassification)
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        return;
                    }
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNoticeActivity.this);
                                    dialog = builder.setMessage("공지사항 수정이 완료되었습니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    finish();
                                                }
                                            })
                                            .create();
                                    dialog.show();
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNoticeActivity.this);
                                    builder.setMessage("공지사항 수정에 실패했습니다.")
                                            .setPositiveButton("다시 시도", null)
                                            .create()
                                            .show();
                                }

                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    AlterNoticeRequest alterNoticeRequest = new AlterNoticeRequest(noticeNum, noticeClassification, noticeTitle, noticeContent, loginUserId, noticeDate, noticeTopFixed, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AddNoticeActivity.this);
                    queue.add(alterNoticeRequest);
                }
            });
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(dialog!=null) {
            dialog.dismiss();
            dialog = null;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode==PICK_FROM_ALBUM && resultCode==RESULT_OK && null!=data) {
                Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                int nh = (int) (bitmap.getHeight()*(1024.0/bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                addedImageLinearLayout.setVisibility(View.VISIBLE);

                AddedImage tmp = new AddedImage(scaled);
                addedImageList.add(tmp);
                addedImageListAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, "취소 되었습니다", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
