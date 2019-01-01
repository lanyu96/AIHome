package com.lanyu96.querylogistics.uitl;


import android.content.Context;
import android.widget.Toast;

public class TransformationUtil {


    public static String Transformation(Context context,String str){
        String logisticsCompany;
        switch (str) {
            case "申通快递":
                logisticsCompany = "shentong";
                break;
            case "EMS":
                logisticsCompany = "ems";
                break;
            case "顺丰速运":
                logisticsCompany = "shunfeng";
                break;
            case "圆通快递":
                logisticsCompany = "yuantong";
                break;
            case "中通快递":
                logisticsCompany = "zhongtong";
                break;
            case "韵达快递":
                logisticsCompany = "yunda";
                break;
            case "汇通快递":
                logisticsCompany = "huitongkuaidi";
                break;
            case "天天快递":
                logisticsCompany = "tiantian";
                break;
            case "全峰物流":
                logisticsCompany = "quanfengkuaidi";
                break;
            case "德邦物流":
                logisticsCompany = "debangwuliu";
                break;
            case "宅急送":
                logisticsCompany = "zhaijisong";
                break;
            case "请选择":
                Toast.makeText(context, "请选择一个快递公司", Toast.LENGTH_SHORT).show();
                logisticsCompany = "";
                break;
            default:
                Toast.makeText(context, "请选择快递公司", Toast.LENGTH_SHORT).show();
                logisticsCompany = "";
        }
        return logisticsCompany;

    }

}
