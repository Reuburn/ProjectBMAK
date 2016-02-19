package com.example.projectbmak;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ManageConnectedThread extends Thread {
	private final BluetoothSocket btSocket;
	private final InputStream btInStream;
	private final OutputStream btOutStream;
	
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
		byte[] buffer = new byte[1024];
		int begin = 0;
		int bytes = 0;
		
		while (true){
			try {
				bytes += btInStream.read(buffer, bytes, buffer.length - bytes);
				for(int i = begin; i < bytes; i++){
					if (buffer[i] == "#".getBytes()[0]){
						btHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
						begin = i + 1;
						if(i == bytes - 1){
							bytes = 0;
							begin = 0;
						}
					}
				}		
			}
			catch(IOException e) {
				
			}
		}
	}
	
	public void write(byte[] bytes) {
		try {
			btOutStream.write(bytes);
		} 
		catch (IOException e) {
			
		}
	}
	
	public void cancel() {
		try {
			btSocket.close();
		}
		catch(IOException e) {
			
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
}

