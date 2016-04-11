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
			Log.i("ConnectThread", "Connecting to server.");
			btSocket.connect();
			Log.i("ConnectThread", "Connected to server.");
			setDeviceConnected(true);
			
		}
		catch (IOException connectException) {
			connectException.printStackTrace();
			Log.e("ConnectThread", "Could not connect to server application.");
			fallbackMethod();
		}
		
		if (deviceConnected == true){
			btConnectedThread = new ManageConnectedThread(btSocket, (new ManageConnectedThread.OnManageConnectedThreadCallback() {
				
				@Override
				public void changeToDisconnected() {
					setDeviceConnected(false);
				}
			}));
			btConnectedThread.start();
		}
	}

	private void fallbackMethod() {
		try{
			Log.i("ConnectThread", "Attempting alternate fallback method.");
			btSocket = (BluetoothSocket) btDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(btDevice, 1);
			btSocket.connect();
			Log.v("ConnectThread", "Connected to device.");
			setDeviceConnected(true);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | IOException e){
			Log.e("ConnectThread","Could not connect to server application.");
			cancel();
		}
	}
	
	public void cancel() {
		try {
			btSocket.close();
			Log.v("ConnectThread", "Connection is now closed.");
			setDeviceConnected(false);
		}
		catch(IOException e) {
			Log.e("ConnectThread", "Unable to close the connection.");
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


