package com.lanyu96.querylogistics.uitl;

public class SelectPackageCompany {
    /*
    ps:快递公司编码:申通=shentong EMS=ems 顺丰=shunfeng 圆通=yuantong 中通=zhongtong
        韵达=yunda 天天=tiantian 汇通=huitongkuaidi 全峰=quanfengkuaidi 德邦=debangwuliu 宅急送=zhaijisong,
     */

//    private String number;

    //    public SelectPackageCompany(String number) {
//        this.number = number;
//
//    }
    private static String packageCompany;
    public static String getCompany(String number) {

        switch (number) {
            case "shentong":
                packageCompany = "申通";
                break;
            case "ems":
                packageCompany = "EMS";
                break;
        }






        return null;
    }


}
