package com.mir.refreshbutton;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLltRefresh, mLltExpansion;
    private ImageView mImgRefresh, mImgDownArrows;
    private boolean mIsExpansion = false;
    private TextView mTextExpansion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLltRefresh = findViewById(R.id.llt_refresh);
        mImgRefresh = findViewById(R.id.img_refresh);

        mLltRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimator();
            }
        });

        mTextExpansion = findViewById(R.id.text_expansion);
        mLltExpansion = findViewById(R.id.llt_expansion);
        mImgDownArrows = findViewById(R.id.img_down_arrows);
        mLltExpansion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsExpansion = !mIsExpansion;
                startExpansionAnimator();
                mTextExpansion.setText(mIsExpansion ? "收起" : "展开");
            }
        });
    }

    private void startExpansionAnimator() {
        ObjectAnimator animator;
        if (mIsExpansion) {
            animator = ObjectAnimator.ofFloat(mImgDownArrows, "rotation", 0, 180);
        }else{
            animator = ObjectAnimator.ofFloat(mImgDownArrows, "rotation", 180, 0);
        }
        animator.setDuration(500);
        animator.start();
    }

    private void startAnimator() {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(mImgRefresh, "rotation", 0, 360);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animator.cancel();
            }
        }, 2000);
    }
}
