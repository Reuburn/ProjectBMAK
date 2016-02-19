package com.example.projectbmak;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;

public interface BluetoothSocketWrapper {
	InputStream getInputStream() throws IOException;
	OutputStream getOutputStream() throws IOException;
	String getRemoteDeviceName();
	void connect() throws IOException;
	String getRemoteDeviceAddress();
	void close() throws IOException;
	BluetoothSocket getUnderlyingSocket();

}