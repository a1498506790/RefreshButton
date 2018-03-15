package com.mir.refreshbutton;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TelContactActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<TelContactBean> mDatas = new ArrayList<>();
    private TelContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_contact);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mAdapter = new TelContactAdapter(this, mDatas);
        mAdapter.setOnItemClickListener(new TelContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<TelContactBean> data = mAdapter.getData();
                String telNumber = data.get(position).getTelNumber();
                try{
                    sendSms(telNumber);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        MPermissionUtils.requestPermissionsResult(TelContactActivity.this, 1,
                new String[]{Manifest.permission.READ_CONTACTS}, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        readContacts();
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(TelContactActivity.this);
                    }
                });

    }

    /**
     * 发送短信
     * @param telNumber
     */
    private void sendSms(String telNumber){
        Uri uri = Uri.parse("smsto:" + telNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "快来参加活动吧~~~~");
        startActivity(intent);
    }

    /**
     * 获取手机通讯录联系人
     */
    private void readContacts(){
        Cursor cursor = null;
        try{
            cursor=getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            mDatas.clear();
            while(cursor.moveToNext()){
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                TelContactBean bean = new TelContactBean(displayName, number);
                mDatas.add(bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        mAdapter.setNewData(mDatas);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
