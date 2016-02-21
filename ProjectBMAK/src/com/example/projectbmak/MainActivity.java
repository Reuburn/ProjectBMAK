package com.example.projectbmak;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.projectbmak.ConnectThread.OnConnectionCallback;

import android.app.Activity;
import android.bluetooth.*;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private final static int REQUEST_ENABLE_BT = 1;
	private BluetoothDevice contactedDevice;
	private ConnectThread btConnection;
	private boolean deviceConnected = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		showToast("onCreate");
		
		setContentView(R.layout.activity_main);
		
		assignVariables();
		
		data.lbl_bluetoothAvailable.setText("Checking if Bluetooth is available.");
		
		data.btn_connectComputer.setEnabled(false);
		
		data.lst_btList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id){
				if(data.adpt_btAdapter.isDiscovering() == true){
					data.adpt_btAdapter.cancelDiscovery();
				}
				
				data.str_selectedDevice = ((TextView)v).getText().toString();
				
				showToast(data.str_selectedDevice + " has been selected.");
				data.btn_connectComputer.setEnabled(true);
			}

		});
		
		this.checkBluetooth();
		this.scanPaired(data.adpt_btArrayAdapter);
	}

	private void assignVariables() {
		data.lbl_bluetoothAvailable = (TextView) findViewById(R.id.lbl_bluetoothAvailableLabel);
		data.btn_scanDevices = (Button) findViewById(R.id.btn_scanDevices);
		data.btn_connectComputer = (Button) findViewById(R.id.btn_connectComputer);
		data.lst_btList = (ListView) findViewById(R.id.lst_btListView);
		int items = 0;
		data.adpt_btArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
	}
	
	private String findMacAddress(String item) {
		Pattern macPattern = Pattern.compile("([A-Z a-z 0-9][A-Z a-z 0-9]:){5}[A-Z a-z 0-9][A-Z a-z 0-9]");
		Matcher macFinder = macPattern.matcher(item);
		String macAddress = "";
		
		while (macFinder.find()){
			macAddress = macFinder.group(0);
			showToast(macAddress);
		}
		return macAddress;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private BluetoothDiscoveryManager data = new BluetoothDiscoveryManager("",
			BluetoothAdapter.getDefaultAdapter(), new IntentFilter(BluetoothDevice.ACTION_FOUND), new HashMap <String, BluetoothDevice>());

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == RESULT_OK){
				this.changeBluetoothAvailabilityLabel();
			}
			else{
				finish();
			}
		}
	}
	
	public void connectToDevice(View v) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		
		if (deviceConnected == false){
			String macAddress = findMacAddress(data.str_selectedDevice);
			contactedDevice = data.hm_btDeviceMap.get(macAddress);
			showToast("Attempting to connect to " + contactedDevice.getName());
			btConnection = new ConnectThread(contactedDevice, (new ConnectThread.OnConnectionCallback () {
	            @Override
	            public void onConnected() {
	            	Log.i("MAINACTIVITY","Connected now");
	            	deviceConnected = true;
	            	changeConnectButtonText(deviceConnected);
	            }
	            @Override
	            public void onConnectedFailed() {
	            	Log.i("MAINACTIVITY","Connection failed");
	            	deviceConnected = false;
	            }
			}));
			btConnection.start();
		}
		else if (deviceConnected == true){
			btConnection.cancel();
			Log.i("MAINACTIVITY","Disconnected Successfully");
			deviceConnected = false;
			changeConnectButtonText(deviceConnected);
		}
		
	}

	public void changeBluetoothAvailabilityLabel(){
		data.lbl_bluetoothAvailable.setText("Please scan for devices.");
	}
	
	public void changeConnectButtonText(boolean deviceConnected){
		if(deviceConnected == true){
			runOnUiThread(new Runnable() {
			     @Override
			     public void run() {
			    	 data.btn_connectComputer.setText("Disconnect Device");
			    	 showToast(contactedDevice.getName() + " is connected.");
			     }});
		}
		else {
			runOnUiThread(new Runnable() {
			     @Override
			     public void run() {
			    	 data.btn_connectComputer.setText("Connect to Device");
			    	 showToast("Disconnected from device.");
			     }});
		}
		
	}
	
	public void checkBluetooth(){
		
		if (data.adpt_btAdapter == null) {
			//No Bluetooth on this device
			data.lbl_bluetoothAvailable.setText("This device doesn't support bluetooth.");
		}
		else if (!data.adpt_btAdapter.isEnabled()) {
			requestToEnableBt();
		}
		else if (data.adpt_btAdapter.isEnabled()) {
			data.lbl_bluetoothAvailable.setText("Please scan for devices.");
		}
	}
	
	private void requestToEnableBt() {
		data.lbl_bluetoothAvailable.setText("Please enable bluetooth.");
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	}
	
	public void scanDevices(View v){
		
		if (data.adpt_btAdapter.isDiscovering()){
			
			stopDiscoveringDevices();
			
		} else {
			
			discoverDevices();
			
		}
		
		data.lbl_bluetoothAvailable.setText("Please select a device to connect to.");
		
	}

	private void discoverDevices() {
		data.adpt_btArrayAdapter.clear();
		
		Log.i("Discovery","Beginning discovery");
		showToast("Beginning discovery...");

		discoverNew(data.adpt_btArrayAdapter);
		
		data.btn_scanDevices.setText("Stop Scanning");
	}

	private void stopDiscoveringDevices() {
		data.adpt_btAdapter.cancelDiscovery();
		
		Log.i("Discovery","Discovery has been stopped.");
		showToast("Discovery has been stopped.");
		
		data.btn_scanDevices.setText("Scan Devices");
	}
	
	public void scanPaired(ArrayAdapter<String> btArrayAdapter){
		data.pairedDevices = data.adpt_btAdapter.getBondedDevices();
		
		if (data.pairedDevices.size() > 0){
			for(BluetoothDevice device : data.pairedDevices){
				String str_deviceAddress = device.getAddress();
				String str_deviceName = device.getName();
				
				btArrayAdapter.add(str_deviceName + "\n" + str_deviceAddress);
				showToast(device.getName() + " is paired.");
				data.hm_btDeviceMap.put(str_deviceAddress, device);
				data.lst_btList.setAdapter(btArrayAdapter);
			}
		}
	}
	
	public void discoverNew(ArrayAdapter<String> btArrayAdapter){
		data.iF_btFilter.addAction(BluetoothDevice.ACTION_FOUND);
		data.iF_btFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		data.iF_btFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
 
		data.adpt_btAdapter.startDiscovery();
		
		registerReceiver(data.btBroadcastReceiver, data.iF_btFilter);
		
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(data.btBroadcastReceiver);
		super.onDestroy();
	}

	public void switchToKeyboard(View v){
		Intent intent = new Intent(this,KeyboardActivity.class);
		startActivity(intent);
	}
	
	public void switchToMouse(View v){
		Intent intent = new Intent(this,MouseActivity.class);
		startActivity(intent);
	}
	
	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	public BluetoothDevice getContactedDevice() {
		return contactedDevice;
	}

	public void setContactedDevice(BluetoothDevice contactedDevice) {
		this.contactedDevice = contactedDevice;
	}
	
}
