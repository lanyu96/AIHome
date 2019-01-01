package com.lanyu96.querylogistics.uitl;

import android.bluetooth.BluetoothAdapter;

public class BlueToothContraller {

    private BluetoothAdapter bluetoothAdapter;

    public BlueToothContraller () {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    /**
     * 判断设备是否支持蓝牙
     * @return true 设备支持蓝牙  false 设备不支持蓝牙
     */
    public boolean isSupportBlueTooth() {
        if (bluetoothAdapter != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断蓝牙是否打开
     * @return true 打开  false 关闭
     */
    public boolean getBlueToothStatus() {
        //添加断言,防止当bluetoothAdapter为空的时候,引发空指针异常
        assert (bluetoothAdapter != null);

        return bluetoothAdapter.isEnabled();
    }

}
