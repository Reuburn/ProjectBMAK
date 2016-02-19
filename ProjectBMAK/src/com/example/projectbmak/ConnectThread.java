package com.example.projectbmak;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.util.Log;

public class ConnectThread extends Thread {
	private BluetoothSocket btSocket;
	private final BluetoothDevice btDevice;
	private final static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
	public ManageConnectedThread btConnectedThread;

	public ConnectThread(BluetoothDevice device) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		BluetoothSocket tmp = null;
		btDevice = device;
		
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
		}
		catch (IOException connectException) {
			connectException.printStackTrace();
			//Log.d("CONNECTTHREAD","Could not connect:" + connectException.toString());
			fallbackMethod();
			//return;
		}
		
		//btConnectedThread = new ManageConnectedThread(btSocket);
		//btConnectedThread.start();
	}

	private void fallbackMethod() {
		try{
			Log.e("CONNECTTHREAD", "Attempting fallback...");
			btSocket = (BluetoothSocket) btDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(btDevice, 1);
			btSocket.connect();
			Log.e("CONNECTTHREAD", "Connected");
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | IOException e){
			e.printStackTrace();
			Log.d("CONNECTTHREAD","Fallback failed.");
			cancel();
		}
	}
	
	/*public BluetoothSocketWrapper connect() throws IOException{
		boolean success = false;
		try {
			Log.d("CONNECTTHREAD","Attempting to connect");
			btSocket.connect();
			Log.e("", "Connection Successful");
			success = true;
		}
		catch (IOException e){
			try{
				btSocket = new FallbackBluetoothSocket(btSocket.getUnderlyingSocket());
				Thread.sleep(500);
				btSocket.connect();success = true;
			}
			catch (FallbackException e1){
				Log.w("CONNECTTHREAD", "Could not init FallbackBluetoothSocketClasses.", e);
			}
			catch (InterruptedException e1){
				Log.w("CONNECTTHREAD", e1.getMessage(), e1);
			}
			catch(IOException e1){
				Log.w("CONNECTTHREAD", "Fallback failed. Cancelling.", e1);
			}
		}
		
		if (!success){
			throw new IOException("Could not connect to device: " + btDevice.getAddress());
		}
		return btSocket;
	}*/
	
	public void cancel() {
		try {
			btSocket.close();
			Log.e("CONNECTTHREAD", "Connection closed");
		}
		catch(IOException e) {
			Log.d("CONNECTTHREAD", "Unable to close connection: " + e.toString());
			return;
		}
		return;
	}
}

