package com.example.projectbmak;

public class BTConnectionFactory {
	
	private static ConnectThread storedConnection;

	public static void setConnection(ConnectThread btConnection) {
		storedConnection = btConnection;
		
	}
	
	public static ConnectThread getConnection(){
		
		return storedConnection;
	}
}
