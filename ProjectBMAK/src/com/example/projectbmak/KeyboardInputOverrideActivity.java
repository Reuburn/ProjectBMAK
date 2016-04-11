package com.example.projectbmak;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;

public abstract class KeyboardInputOverrideActivity extends Activity {
	
	protected ConnectThread btConnection;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		try {
			Log.i("KeyboardInputOverrideActivity", String.valueOf(event.getKeyCode()));
		switch (keyCode){
			case KeyEvent.KEYCODE_VOLUME_UP:
				btConnection.btConnectedThread.write(ManageConnectedThread.VOL_UP_PRESS);
				event.startTracking();
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				btConnection.btConnectedThread.write(ManageConnectedThread.VOL_DOWN_PRESS);
				event.startTracking();
				return true;
			case KeyEvent.KEYCODE_A:
				btConnection.btConnectedThread.write(7);
				event.startTracking();
				return true;
			case KeyEvent.KEYCODE_B:
				btConnection.btConnectedThread.write(8);
				return true;
			case KeyEvent.KEYCODE_C:
				btConnection.btConnectedThread.write(9);
				return true;
			case KeyEvent.KEYCODE_D:
				btConnection.btConnectedThread.write(10);
				return true;
			case KeyEvent.KEYCODE_E:
				btConnection.btConnectedThread.write(11);
				return true;
			case KeyEvent.KEYCODE_F:
				btConnection.btConnectedThread.write(12);
				return true;
			case KeyEvent.KEYCODE_G:
				btConnection.btConnectedThread.write(13);
				return true;
			case KeyEvent.KEYCODE_H:
				btConnection.btConnectedThread.write(14);
				return true;
			case KeyEvent.KEYCODE_I:
				btConnection.btConnectedThread.write(15);
				return true;
			case KeyEvent.KEYCODE_J:
				btConnection.btConnectedThread.write(16);
				return true;
			case KeyEvent.KEYCODE_K:
				btConnection.btConnectedThread.write(17);
				return true;
			case KeyEvent.KEYCODE_L:
				btConnection.btConnectedThread.write(18);
				return true;
			case KeyEvent.KEYCODE_M:
				btConnection.btConnectedThread.write(19);
				return true;
			case KeyEvent.KEYCODE_N:
				btConnection.btConnectedThread.write(20);
				return true;
			case KeyEvent.KEYCODE_O:
				btConnection.btConnectedThread.write(21);
				return true;
			case KeyEvent.KEYCODE_P:
				btConnection.btConnectedThread.write(22);
				return true;
			case KeyEvent.KEYCODE_Q:
				btConnection.btConnectedThread.write(23);
				return true;
			case KeyEvent.KEYCODE_R:
				btConnection.btConnectedThread.write(24);
				return true;
			case KeyEvent.KEYCODE_S:
				btConnection.btConnectedThread.write(25);
				return true;
			case KeyEvent.KEYCODE_T:
				btConnection.btConnectedThread.write(26);
				return true;
			case KeyEvent.KEYCODE_U:
				btConnection.btConnectedThread.write(27);
				return true;
			case KeyEvent.KEYCODE_V:
				btConnection.btConnectedThread.write(28);
				return true;
			case KeyEvent.KEYCODE_W:
				btConnection.btConnectedThread.write(29);
				return true;
			case KeyEvent.KEYCODE_X:
				btConnection.btConnectedThread.write(30);
				return true;
			case KeyEvent.KEYCODE_Y:
				btConnection.btConnectedThread.write(31);
				return true;
			case KeyEvent.KEYCODE_Z:
				btConnection.btConnectedThread.write(32);
				return true;
			case KeyEvent.KEYCODE_ENTER:
				btConnection.btConnectedThread.write(33);
				return true;
			case KeyEvent.KEYCODE_DEL:
				btConnection.btConnectedThread.write(34);
				return true;
			case KeyEvent.KEYCODE_SPACE:
				btConnection.btConnectedThread.write(35);
				return true;
			case KeyEvent.KEYCODE_SHIFT_LEFT:
				btConnection.btConnectedThread.write(36);
				return true;
			case KeyEvent.KEYCODE_CAPS_LOCK:
				btConnection.btConnectedThread.write(37);
				return true;
			case KeyEvent.KEYCODE_0:
				btConnection.btConnectedThread.write(38);
				return true;
			case KeyEvent.KEYCODE_1:
				btConnection.btConnectedThread.write(39);
				return true;
			case KeyEvent.KEYCODE_2:
				btConnection.btConnectedThread.write(40);
				return true;
			case KeyEvent.KEYCODE_3:
				btConnection.btConnectedThread.write(41);
				return true;
			case KeyEvent.KEYCODE_4:
				btConnection.btConnectedThread.write(42);
				return true;
			case KeyEvent.KEYCODE_5:
				btConnection.btConnectedThread.write(43);
				return true;
			case KeyEvent.KEYCODE_6:
				btConnection.btConnectedThread.write(44);
				return true;
			case KeyEvent.KEYCODE_7:
				btConnection.btConnectedThread.write(45);
				return true;
			case KeyEvent.KEYCODE_8:
				btConnection.btConnectedThread.write(46);
				return true;
			case KeyEvent.KEYCODE_9:
				btConnection.btConnectedThread.write(47);
				return true;
			case KeyEvent.KEYCODE_CTRL_LEFT:
				btConnection.btConnectedThread.write(48);
				return true;
			case KeyEvent.KEYCODE_ALT_LEFT:
				btConnection.btConnectedThread.write(49);
				return true;
			case KeyEvent.KEYCODE_PAGE_UP:
				btConnection.btConnectedThread.write(53);
				return true;
			case KeyEvent.KEYCODE_PAGE_DOWN:
				btConnection.btConnectedThread.write(54);
				return true;
			case KeyEvent.KEYCODE_INSERT:
				btConnection.btConnectedThread.write(55);
				return true;
			case KeyEvent.KEYCODE_ALT_RIGHT:
				btConnection.btConnectedThread.write(56);
				return true;
			case KeyEvent.KEYCODE_F1:
				btConnection.btConnectedThread.write(58);
				return true;
			case KeyEvent.KEYCODE_F2:
				btConnection.btConnectedThread.write(59);
				return true;
			case KeyEvent.KEYCODE_F3:
				btConnection.btConnectedThread.write(60);
				return true;
			case KeyEvent.KEYCODE_F4:
				btConnection.btConnectedThread.write(61);
				return true;
			case KeyEvent.KEYCODE_F5:
				btConnection.btConnectedThread.write(62);
				return true;
			case KeyEvent.KEYCODE_F6:
				btConnection.btConnectedThread.write(63);
				return true;
			case KeyEvent.KEYCODE_F7:
				btConnection.btConnectedThread.write(64);
				return true;
			case KeyEvent.KEYCODE_F8:
				btConnection.btConnectedThread.write(65);
				return true;
			case KeyEvent.KEYCODE_F9:
				btConnection.btConnectedThread.write(66);
				return true;
			case KeyEvent.KEYCODE_F10:
				btConnection.btConnectedThread.write(67);
				return true;
			case KeyEvent.KEYCODE_F11:
				btConnection.btConnectedThread.write(68);
				return true;
			case KeyEvent.KEYCODE_F12:
				btConnection.btConnectedThread.write(69);
				return true;
			case KeyEvent.KEYCODE_ESCAPE:
				btConnection.btConnectedThread.write(74);
				return true;
			/*case KeyEvent.KEYCODE_VOLUME_MUTE:
				btConnection.btConnectedThread.write(75);
				return true;*/
			case KeyEvent.KEYCODE_PERIOD:
				btConnection.btConnectedThread.write(76);
				return true;
			case KeyEvent.KEYCODE_COMMA:
				btConnection.btConnectedThread.write(77);
				return true;
			case KeyEvent.KEYCODE_SEMICOLON:
				btConnection.btConnectedThread.write(78);
				return true;
			case KeyEvent.KEYCODE_LEFT_BRACKET:
				btConnection.btConnectedThread.write(79);
				return true;
			case KeyEvent.KEYCODE_RIGHT_BRACKET:
				btConnection.btConnectedThread.write(80);
				return true;
			case KeyEvent.KEYCODE_APOSTROPHE:
				btConnection.btConnectedThread.write(81);
				return true;
			case KeyEvent.KEYCODE_GRAVE:
				btConnection.btConnectedThread.write(82);
				return true;
			case KeyEvent.KEYCODE_MINUS:
				btConnection.btConnectedThread.write(83);
				return true;
			case KeyEvent.KEYCODE_EQUALS:
				btConnection.btConnectedThread.write(84);
				return true;
			case KeyEvent.KEYCODE_SLASH:
				btConnection.btConnectedThread.write(85);
				return true;
			case KeyEvent.KEYCODE_BACKSLASH:
				btConnection.btConnectedThread.write(86);
				return true;
			case KeyEvent.KEYCODE_POUND:
				btConnection.btConnectedThread.write(87);
				return true;
			}
		}
		catch(Exception e){
			Log.e("KeyboardInputOverrideActivity", "No server application connected to receive output.");
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		if(keyCode < 24 || keyCode > 25){
			return false;
		}
		
		switch(keyCode){
		case KeyEvent.KEYCODE_VOLUME_UP:
			btConnection.btConnectedThread.write(ManageConnectedThread.VOL_UP_HELD);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			btConnection.btConnectedThread.write(ManageConnectedThread.VOL_DOWN_HELD);
			return true;
		}
		return super.onKeyLongPress(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event){
		switch (keyCode){
		case KeyEvent.KEYCODE_VOLUME_UP:
			btConnection.btConnectedThread.write(ManageConnectedThread.VOL_UP_RELEASED);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			btConnection.btConnectedThread.write(ManageConnectedThread.VOL_DOWN_RELEASED);
			return true;
		case KeyEvent.KEYCODE_A:
			break;
		}
			
		return super.onKeyUp(keyCode, event);
		
	}
}
