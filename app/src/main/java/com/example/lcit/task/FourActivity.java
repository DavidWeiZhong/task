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
        switch (view.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(this, FourActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                Intent  intent1 = new Intent("finish");
                sendBroadcast(intent1);
                break;

        }



//        ScreenManager.getScreenManager();

    }
}
