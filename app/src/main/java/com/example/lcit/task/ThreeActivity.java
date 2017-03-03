package com.example.lcit.task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ${qiuweizhong} on 2017/3/3.
 */
public class ThreeActivity extends AppCompatActivity {


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("finish")) {
                Toast.makeText(ThreeActivity.this, "好的", Toast.LENGTH_SHORT).show();
                ThreeActivity.this.finish();
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("finish");
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    public void btnclick(View view) {

        Intent intent = new Intent(this, FourActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mBroadcastReceiver);
    }
}
