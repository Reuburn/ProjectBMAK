package com.example.projectbmak;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.util.Log;

public class ConnectThread extends Thread{
	/**
	 * 
	 */
	private BluetoothSocket btSocket;
	private final BluetoothDevice btDevice;
	private final static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
	private boolean deviceConnected;
	public ManageConnectedThread btConnectedThread;
	
	public static interface OnConnectionCallback {

		public void onConnected();
		public void onConnectedFailed();

	}
	
	private OnConnectionCallback mCallback;

	public ConnectThread(BluetoothDevice device, OnConnectionCallback callback) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		BluetoothSocket tmp = null;
		btDevice = device;
		mCallback = callback;
		setDeviceConnected(false);
		
		if (Build.VERSION.SDK_INT < 10){
			Method m = btDevice.getClass().getMethod("createInsecureRfcommSocket",new Class[] { int.class });
			tmp = (BluetoothSocket) m.invoke(device, Integer.valueOf(1));
		}
		else{
			tmp = btDevice.createRfcommSocketToServiceRecord(MY_UUID); 
		}
		
		btSocket = (tmp);
	}
	
	public void run() {
		
		try {
			Log.e("CONNECTTHREAD", "Connecting...");
			btSocket.connect();
			Log.e("CONNECTTHREAD", "Connected");
			setDeviceConnected(true);
			
		}
		catch (IOException connectException) {
			connectException.printStackTrace();
			//Log.d("CONNECTTHREAD","Could not connect:" + connectException.toString());
			fallbackMethod();
			//return;
		}
		
		if (deviceConnected == true){
			btConnectedThread = new ManageConnectedThread(btSocket);
			btConnectedThread.start();
		}
	}

	private void fallbackMethod() {
		try{
			Log.e("CONNECTTHREAD", "Attempting fallback...");
			btSocket = (BluetoothSocket) btDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(btDevice, 1);
			btSocket.connect();
			Log.e("CONNECTTHREAD", "Connected");
			setDeviceConnected(true);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | IOException e){
			e.printStackTrace();
			Log.d("CONNECTTHREAD","Fallback failed.");
			cancel();
		}
	}
	
	public void cancel() {
		try {
			btSocket.close();
			Log.e("CONNECTTHREAD", "Connection closed");
			setDeviceConnected(false);
		}
		catch(IOException e) {
			Log.d("CONNECTTHREAD", "Unable to close connection: " + e.toString());
			return;
		}
		return;
	}

	public boolean isDeviceConnected() {
		return deviceConnected;
	}

	public void setDeviceConnected(boolean deviceConnected) {
		if(mCallback != null){ 
			if(deviceConnected){
				mCallback.onConnected(); 
			}				
			else{
				mCallback.onConnectedFailed();
			}
		}
		this.deviceConnected = deviceConnected;
	}
	
}


