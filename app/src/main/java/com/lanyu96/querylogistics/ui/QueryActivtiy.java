package com.lanyu96.querylogistics.ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lanyu96.querylogistics.R;
import com.lanyu96.querylogistics.adapter.DataInfoAdapter;
import com.lanyu96.querylogistics.bean.LocAndTimeInfo;
import com.lanyu96.querylogistics.bean.PackagesCompany;
import com.lanyu96.querylogistics.dialog.WheelViewDialog;
import com.lanyu96.querylogistics.uitl.GetJsonData;
import com.lanyu96.querylogistics.uitl.SimpleDividerItemDecoration;
import com.lanyu96.querylogistics.uitl.TransformationUtil;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import github.ishaan.buttonprogressbar.ButtonProgressBar;


public class QueryActivtiy extends AppCompatActivity {
    private static String TAG = "TTTT";
    //    private EditText logisticsCompany_et;
    private TextView logisticsInfo_tv;
    private StringBuilder sb;
    private EditText logisticsDanhao_et;
    private String jsonData;
    private TextView query_company_tv;
    private TextView query_danhao_tv;
    private RecyclerView dataInfo_rv;
    private NiceSpinner query_company_nicespinner;
    private String logisticsCompany;
    private String company;
    private SharedPreferences sp;
    private String spCompany;
    private ButtonProgressBar btnProgress;
    private Button queryCompanyBtn;
    private ArrayList<String> stringArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_activtiy);
        initData();
        initSp();
        initView();
        showDialog();
//        sb = new StringBuilder();
        //按钮实现逻辑
        ButtonProgress();

    }

    private void initData() {
        stringArrayList = new ArrayList<>();
        stringArrayList.addAll(Arrays.asList(PackagesCompany.str));
    }

    private void showDialog() {
        queryCompanyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WheelViewDialog dialog = new WheelViewDialog( QueryActivtiy.this, stringArrayList );
                dialog.setOnSelectedListener(new WheelViewDialog.OnSelectedListener() {
                    @Override
                    public void getData(String data) {
                        Toast.makeText(QueryActivtiy.this, ""+data, Toast.LENGTH_SHORT).show();
                        queryCompanyBtn.setText(data);
                        logisticsCompany = TransformationUtil.Transformation(QueryActivtiy.this,data);
                        company = data;
                    }
                });
                dialog.show();
            }
        });
    }

    private void ButtonProgress() {

        btnProgress = findViewById(R.id.act_query_btn_progressBar);
    }


    /**
     * 初始化SharedPreferences存储
     */
    private void initSp() {
        sp = getSharedPreferences("danhao", MODE_PRIVATE);


    }

    private void initView() {
//        logisticsCompany_et = findViewById(R.id.act_query_wuliugongsi_et);

//        logisticsInfo_tv = findViewById(R.id.act_query_info_tv);
        query_company_tv = findViewById(R.id.act_query_company_tv);
        query_danhao_tv = findViewById(R.id.act_query_danhao_tv);

        logisticsDanhao_et = findViewById(R.id.act_query_yundanhao_et);
//        query_company_nicespinner = findViewById(R.id.act_query_company_nicespinner);

        //初始化RecyclerView
        dataInfo_rv = findViewById(R.id.act_query_dateInfo_rv);
        //设置为线性布局管理器
        dataInfo_rv.setLayoutManager(new LinearLayoutManager(QueryActivtiy.this
                , LinearLayoutManager.VERTICAL, false));
        //瀑布流管理器
//        dataInfo_rv.setLayoutManager(new StaggeredGridLayoutManager(1, VERTICAL));

        //为recyclerView添加分割线
        dataInfo_rv.addItemDecoration(new SimpleDividerItemDecoration(this, 50, 3));


        //初始化弹出dialo g的button
        queryCompanyBtn = findViewById(R.id.act_query_company_btn);



        //为下拉列表设置item

//        LinkedList<String> linkedList = new LinkedList<>();
//        for (int i = 0; i < PackagesCompany.str.length; i++) {
//            linkedList.add(PackagesCompany.str[i]);
//        }
//
//        query_company_nicespinner.attachDataSource(linkedList);
//
//
//        //为下拉列表设置监听
//        query_company_nicespinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
////                Toast.makeText(QueryActivtiy.this, ""+view+PackagesCompany.str[position], Toast.LENGTH_SHORT).show();
//                logisticsCompany = TransformationUtil.Transformation(QueryActivtiy.this, PackagesCompany.str[position]);
//                company = PackagesCompany.str[position];
//            }
//        });


        //临时指定 edittext
//        logisticsCompany_et.setText("yuantong");
//        logisticsDanhao_et.setText("802990317202125904");
        spCompany = sp.getString("COMPANY", "");
        String spNumber = sp.getString("NUMBER", "");
        if (!spNumber.equals("") && !spCompany.equals("")) {
            logisticsDanhao_et.setText(spNumber);
//            query_company_nicespinner.setTextInternal(spCompany);
            //当sp中有数据时,自动调取数据
            queryCompanyBtn.setText(spCompany);

            //通过TransformationUtil工具类解析快递公司
            logisticsCompany = TransformationUtil.Transformation(this, spCompany);

        }


    }

    public void queryInfo(final View view) {
        String logisticsDanhao = logisticsDanhao_et.getText().toString().trim();
        if (logisticsCompany ==null || logisticsCompany.equals("请选择") || logisticsDanhao.equals("")) {
            Toast.makeText(this, "请填写完整后再试", Toast.LENGTH_SHORT).show();
        } else {
            btnProgress.startLoader();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnProgress.stopLoader();
                }
            }, 500);

            sb = new StringBuilder();
            sb.append("http://www.kuaidi100.com/query?type=");

//        String logisticsCompany = logisticsCompany_et.getText().toString().trim();





            sb.append(logisticsCompany);

            sb.append("&postid=");
            sb.append(logisticsDanhao);
            Log.i(TAG, "字符串请求");

            new RequestNetworkDataTask().execute(sb.toString());

            new Thread(new Runnable() {
                @Override
                public void run() {
//                    queryInfo(view);
                    btnProgress.startLoader();
                }
            });

            //获取数据后,清空StringBuilder
//        sb.delete(0, sb.length() - 1);

        }
        }




//    /**
//     *     解析
//     * @param jsonData
//     */
//    private void parseJSONWithGSON(String jsonData) {
//        Gson gson = new Gson();
//        List<JSONInfo> jsonInfos = gson.fromJson(jsonData, new TypeToken<List<JSONInfo>>() {
//        }.getType());
//        for (JSONInfo jsonInfo : jsonInfos) {
////            Log.i(TAG,jsonInfo.getState());
////            Log.i(TAG, jsonInfo.getMessage());
//        }
//
//    }

    //内部类: 实现异步网络请求
    @SuppressLint("StaticFieldLeak")
    class RequestNetworkDataTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            logisticsInfo_tv.setText("正在加载");
            query_company_tv.setText("正在加载");
            query_danhao_tv.setText("");
            List<DataInfoAdapter> list = new ArrayList<>();
            dataInfo_rv.setAdapter(new DataInfoAdapter(QueryActivtiy.this, list));
            Log.i(TAG, "正在获取jsonData");
        }

        @Override
        protected String doInBackground(String... strings) {
            //获取返回的JsonObject
            GetJsonData getJsonData = new GetJsonData();
            String result = getJsonData.getJsonData(strings[0]);
//            String result = GetJsonData.getJsonData(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //调用解析方法
//            parseJSONWithGSON(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

//                PackagesInfo packagesInfo = new PackagesInfo();

                //解析JSON
                //第一层解析
//                String company = jsonObject.optString("com");
                String condition = jsonObject.optString("codition");
                String ischeck = jsonObject.optString("ischeck");
                String number = jsonObject.optString("nu");
                Log.i(TAG, "快递单号为 : " + number);
                Log.i(TAG, "快递公司为" + company);
                //通过textView 显示单号和物流公司

                query_danhao_tv.setText(number);
                //判断如果company没有数据,则使用spCompany
                if (company == null || company.equals("")) {
                    query_company_tv.setText(spCompany);
                } else {
                    query_company_tv.setText(company);
                }
                String state = jsonObject.optString("state");
                String status = jsonObject.optString("status");

                //第二层解析
                JSONArray jsonArray = jsonObject.optJSONArray("data");

                //第三层解析
                List<LocAndTimeInfo> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonInfo = jsonArray.optJSONObject(i);

                    if (jsonInfo != null) {
                        String time = jsonInfo.getString("time");
                        String context = jsonInfo.getString("context");
                        Log.i(TAG, "时间 : " + time + "物流状态" + context);

                        list.add(new LocAndTimeInfo(time, context));
                    }
                }
                dataInfo_rv.setAdapter(new DataInfoAdapter(QueryActivtiy.this, list));

                //将快递公司信息和快递单号添加到SP中
                sp.edit().putString("COMPANY", company)
                        .putString("NUMBER", number).apply();


            } catch (JSONException e) {
                e.printStackTrace();
            }

//            logisticsInfo_tv.setText(s);
            Log.i(TAG, s);
        }
    }

}
