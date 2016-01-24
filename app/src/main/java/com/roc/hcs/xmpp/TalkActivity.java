package com.roc.hcs.xmpp;

import java.io.File;

import com.roc.hcs.R;
import com.roc.hcs.xmpp.ConnecMethod;
import com.roc.hcs.xmpp.DemoApplication;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 聊天界面
 * 
 * @author jin
 * 
 */
public class TalkActivity extends Activity implements OnClickListener {
	private String Tag = "TalkAcy";
	private ListView listView;

	// 广播接受者
	protected BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if ("msg_receiver".equals(intent.getAction())) {
				Log.d(Tag, "msg");
				if (null != intent.getExtras()) {
					((EditText) findViewById(R.id.et_talk)).setText(intent
							.getExtras().getString("msg"));
				}
			} else if ("file_receiver".equals(intent.getAction())) {
				if (null != intent.getExtras()) {
					((EditText) findViewById(R.id.et_talk)).setText(intent
							.getExtras().getString("path"));
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acy_talks);
		regisBroadcast();
		initView();
	}

	/**
	 * 注册广播
	 */
	private void regisBroadcast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("msg_receiver");
		filter.addAction("file_receiver");
		this.registerReceiver(this.receiver, filter);
	}

	private void initView() {
		findViewById(R.id.btn_sendmsg).setOnClickListener(this);
		findViewById(R.id.btn_sendimg).setOnClickListener(this);

		listView = (ListView) findViewById(R.id.lv_talks);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 发送文本消息
		case R.id.btn_sendmsg:
			String txt = ((EditText) findViewById(R.id.et_talk)).getText()
					.toString();
			if (0 < txt.length()) {
				ConnecMethod.sendTalkMsg(DemoApplication.FromName, txt);
			}
			break;
		// 发送图片
		case R.id.btn_sendimg:
			String filepath = ((EditText) findViewById(R.id.et_talk)).getText()
					.toString();
			ConnecMethod.sendTalkFile(DemoApplication.FromName, filepath);
			break;

		default:
			break;
		}
	}
}
