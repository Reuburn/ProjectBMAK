package com.example.projectbmak;

import android.util.Log;

public class MouseMover {
	
	public enum MouseSpeed{
		VERYFAST(1.3f, 9),
		FAST(1.1f, 7),
		MEDIUM(0.9f, 5),
		SLOW(0.7f, 3),
		VERYSLOW(0.5f, 1);	
		
		private float threshold;
		private int repetitions;
		
		private MouseSpeed(float threshold, int repetitions) {
			this.threshold = threshold;
			this.repetitions = repetitions;
		}
		
		public static int getSpeed(float lastCoordinate, Direction direction) {
			DirectionType directionType = direction.getType();
			for(MouseSpeed mouseSpeed : MouseSpeed.values()) {
				if(DirectionType.POSITIVE.equals(directionType)) {
					if(lastCoordinate > mouseSpeed.threshold) {
						return mouseSpeed.repetitions;
					}
				} else {
					if(lastCoordinate < - mouseSpeed.threshold) {
						return mouseSpeed.repetitions;
					}
				}
			}
			return 0;
		}
	}
	
	public enum Direction {
		UP(203, DirectionType.NEGATIVE),
		DOWN(202, DirectionType.POSITIVE),
		LEFT(200, DirectionType.POSITIVE),
		RIGHT(201, DirectionType.NEGATIVE);
		
		private int data;
		private DirectionType directionType;
		
		private Direction(int data, DirectionType directionType) {
			this.data = data;
			this.directionType = directionType;
		}
		
		public int getData() {
			return data;
		}
		
		public DirectionType getType() {
			return directionType;
		}		
	}
	
	public enum DirectionType {
		POSITIVE,
		NEGATIVE;			
	}
	
	private ManageConnectedThread btConnectedThread;
	private MouseActivity mouseActivity;
	
	public MouseMover(ManageConnectedThread btConnectedThread, MouseActivity mouseActivity){
		this.btConnectedThread = btConnectedThread;
		this.mouseActivity = mouseActivity;
	}
	
	public void moveMouse(float mLastX, float mLastY){
		accelerateMouse(Direction.UP, MouseSpeed.getSpeed(mLastY, Direction.UP));
		accelerateMouse(Direction.DOWN, MouseSpeed.getSpeed(mLastY, Direction.DOWN));
		accelerateMouse(Direction.LEFT, MouseSpeed.getSpeed(mLastX, Direction.LEFT));
		accelerateMouse(Direction.RIGHT, MouseSpeed.getSpeed(mLastX, Direction.RIGHT));
	}

	private void accelerateMouse(Direction direction, int speed) {
		for(int i=0;i<speed;i++) {
			handleDataTransfer(direction.getData());
		}
	}
	
	private void handleDataTransfer(int data){
		try{
			btConnectedThread.write(data);
		}
		catch (Exception e){
			Log.e("MouseMover", "No server application connected to receive output.");
			mouseActivity.finish();
		}
	}
}
