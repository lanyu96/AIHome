package com.lanyu96.querylogistics.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lanyu96.querylogistics.R;
import com.lanyu96.querylogistics.adapter.LeDeviceListAdapter;

import java.util.ArrayList;

public class BLEShowActivity extends AppCompatActivity {

    // listview显示扫描到的蓝牙信息
    ListView showDeviceLv;
    // 扫描蓝牙按钮
    private Button scan_btn;
    // 蓝牙适配器
    BluetoothAdapter mBluetoothAdapter;
    // 蓝牙信号强度
    private ArrayList<Integer> rssis;
    // 自定义Adapter
    LeDeviceListAdapter mleDeviceListAdapter;
    // 描述扫描蓝牙的状态
    private boolean mScanning;
    private boolean scan_flag;
    private Handler mHandler;
    int REQUEST_ENABLE_BT = 1;
    // 蓝牙扫描时间
    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bleshow);
        // 初始化控件
        init();
        // 初始化蓝牙
        init_ble();
        scan_flag = true;
        // 自定义适配器
        mleDeviceListAdapter = new LeDeviceListAdapter(BLEShowActivity.this);
        // 为listview指定适配器
        showDeviceLv.setAdapter(mleDeviceListAdapter);

        /* listview点击函数 */
        showDeviceLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long id) {
                rvOnItemClick(position);


            }
        });

    }

    /**
     * ListView条目点击事件
     *
     * @param position
     */
    private void rvOnItemClick(int position) {
        // TODO Auto-generated method stub
        final BluetoothDevice device = mleDeviceListAdapter
                .getDevice(position);
        if (device == null)
            return;
        final Intent intent = new Intent(BLEShowActivity.this,
                BLEConnectActivity.class);
        intent.putExtra(BLEConnectActivity.EXTRAS_DEVICE_NAME,
                device.getName());
        intent.putExtra(BLEConnectActivity.EXTRAS_DEVICE_ADDRESS,
                device.getAddress());
        intent.putExtra(BLEConnectActivity.EXTRAS_DEVICE_RSSI,
                rssis.get(position).toString());
        if (mScanning) {
            /* 停止扫描设备 */
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning = false;
        }

        try {
            // 启动Ble_Activity
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    /**
     * @return void
     * @throws
     * @Title: init
     * @Description: TODO(初始化UI控件)
     */
    private void init() {
        scan_btn = findViewById(R.id.act_BLE_scan_dev_btn);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scan_flag) {
                    mleDeviceListAdapter = new LeDeviceListAdapter(BLEShowActivity.this);
                    showDeviceLv.setAdapter(mleDeviceListAdapter);
                    scanLeDevice(true);
                } else {

                    scanLeDevice(false);
                    scan_btn.setText("扫描设备");
                }
            }
        });
        showDeviceLv = this.findViewById(R.id.fragment_device_show_lv);
        mHandler = new Handler();
    }

    /**
     * @return void
     * @throws
     * @Title: init_ble
     * @Description: TODO(初始化蓝牙)
     */
    private void init_ble() {
        // 手机硬件支持蓝牙
        if (!getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持BLE", Toast.LENGTH_SHORT).show();
            finish();
        }
        // Initializes Bluetooth adapter.
        // 获取手机本地的蓝牙适配器
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //更改
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 打开蓝牙权限
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }


    /**
     * @param enable (扫描使能，true:扫描开始,false:扫描停止)
     * @return void
     * @throws
     * @Title: scanLeDevice
     * @Description: TODO(扫描蓝牙设备)
     */
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    scan_flag = true;
                    scan_btn.setText("扫描设备");
                    Log.i("SCAN", "stop.....................");
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
            /* 开始扫描蓝牙设备，带mLeScanCallback 回调函数 */
            Log.i("SCAN", "begin.....................");
            mScanning = true;
            scan_flag = false;
            scan_btn.setText("停止扫描");
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            Log.i("Stop", "stoping................");
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            scan_flag = true;
        }

    }

    /**
     * 蓝牙扫描回调函数 实现扫描蓝牙设备，回调蓝牙BluetoothDevice，可以获取name MAC等信息
     **/
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi,
                             byte[] scanRecord) {
            // TODO Auto-generated method stub

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    /* 讲扫描到设备的信息输出到listview的适配器 */
                    mleDeviceListAdapter.addDevice(device, rssi);
                    mleDeviceListAdapter.notifyDataSetChanged();
                }
            });

            Log.i("TEST","Address:" + device.getAddress());
            Log.i("TEST","Name:" + device.getName());
            Log.i("TEST","rssi:" + rssi);

        }
    };

}
