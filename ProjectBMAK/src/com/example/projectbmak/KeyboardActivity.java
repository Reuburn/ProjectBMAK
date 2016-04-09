package com.example.projectbmak;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class KeyboardActivity extends KeyboardInputOverrideActivity
		implements FKeyFragment.OnFragmentInteractionListener, ArrowKeysFragment.OnFragmentInteractionListener, SpecialFunctionFragment.OnFragmentInteractionListener{
	
	private Button keyboardToggleButton;
	
	private InputMethodManager imm;
	
	private boolean keyboardActive = false;
	
	private View fragmentContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyboard);
		fragmentContainer = findViewById(R.id.container);
		btConnection = BTConnectionFactory.getConnection();
		imm = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE);
		keyboardToggleButton = (Button) findViewById(R.id.btn_keyboardToggle);
		keyboardToggleButton.setOnEditorActionListener(new OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (event.getAction()==KeyEvent.ACTION_DOWN && event.getKeyCode()==KeyEvent.KEYCODE_ENTER){
					Log.e("WEDIDIT","WOOOOOO");
					btConnection.btConnectedThread.write(33);
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.keyboard, menu);
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
	
	public void showKeyboard(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		fragmentContainer.setVisibility(View.GONE);
			if (imm!=null){
				if (keyboardActive){
					hideKeyboard();
				}
				else {
					imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
					keyboardActive = true;
				}
			}		
	}
	
	public void hideKeyboard(){
		if (keyboardActive){
			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			keyboardActive = false;
		}
	}
	
	public void showFunctionKeys(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		fragmentContainer.setVisibility(View.VISIBLE);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.container, new FKeyFragment());
		ft.commitAllowingStateLoss();
		
		if (imm.isActive()){
			hideKeyboard();
		}
	}
	
	public void showSpecialFunctionKeys(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		fragmentContainer.setVisibility(View.VISIBLE);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.container, new SpecialFunctionFragment());
		ft.commitAllowingStateLoss();
		
		if (imm.isActive()){
			hideKeyboard();
		}
	}
	
	public void showArrowKeys(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		fragmentContainer.setVisibility(View.VISIBLE);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.container, new ArrowKeysFragment());
		ft.commitAllowingStateLoss();
		
		if (imm.isActive()){
			hideKeyboard();
		}
	}
	
	public void onF1Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(58);
	}
	
	public void onF2Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(59);
	}
	
	public void onF3Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(60);
	}
	
	public void onF4Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(61);
	}
	
	public void onF5Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(62);
	}
	
	public void onF6Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(63);
	}
	
	public void onF7Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(64);
	}
	
	public void onF8Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(65);
	}
	
	public void onF9Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(66);
	}
	
	public void onF10Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(67);
	}
	
	public void onF11Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(68);
	}
	
	public void onF12Click(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(69);
	}
	
	public void onEscapeClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(74);
	}
	
	public void onTabClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(89);
	}
	
	public void onCapsLockClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(37);
	}
	
	public void onShiftClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(36);
	}
	
	public void onCtrlClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(48);
	}
	
	public void onAltClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(49);
	}
	
	public void onPrtScrClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(88);
	}
	
	public void onWinKeyClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(57);
	}
	
	public void onAltGrClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(48);
		btConnection.btConnectedThread.write(49);
	}
	
	public void onInsertClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(55);
	}
	
	public void onHomeClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(51);
	}
	
	public void onPageUpClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(53);
	}
	
	public void onDeleteClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(50);
	}
	
	public void onEndClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(52);
	}
	
	public void onPageDownClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(54);
	}
	
	public void onUpArrowClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(70);
	}
	
	public void onLeftArrowClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(71);
	}
	
	public void onRightArrowClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(72);
	}
	
	public void onDownArrowClick(View v){
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		btConnection.btConnectedThread.write(73);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}
}
