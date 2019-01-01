package com.lanyu96.querylogistics.bean;

import java.util.List;

public class PackagesInfo {


    /**
     * message : ok
     * nu : 802990317202125904
     * ischeck : 1
     * condition : F00
     * com : yuantong
     * status : 200
     * state : 3
     * data : [{"time":"2018-12-01 15:55:00","ftime":"2018-12-01 15:55:00","context":"客户 签收人: 他人代签,妈妈驿站 已签收 感谢使用圆通速递，期待再次为您服务","location":""},{"time":"2018-12-01 15:38:34","ftime":"2018-12-01 15:38:34","context":"快件已到达海岱中心街红绿灯西300米路北妈妈驿站,联系电话13002729433","location":""},{"time":"2018-12-01 09:58:17","ftime":"2018-12-01 09:58:17","context":"【山东省烟台市龙口市公司】 派件人: 韩丽 派件中 派件员电话15005454471","location":""},{"time":"2018-12-01 09:40:29","ftime":"2018-12-01 09:40:29","context":"【山东省烟台市龙口市公司】 已收入","location":""},{"time":"2018-11-30 06:02:24","ftime":"2018-11-30 06:02:24","context":"【潍坊转运中心】 已发出 下一站 【山东省烟台市龙口市公司】","location":""},{"time":"2018-11-30 06:00:25","ftime":"2018-11-30 06:00:25","context":"【潍坊转运中心】 已收入","location":""},{"time":"2018-11-30 01:58:15","ftime":"2018-11-30 01:58:15","context":"【青岛转运中心】 已发出 下一站 【潍坊转运中心】","location":""},{"time":"2018-11-28 19:41:39","ftime":"2018-11-28 19:41:39","context":"【青岛转运中心】 已收入","location":""},{"time":"2018-11-27 04:29:57","ftime":"2018-11-27 04:29:57","context":"【佛山转运中心】 已发出 下一站 【青岛转运中心】","location":""},{"time":"2018-11-27 04:28:44","ftime":"2018-11-27 04:28:44","context":"【佛山转运中心】 已收入","location":""},{"time":"2018-11-27 01:33:02","ftime":"2018-11-27 01:33:02","context":"【深圳转运中心】 已发出 下一站 【佛山转运中心】","location":""},{"time":"2018-11-27 01:30:05","ftime":"2018-11-27 01:30:05","context":"【深圳转运中心】 已收入","location":""},{"time":"2018-11-26 23:55:16","ftime":"2018-11-26 23:55:16","context":"【广东省深圳市电子公司】 已发出 下一站 【深圳转运中心】","location":""},{"time":"2018-11-26 23:48:46","ftime":"2018-11-26 23:48:46","context":"【广东省深圳市电子公司】 已打包","location":""},{"time":"2018-11-26 23:44:38","ftime":"2018-11-26 23:44:38","context":"【广东省深圳市电子公司】 已收件","location":""}]
     */

    private String nu;
    private String com;
    private List<DataBean> data;

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018-12-01 15:55:00
         * ftime : 2018-12-01 15:55:00
         * context : 客户 签收人: 他人代签,妈妈驿站 已签收 感谢使用圆通速递，期待再次为您服务
         * location :
         */

        private String time;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
