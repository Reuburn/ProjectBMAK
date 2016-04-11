package com.example.projectbmak;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ManageConnectedThread extends Thread {
	public final BluetoothSocket btSocket;
	private final InputStream btInStream;
	private final OutputStream btOutStream;
	public static final int EXIT_CMD = -1;
	public static final int VOL_UP_PRESS = 1;
	public static final int VOL_UP_HELD = 2;
	public static final int VOL_UP_RELEASED = 3;
	public static final int VOL_DOWN_PRESS = 4;
	public static final int VOL_DOWN_HELD = 5;
	public static final int VOL_DOWN_RELEASED = 6;
	
	public static interface OnManageConnectedThreadCallback{
		public void changeToDisconnected();
	}
	
	private OnManageConnectedThreadCallback mCallback;
	
	public ManageConnectedThread(BluetoothSocket btSocket2, OnManageConnectedThreadCallback onManageConnectedThreadCallback){
		this.mCallback = onManageConnectedThreadCallback;
		btSocket = btSocket2;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		
		try {
			tmpIn = btSocket2.getInputStream();
			tmpOut = btSocket2.getOutputStream();
		} 
		catch(IOException e) {
			Log.e("ManageConnectedThread", "There are no available input or output streams.");
		}
		
		btInStream = tmpIn;
		btOutStream = tmpOut;
		
	}
	
	public void run() {
		Log.v("ManageConnectedThread", "Connection to server application established.");
		
		while (true){
			try {
				int bytes = btInStream.read();
				if (bytes == 1) this.cancel();
			}
			catch(IOException e) {
				Log.v("ManageConnectedThread", "Connection to server application has ended.");
				break;
			}
		}
	}
	
	public void write(byte[] bytes) {
		try {
			btOutStream.write(bytes);
		} 
		catch (IOException e) {
			Log.e("ManageConnectedThread", "Error while writing output to server application.");
		}
	}
	
	public void cancel() {
		try {
			btOutStream.write(EXIT_CMD);
			btOutStream.close();
			btInStream.close();
			btSocket.close();
			mCallback.changeToDisconnected();
		}
		catch(IOException e) {
			Log.e("ManageConnectedThread", "Failed to close the bluetooth socket.");
		}
	}

	public void write(int output){
		try{
			btOutStream.write(output);
		}
		catch (IOException e) {
			Log.e("ManageConnectedThread", "Error while writing output to server application.");
		}
	}
}

