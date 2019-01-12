package com.lanyu96.querylogistics.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanyu96.querylogistics.R;

import java.util.ArrayList;

public class LeDeviceListAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> mLeDevices;
    // 蓝牙信号强度
    private ArrayList<Integer> rssis;

    private LayoutInflater mInflator;

    private Context mContext;

    public LeDeviceListAdapter(Context context)
    {
        super();
        this.mContext = context;
        rssis = new ArrayList<>();
        mLeDevices = new ArrayList<>();
        mInflator = LayoutInflater.from(mContext);
    }

    public void addDevice(BluetoothDevice device, int rssi)
    {
        if (!mLeDevices.contains(device))
        {
            mLeDevices.add(device);
            rssis.add(rssi);
        }
    }

    public BluetoothDevice getDevice(int position)
    {
        return mLeDevices.get(position);
    }

    public void clear()
    {
        mLeDevices.clear();
        rssis.clear();
    }

    @Override
    public int getCount()
    {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i)
    {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    /**
     * 重写getview
     *
     * **/
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        // General ListView optimization code.
        // 加载listview每一项的视图
        view = mInflator.inflate(R.layout.item_device_list, null);
        // 初始化三个textview显示蓝牙信息
        TextView deviceAddress = view
                .findViewById(R.id.tv_deviceAddr);
        TextView deviceName = view
                .findViewById(R.id.tv_deviceName);
        TextView rssi = view.findViewById(R.id.tv_rssi);

        BluetoothDevice device = mLeDevices.get(i);
        deviceAddress.setText(device.getAddress());
        deviceName.setText(device.getName());
        rssi.setText("" + rssis.get(i));

        return view;
    }

}
