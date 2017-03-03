package com.example.lcit.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ${qiuweizhong} on 2017/3/3.
 */
public class FourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
    }

    public void btnclick(View view) {

        Intent intent = new Intent(this, FourActivity.class);
        startActivity(intent);

//        ScreenManager.getScreenManager();

    }
}
