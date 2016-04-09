package com.example.projectbmak;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ManageConnectedThread extends Thread {
	private final BluetoothSocket btSocket;
	private final InputStream btInStream;
	private final OutputStream btOutStream;
	public static final int EXIT_CMD = -1;
	public static final int VOL_UP_PRESS = 1;
	public static final int VOL_UP_HELD = 2;
	public static final int VOL_UP_RELEASED = 3;
	public static final int VOL_DOWN_PRESS = 4;
	public static final int VOL_DOWN_HELD = 5;
	public static final int VOL_DOWN_RELEASED = 6;
	
	public ManageConnectedThread(BluetoothSocket btSocket2){
		
		btSocket = btSocket2;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		
		try {
			tmpIn = btSocket2.getInputStream();
			tmpOut = btSocket2.getOutputStream();
		} 
		catch(IOException e) {
			
		}
		
		btInStream = tmpIn;
		btOutStream = tmpOut;
		
	}
	
	public void run() {
		Log.i("MNGCONNTHREAD", "ManageConnectedThread started");
		byte[] buffer = new byte[1024];
		
		while (true){
			try {
				int bytes = btInStream.read(buffer);
				btHandler.obtainMessage(MainActivity.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
			}
			catch(IOException e) {
				Log.e("MNGCONNTHREAD", "Disconnected", e);
				break;
			}
		}
	}
	
	public void write(byte[] bytes) {
		try {
			btOutStream.write(bytes);
		} 
		catch (IOException e) {
			Log.e("MNGCONNTHREAD", "Error while writing.", e);
		}
	}
	
	public void cancel() {
		try {
			btOutStream.write(EXIT_CMD);
			btSocket.close();
		}
		catch(IOException e) {
			Log.e("MNGCONNTHREAD", "closing socket failed", e);
		}
	}
	
	static Handler btHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			byte[] writeBuf = (byte[]) msg.obj;
			int begin = (int)msg.arg1;
			int end = (int)msg.arg2;
			
			switch(msg.what) {
			case 1:
				String writeMessage = new String(writeBuf);
				writeMessage = writeMessage.substring(begin, end);
				break;
			}
		}
	};

	public void write(int output) {
		try{
			btOutStream.write(output);
		}
		catch (IOException e) {
			Log.e("MNGCONNTHREAD","Exception during write", e);
		}
		
	}
}

