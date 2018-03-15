package com.mir.refreshbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jaeger.library.SelectableTextHelper;

public class TextSelectActivity extends AppCompatActivity {

    private TextView mTextView;
    private SelectableTextHelper mTextHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_select);
        mTextView = findViewById(R.id.textView);
//        mTextHelper = new SelectableTextHelper.Builder(mTextView)
//                .setSelectedColor(getResources().getColor(R.color.selected_blue))
//                .setCursorHandleSizeInDp(20)
//                .setCursorHandleColor(getResources().getColor(R.color.cursor_handle_color))
//                .build();
//        mTextHelper.setSelectListener(new OnSelectListener() {
//            @Override
//            public void onTextSelected(CharSequence content) {}
//        });
    }
}
