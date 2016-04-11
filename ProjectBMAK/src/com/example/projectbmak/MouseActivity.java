package com.example.projectbmak;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MouseActivity extends Activity implements SensorEventListener{
	
	protected ConnectThread btConnection;
	private float mLastX, mLastY;
	private boolean initialised;
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private Sensor proxSensor;
	private float scrollOldY;
	private float scrollNewY;
	private boolean mouseActive;
	private MouseMover mouseMover;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			btConnection = BTConnectionFactory.getConnection();
		}
		catch(Exception e){
			Log.e("MouseActivity", "Not connected to the server application.");
		}
		
		try{
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
			proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		}
		catch(Exception e){
			Log.e("MouseActivity","Accelerometer or Light Sensor not available.");
			sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
			sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		setContentView(R.layout.activity_mouse);
		
		View leftMouse = findViewById(R.id.view_mouseLeft);
		View rightMouse = findViewById(R.id.view_mouseRight);
		View middleMouse = findViewById(R.id.view_mouseWheel);
		try{
			mouseMover = new MouseMover(btConnection.btConnectedThread, this);
		}
		catch(Exception e){
			Log.e("MouseActivity", "No server application connected to receive output.");
			this.finish();
		}
		
		leftMouse.setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View view, MotionEvent event) {
				
				switch (event.getAction()) {
		            case MotionEvent.ACTION_DOWN:
		            	leftClick();
		            	return true;
		            case MotionEvent.ACTION_MOVE:
		            	return true;
		            case MotionEvent.ACTION_UP:
		            	leftClickRelease();
		            	view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		            	return true;
	            }
				return true;
		    }
		});
		
		rightMouse.setOnTouchListener(new OnTouchListener(){
			public boolean onTouch(View view, MotionEvent event) {
				
				switch (event.getAction()) {
		            case MotionEvent.ACTION_DOWN:
		            	rightClick();
		            	return true;
		            case MotionEvent.ACTION_MOVE:
		            	return true;
		            case MotionEvent.ACTION_UP:
		            	rightClickRelease();
		            	view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		            	return true;
				}
				
				return true;
			}
		});
		
		middleMouse.setOnTouchListener(new OnTouchListener(){
			public boolean onTouch(View view, MotionEvent event) {
				
				switch (event.getAction()) {
		            case MotionEvent.ACTION_DOWN:
		            	scrollOldY = event.getY();
		            	return true;
		            case MotionEvent.ACTION_MOVE:
		            	return true;
		            case MotionEvent.ACTION_UP:
		            	scrollNewY = event.getY();
		            	scrollWheel(scrollOldY, scrollNewY);
		            	view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		            	return true;
				}
				
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mouse, menu);
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
	
	@Override
	public void onResume(){
		super.onResume();
		
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
			if(event.values[0] == 8.0) {
				mouseActive = false;
			}
			if(event.values[0] == 0.0) {
				mouseActive = true;
			}
		}
		
		if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && mouseActive){
			float x = event.values[0];
			float y = event.values[1];
			
			if (!initialised){
				mLastX = x;
				mLastY = y;
				
				initialised = true;
			}
			else {
				mLastX = x;
				mLastY = y;
				
				mouseMover.moveMouse(mLastX, mLastY);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	
	public void leftClick(){
		handleDataTransfer(204);
	}
	
	public void leftClickRelease(){
		handleDataTransfer(207);
	}
	
	public void rightClick(){
		handleDataTransfer(206);
	}
	
	public void rightClickRelease(){
		handleDataTransfer(209);
	}
	
	public void scrollWheel(float oldY, float newY){
		if (newY == oldY){
			middleClick(); 
			middleClickRelease();
		}
		if (newY < oldY) scrollUp(oldY, newY);
		if (newY > oldY) scrollDown(oldY, newY);
	}
	
	public void scrollUp(float oldY, float newY){
		int yDifference = Math.round(oldY) - Math.round(newY);
		scrollUpCommand(yDifference/5);
	}
	
	public void scrollUpCommand(int iterations){
		for (int i = 0; i < iterations; i++){
			handleDataTransfer(210);
		}
	}
	
	public void scrollDown(float oldY, float newY){
		int yDifference = Math.round(newY) - Math.round(oldY);
		scrollDownCommand(yDifference/5);
	}
	
	public void scrollDownCommand(int iterations){
		for (int i = 0; i < iterations; i++){
			handleDataTransfer(211);
		}
	}
	
	public void middleClick(){
		handleDataTransfer(205);
	}
	
	public void middleClickRelease(){
		handleDataTransfer(208);
	}
	
	private void handleDataTransfer(int data){
		try{
			btConnection.btConnectedThread.write(data);
		}
		catch (Exception e){
			Log.e("MouseActivity", "No server application connected to receive output.");
			MouseActivity.this.finish();
		}
	}
	
}
