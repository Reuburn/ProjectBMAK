package com.example.projectbmak;

import java.util.HashMap;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BluetoothDiscoveryManager {
	public String str_selectedDevice;
	public ArrayAdapter<String> adpt_btArrayAdapter;
	public BluetoothAdapter adpt_btAdapter;
	public IntentFilter iF_btFilter;
	public Set<BluetoothDevice> pairedDevices;
	public HashMap<String, BluetoothDevice> hm_btDeviceMap;
	public TextView lbl_bluetoothAvailable;
	public ListView lst_btList;
	public Button btn_scanDevices;
	public Button btn_connectComputer;
	
	public BroadcastReceiver btBroadcastReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String str_action = intent.getAction();
	 
	        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(str_action)) {
	            //discovery starts, we can show progress dialog or perform other tasks
	        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(str_action)) {
	        	Log.e("","Discovery finished");
	        	btn_scanDevices.setText("Scan Devices");
	            //discovery finishes, dismis progress dialog
	        } else if (BluetoothDevice.ACTION_FOUND.equals(str_action)) {
	            addDeviceToList(intent);
	        }
	    }
	
		private void addDeviceToList(Intent intent) {
			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			
			String str_deviceName = device.getName();
			String str_deviceAddress = device.getAddress();
			
			if (str_deviceName == null){
				str_deviceName = "Unknown Device";
			}
			
			adpt_btArrayAdapter.add(str_deviceName + "\n" + str_deviceAddress);
			Log.d("",str_deviceName + " has been found.");
			lst_btList.setAdapter(adpt_btArrayAdapter);
			hm_btDeviceMap.put(str_deviceAddress, device);
		}
	};
	

	public BluetoothDiscoveryManager(String str_selectedDevice,
			BluetoothAdapter adpt_btAdapter, IntentFilter iF_btFilter,
			HashMap<String, BluetoothDevice> hm_btDeviceMap) {
		this.str_selectedDevice = str_selectedDevice;
		this.adpt_btAdapter = adpt_btAdapter;
		this.iF_btFilter = iF_btFilter;
		this.hm_btDeviceMap = hm_btDeviceMap;
	}
}