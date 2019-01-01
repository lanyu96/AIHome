package com.lanyu96.querylogistics.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.lanyu96.querylogistics.R;
import com.lanyu96.querylogistics.adapter.DataInfoAdapter;
import com.lanyu96.querylogistics.bean.LocAndTimeInfo;
import com.lanyu96.querylogistics.bean.PackagesCompany;
import com.lanyu96.querylogistics.dialog.WheelViewDialog;
import com.lanyu96.querylogistics.uitl.GetJsonData;
import com.lanyu96.querylogistics.uitl.NetWorkUtil;
import com.lanyu96.querylogistics.uitl.SimpleDividerItemDecoration;
import com.lanyu96.querylogistics.uitl.TransformationUtil;
import com.weavey.loading.lib.LoadingLayout;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import github.ishaan.buttonprogressbar.ButtonProgressBar;

import static android.content.Context.MODE_PRIVATE;

public class PackagesFragment extends Fragment{
    private Activity mActivity;
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
    private Button onClickQueryBtn;
    private Button queryCompanyBtn;
    private ArrayList<String> stringArrayList;
    private LoadingLayout loadingLayout;
    private ImageView queryBgIv;
    private ActionProcessButton onClickQueryProcessBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_packages, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stringArrayList = new ArrayList<>();
        stringArrayList.addAll(Arrays.asList(PackagesCompany.str));
//        btnProgress = view.findViewById(R.id.act_query_btn_progressBar);

//        onClickQueryBtn = view.findViewById(R.id.fragment_onClick_query_btn);

        //带进度的查询按钮
        onClickQueryProcessBtn = view.findViewById(R.id.fragment_onClick_query_action_btn);

        sp = mActivity.getSharedPreferences("danhao", MODE_PRIVATE);
        query_company_tv = view.findViewById(R.id.act_query_company_tv);
        query_danhao_tv = view.findViewById(R.id.act_query_danhao_tv);

        logisticsDanhao_et = view.findViewById(R.id.act_query_yundanhao_et);
//        query_company_nicespinner = findViewById(R.id.act_query_company_nicespinner);

        //初始化RecyclerView
        dataInfo_rv = view.findViewById(R.id.act_query_dateInfo_rv);
        //设置为线性布局管理器
        dataInfo_rv.setLayoutManager(new LinearLayoutManager(mActivity
                , LinearLayoutManager.VERTICAL, false));
        //瀑布流管理器
//        dataInfo_rv.setLayoutManager(new StaggeredGridLayoutManager(1, VERTICAL));

        //为recyclerView添加分割线
        dataInfo_rv.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), 50, 3));

        //初始化RecyclerView背景
        queryBgIv = view.findViewById(R.id.fragment_query_bg_iv);

        //初始化弹出dialo g的button
        queryCompanyBtn = view.findViewById(R.id.act_query_company_btn);

        //初始化状态加载布局
        loadingLayout = view.findViewById(R.id.act_loading_ll);


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
            logisticsCompany = TransformationUtil.Transformation(mActivity, spCompany);

        }
        showDialog();


        //加载失败页面中的点击重试按钮监听
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                boolean isNetworkAvailable = NetWorkUtil.isNetworkAvailable(mActivity);
                if (!isNetworkAvailable) {
                    //隐藏背景图片
                    queryBgIv.setVisibility(View.INVISIBLE);
                    //当无网络连接时 ,显示无网络状态
                    loadingLayout.setStatus(LoadingLayout.No_Network);
                } else {
                    netWorkAvailable();
                }
            }
        });
        onClickQueryProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isNetworkAvailable = NetWorkUtil.isNetworkAvailable(mActivity);
                if (!isNetworkAvailable) {
                    //进度按钮设置正在加载
                    onClickQueryProcessBtn.setProgress(50);
                    //隐藏背景图片
                    queryBgIv.setVisibility(View.INVISIBLE);
                    //当无网络连接时 ,显示无网络状态
                    loadingLayout.setStatus(LoadingLayout.No_Network);
                    //进度按钮设置错误状态
                    onClickQueryProcessBtn.setProgress(-1);
                } else {
                    netWorkAvailable();

                }
            }
        });


    }
    private void netWorkAvailable(){
        String logisticsDanhao = logisticsDanhao_et.getText().toString().trim();
        if (logisticsCompany == null || logisticsCompany.equals("请选择") || logisticsDanhao.equals("")) {
//            Toast.makeText(mActivity, "请填写完整后再试", Toast.LENGTH_SHORT).show();

            Toast toasty = Toasty.warning(mActivity, "请将快递公司和单号补充完整", Toast.LENGTH_SHORT, true);
            toasty.setGravity(Gravity.BOTTOM,0,300);
            toasty.show();

//            SweetAlertDialog sDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.WARNING_TYPE);
//            sDialog.setTitleText("信息不完整");
//            sDialog.setContentText("请将快递公司和快递单号填写完整后再试");
//            sDialog.setConfirmText("继续填写");
//            sDialog.show();
        } else {
            //设置带进度的按钮状态为正在加载
            onClickQueryProcessBtn.setProgress(50);
            //显示正在加载的图标
            loadingLayout.setStatus(LoadingLayout.Loading);
            //隐藏背景图片
            queryBgIv.setVisibility(View.INVISIBLE);
            sb = new StringBuilder();
            sb.append("http://www.kuaidi100.com/query?type=");

//        String logisticsCompany = logisticsCompany_et.getText().toString().trim();


            sb.append(logisticsCompany);

            sb.append("&postid=");
            sb.append(logisticsDanhao);
            Log.i(TAG, "字符串请求");

            new RequestNetworkDataTask().execute(sb.toString());

        }
    }

    private void showDialog() {
        queryCompanyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final WheelViewDialog dialog = new WheelViewDialog( mActivity, stringArrayList );
                dialog.setOnSelectedListener(new WheelViewDialog.OnSelectedListener() {
                    @Override
                    public void getData(String data) {
                        Toast.makeText(mActivity, ""+data, Toast.LENGTH_SHORT).show();
                        queryCompanyBtn.setText(data);
                        logisticsCompany = TransformationUtil.Transformation(mActivity,data);
                        company = data;
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

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
            dataInfo_rv.setAdapter(new DataInfoAdapter(mActivity, list));
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

                Log.i("ZHOUT", "state是" + state);
                Log.i("ZHOUT", "status是" + status);
                if (Integer.parseInt(status) != 200) {
                    loadingLayout.setStatus(LoadingLayout.Empty);
                }else{

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
                dataInfo_rv.setAdapter(new DataInfoAdapter(mActivity, list));
                loadingLayout.setStatus(LoadingLayout.Success);
                //设置带进度加载按钮状态为完成状态
                onClickQueryProcessBtn.setProgress(100);
                //这个判断为了解决当缓存一个数据后, 打开应用 直接点击查询,出结果,
                //关闭再次打开App 导致 数据清空问题
                if (company != null && number != null && !company.equals("") && !number.equals("")) {
                    //将快递公司信息和快递单号添加到SP中
                    sp.edit().putString("COMPANY", company)
                            .putString("NUMBER", number).apply();
                }
            }

            } catch (JSONException e) {
                e.printStackTrace();
            }

//            logisticsInfo_tv.setText(s);
            Log.i(TAG, s);
        }

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }
}
