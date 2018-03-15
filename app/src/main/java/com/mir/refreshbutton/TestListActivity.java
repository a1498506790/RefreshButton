package com.mir.refreshbutton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.gjiazhe.wavesidebar.WaveSideBar;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018-03-15
 * @desc
 */

public class TestListActivity extends AppCompatActivity{

    private WaveSideBar mWaveSideBar;
    private RecyclerView mRecyclerView;
    private TestAdapter mAdapter;
    private String[] mContactNames = {"张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123",
            "天山童姥", "任我行", "于万亭", "陈家洛", "韦小宝", "$6", "穆人清", "陈圆圆", "郭芙", "郭襄", "穆念慈",
            "东方不败", "梅超风", "林平之", "林远图", "灭绝师太", "段誉", "鸠摩智", "uuu", "123", "ninini", "啊啊啊", "变变变"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);
        mRecyclerView = findViewById(R.id.recyclerView);
        mWaveSideBar = findViewById(R.id.side_bar);
        mWaveSideBar.setIndexItems("A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#");
        initAdapter();
    }

    private void initAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mAdapter = new TestAdapter(this, mContactNames);
        mRecyclerView.setAdapter(mAdapter);


        mWaveSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                int position = mAdapter.getScrollPosition(index);
                layoutManager.scrollToPositionWithOffset(position, 0);
            }
        });
    }
}
