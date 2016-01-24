package com.roc.hcs.activity;

import java.text.SimpleDateFormat;

import com.roc.hcs.R;
import com.roc.hcs.utils.SPUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

	private EditText login_edt_username,login_edt_password;
	private CheckBox login_checkbox_me;
	private Button login_btn_register,login_btn_login;
	private InputMethodManager manager;
	//定义对象
	private static SharedPreferences mPreferences;
	private static SharedPreferences.Editor mEditor;
	private long exitTime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		login_edt_username =(EditText)findViewById(R.id.login_edt_username);
		login_edt_password =(EditText)findViewById(R.id.login_edt_password);		
		login_checkbox_me = (CheckBox)findViewById(R.id.login_checkbox_me);
		login_btn_register = (Button)findViewById(R.id.login_btn_register);
		login_btn_login = (Button)findViewById(R.id.login_btn_login);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		login_edt_username.setOnClickListener(this);
		login_edt_password.setOnClickListener(this);
		login_checkbox_me.setOnClickListener(this);
		login_btn_register.setOnClickListener(this);
		login_btn_login.setOnClickListener(this);
				
		if (SPUtils.get(this, "USERNAME", "").toString() != "") {
			login_edt_username.setText(SPUtils.get(this, "USERNAME", "").toString());
		}
		if (SPUtils.get(this, "PASSWORD", "").toString() != "") {
			login_edt_password.setText(SPUtils.get(this, "PASSWORD", "").toString());
		}
				
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay();
		int height = (int) d.getHeight();
		int width = (int) d.getWidth();
		SPUtils.put(this, "SCREEN_HEIGHT", height);
		SPUtils.put(this, "SCREEN_WIDTH", width);
		
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		String year = date.substring(0,4);
		String month = date.substring(5,7);
		String day = date.substring(8,10);
		
		SPUtils.put(this, "STATISTICS_DATE", year+"/"+month+"/"+day);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.login_edt_username) {
			login_checkbox_me.setChecked(false);
		}
		if (v.getId() == R.id.login_edt_password) {
			login_checkbox_me.setChecked(false);
		}
		if(v.getId() == R.id.login_checkbox_me){
			if(login_checkbox_me.isChecked() && login_edt_username.getText().toString().trim() != "" && login_edt_password.getText().toString().trim() != ""){
				SPUtils.put(this, "USERNAME", login_edt_username.getText().toString().trim());
				SPUtils.put(this, "PASSWORD", login_edt_password.getText().toString().trim());
			}
		}
		if (v.getId() == R.id.login_btn_register) {
			Intent intent = new Intent(this,RegisterActivity.class);
			startActivity(intent);
			finish();
		}
		if (v.getId() == R.id.login_btn_login) {
			if ("123".equals(login_edt_username.getText().toString().trim()) && "123".equals(login_edt_password.getText().toString().trim())) {

				mPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
				mEditor = mPreferences.edit();
				mEditor.putInt("userid", 1);
				mEditor.commit();

				Intent intent = new Intent(this,MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
			}
			
		}
		
	}
	// 重写onKeyDown
	public  boolean  onKeyDown ( int  keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				mPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
				mEditor = mPreferences.edit();
//存值
//RECORD 是标记
//index 是值
				mEditor.putInt("userid", 0);
//提交
				mEditor.commit();
				finish();
				System.exit(0);
			}
			return true;
		}
		return  super .onKeyDown(keyCode, event);
	}
}
