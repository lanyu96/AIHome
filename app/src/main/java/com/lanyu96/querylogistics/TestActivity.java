package com.lanyu96.querylogistics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dd.morphingbutton.MorphingButton;
import com.dd.processbutton.iml.ActionProcessButton;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import github.ishaan.buttonprogressbar.ButtonProgressBar;

public class TestActivity extends AppCompatActivity {

    private ActionProcessButton btnSignIn;
    private MorphingButton morBtn;
    private ButtonProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btn = findViewById(R.id.test111111111);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog sDialog = new SweetAlertDialog(TestActivity.this, SweetAlertDialog.ERROR_TYPE);
                sDialog.setTitleText("信息不完整");
                sDialog.setContentText("请将快递公司和快递单号填写完整后再试");
                sDialog.setConfirmText("继续填写");
                sDialog.show();
            }
        });

    }



    public void test111(View view) {



    }
}
