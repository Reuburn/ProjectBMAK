package com.example.projectbmak;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
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
	private final int MOUSELEFT = 200;
	private final int MOUSERIGHT = 201;
	private final int MOUSEDOWN = 202;
	private final int MOUSEUP = 203;
	private final float VERYSLOW = (float)0.5;
	private final float SLOW = (float)0.7;
	private final float MEDIUM = (float) 0.9;
	private final float FAST = (float) 1.1;
	private final float VERYFAST = (float) 1.3;
	private float scrollOldY;
	private float scrollNewY;
	private boolean mouseActive;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			btConnection = BTConnectionFactory.getConnection();
		}
		catch(Exception e){
			Log.i("BTError","No device connected.");
		}
		
		try{
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
			proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		}
		catch(Exception e){
			Log.i("SENSOR_ERROR","Accelerometer or Light Sensor not available.");
			sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
			sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		setContentView(R.layout.activity_mouse);
		
		View leftMouse = findViewById(R.id.view_mouseLeft);
		View rightMouse = findViewById(R.id.view_mouseRight);
		View middleMouse = findViewById(R.id.view_mouseWheel);
		
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

				
		         // Your code here
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
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
			
			if(event.values[0] == 8.0) {
				mouseActive = false;
			}
			if(event.values[0] == 0.0) {
				mouseActive = true;
			}
			Log.i("PROXIMITY", String.valueOf(event.values[0]));
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
							
				if (Math.round(mLastX) > VERYSLOW){
					Log.d("DIRECTION", "Left V Slow");
					btConnection.btConnectedThread.write(MOUSELEFT);
					if(Math.round(mLastX) > SLOW){
						Log.d("DIRECTION", "Left Slow");
						btConnection.btConnectedThread.write(MOUSELEFT);
						btConnection.btConnectedThread.write(MOUSELEFT);
						if (Math.round(mLastX) > MEDIUM){
							Log.d("DIRECTION", "Left Medium");
							btConnection.btConnectedThread.write(MOUSELEFT);
							btConnection.btConnectedThread.write(MOUSELEFT);
							if(Math.round(mLastX) > FAST){
								Log.d("DIRECTION", "Left Fast");
								btConnection.btConnectedThread.write(MOUSELEFT);
								btConnection.btConnectedThread.write(MOUSELEFT);
								if (Math.round(mLastX) > VERYFAST){
									Log.d("DIRECTION", "Left V Fast");
									btConnection.btConnectedThread.write(MOUSELEFT);
									btConnection.btConnectedThread.write(MOUSELEFT);
								}
							}
						}
					}
				}
				
				if (Math.round(mLastX) < - VERYSLOW){
					Log.d("DIRECTION", "Right");				
					btConnection.btConnectedThread.write(MOUSERIGHT);
					if (Math.round(mLastX) < - SLOW){
						btConnection.btConnectedThread.write(MOUSERIGHT);
						btConnection.btConnectedThread.write(MOUSERIGHT);
						if (Math.round(mLastX) < - MEDIUM){
							btConnection.btConnectedThread.write(MOUSERIGHT);
							btConnection.btConnectedThread.write(MOUSERIGHT);
							if(Math.round(mLastX) < - FAST){
								btConnection.btConnectedThread.write(MOUSERIGHT);
								btConnection.btConnectedThread.write(MOUSERIGHT);
								if (Math.round(mLastX) < - VERYFAST){
									btConnection.btConnectedThread.write(MOUSERIGHT);
									btConnection.btConnectedThread.write(MOUSERIGHT);
								}
							}
						}
					}
				}
				
				if (Math.round(mLastY) > VERYSLOW){
					Log.d("DIRECTION", "Down V Slow");
					btConnection.btConnectedThread.write(MOUSEDOWN);
					if(Math.round(mLastY) > SLOW){
						Log.d("DIRECTION", "Down Slow");
						btConnection.btConnectedThread.write(MOUSEDOWN);
						btConnection.btConnectedThread.write(MOUSEDOWN);
						if (Math.round(mLastY) > MEDIUM){
							Log.d("DIRECTION", "Down Medium");
							btConnection.btConnectedThread.write(MOUSEDOWN);
							btConnection.btConnectedThread.write(MOUSEDOWN);
							if(Math.round(mLastY) > FAST){
								Log.d("DIRECTION", "Down Fast");
								btConnection.btConnectedThread.write(MOUSEDOWN);
								btConnection.btConnectedThread.write(MOUSEDOWN);
								if (Math.round(mLastY) > VERYFAST){
									Log.d("DIRECTION", "Down V Fast");
									btConnection.btConnectedThread.write(MOUSEDOWN);
									btConnection.btConnectedThread.write(MOUSEDOWN);
								}
							}
						}
					}	
				}
				
				if (Math.round(mLastY) < - VERYSLOW){
					Log.d("DIRECTION", "Up");				
					btConnection.btConnectedThread.write(MOUSEUP);
					if (Math.round(mLastY) < - SLOW){
						btConnection.btConnectedThread.write(MOUSEUP);
						btConnection.btConnectedThread.write(MOUSEUP);
						if (Math.round(mLastY) < - MEDIUM){
							btConnection.btConnectedThread.write(MOUSEUP);
							btConnection.btConnectedThread.write(MOUSEUP);
							if(Math.round(mLastY) < - FAST){
								btConnection.btConnectedThread.write(MOUSEUP);
								btConnection.btConnectedThread.write(MOUSEUP);
								if (Math.round(mLastY) < - VERYFAST){
									btConnection.btConnectedThread.write(MOUSEUP);
									btConnection.btConnectedThread.write(MOUSEUP);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	public void leftClick(){
		btConnection.btConnectedThread.write(204);
	}
	
	public void leftClickRelease(){
		btConnection.btConnectedThread.write(207);
	}
	
	public void rightClick(){
		btConnection.btConnectedThread.write(206);
	}
	
	public void rightClickRelease(){
		btConnection.btConnectedThread.write(209);
	}
	
	public void scrollWheel(float oldY, float newY){
		Log.i("SCROLLVALUES", "Old Y: " + String.valueOf(oldY) + " New Y: " + String.valueOf(newY));
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
			btConnection.btConnectedThread.write(210);
		}
	}
	
	public void scrollDown(float oldY, float newY){
		int yDifference = Math.round(newY) - Math.round(oldY);
		scrollDownCommand(yDifference/5);
	}
	
	public void scrollDownCommand(int iterations){
		for (int i = 0; i < iterations; i++){
			btConnection.btConnectedThread.write(211);
		}
	}
	
	public void middleClick(){
		btConnection.btConnectedThread.write(205);
	}
	
	public void middleClickRelease(){
		btConnection.btConnectedThread.write(208);
	}
	
}
