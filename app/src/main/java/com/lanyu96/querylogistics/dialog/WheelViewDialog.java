package com.lanyu96.querylogistics.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lanyu96.querylogistics.R;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;

public class WheelViewDialog extends Dialog{

    private LoopView loopView;
    private Button btn_ok;
    private Button btn_cancle;
    private OnSelectedListener listener;

    private ArrayList<String> stringArrayList = new ArrayList<>();
    private int position = 0;
    private LinearLayout ll;

    public WheelViewDialog(Context context, ArrayList<String> arrayList) {
        super(context,R.style.alert_dialog);
        stringArrayList = arrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view);
        setCanceledOnTouchOutside(false);//设置显示dialog后,触屏屏幕不会使dialog消失

        ll = findViewById(R.id.fragment_query_dialog_ll);
        ll.setBackgroundResource(R.drawable.dialog_background);
        loopView =  findViewById(R.id.loopView);
        btn_ok =  findViewById(R.id.btn_ok);
        btn_cancle =  findViewById(R.id.btn_cancle);

        // 设置原始数据
        loopView.setItems(stringArrayList);
        loopView.setNotLoop();
        // 滚动监听

        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (!TextUtils.isEmpty( stringArrayList.get( i) ) ){
//                    Toast.makeText(getContext() ,"选择了" + stringArrayList.get( i) , Toast.LENGTH_SHORT).show();
                    position = i;//存储选择的位序
                }
            }
        });


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    if (!TextUtils.isEmpty( stringArrayList.get( position) ) ){
                        listener.getData( stringArrayList.get( position) );
                    }
                }
                dismiss();
            }
        });

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public interface OnSelectedListener{
        void getData(String data);
    }
    public void setOnSelectedListener(OnSelectedListener listener){
        this.listener = listener;
    }
}
