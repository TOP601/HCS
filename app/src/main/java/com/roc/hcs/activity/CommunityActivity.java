package com.roc.hcs.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.roc.hcs.im.ChatMsgAdapter;
import com.roc.hcs.im.ChatMsgEntity;
import com.roc.hcs.im.FaceRelativeLayout;
import com.roc.hcs.R;
import com.roc.hcs.xmpp.ConnecMethod;
import com.roc.hcs.xmpp.DemoApplication;
import com.roc.hcs.xmpp.OwnerListActivity;
import com.roc.hcs.xmpp.XmppTool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class CommunityActivity extends HorizontalActivity implements OnClickListener{
	private ImageView iv_back,iv_community;
	private TextView tv_title;
	private Button mBtnSend;

	private EditText mEditTextContent;

	private ListView mListView;

	private ChatMsgAdapter mAdapter;

	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private MultiUserChat muc;
	private XMPPConnection con;
	private int flag=0;
	private String rec_mes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_layout_chat);
		init();
	}
	private void init(){
		iv_back = (ImageView)findViewById(R.id.iv_back);
		iv_back.setColorFilter(Color.RED);
		iv_community = (ImageView)findViewById(R.id.iv_community);
		iv_community.setColorFilter(Color.RED);
		iv_community.setVisibility(View.VISIBLE);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("业主群");
		iv_back.setOnClickListener(this);
		iv_community.setOnClickListener(this);

		initView();
		initData();

		accountLogin();

	}
	private Handler insHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				// 登录成功
				case 0:
					Log.d("Tag", "login suc");
					Toast.makeText(CommunityActivity.this, "您现在处于在线状态！", Toast.LENGTH_SHORT).show();
					joinMultiUserChat("user001", "高景花园", "");
					break;
				// 登录失败
				case 1:
					Log.d("Tag", "login fail");
					break;
				default:
					break;
			}
		};
	};
	/**
	 * 账户登录
	 */
	private void accountLogin() {
		new Thread() {
			public void run() {
				boolean is = ConnecMethod.login("user001", "123");
				if (is) {
					insHandler.sendEmptyMessage(0);
					con = XmppTool.getConnection();
					// 将用户名保存
					DemoApplication.UserName = "user001";
				} else {
					insHandler.sendEmptyMessage(1);
				}
			};
		}.start();
	}
	/**
	 * 加入会议室
	 *
	 * @param user
	 *            昵称
	 * @param password
	 *            会议室密码
	 * @param roomsName
	 *            会议室名
	 */
	public MultiUserChat joinMultiUserChat(String user, String roomsName,
										   String password) {
		if (con == null)
			return null;
		try {
			// 使用XMPPConnection创建一个MultiUserChat窗口
			muc = new MultiUserChat(con, roomsName + "@conference." + "139.196.21.14");
			// 聊天室服务将会决定要接受的历史记录数量
			DiscussionHistory history = new DiscussionHistory();
			history.setMaxChars(0);
			// history.setSince(new Date());
			// 用户加入聊天室
			muc.join(user, password, history, SmackConfiguration.getPacketReplyTimeout());
			Log.i("MultiUserChat", "会议室【" + roomsName + "】加入成功........");
			Toast.makeText(CommunityActivity.this, "您已加入会议室【高景花园】！", Toast.LENGTH_SHORT).show();
			muc.addMessageListener(new PacketListener() {
				@Override
				public void processPacket(Packet packet) {
					if(flag==0) {
						Message message = (Message) packet;
						rec_mes = message.getBody();
						get(rec_mes);
					}else{
						flag=0;
					}
				}
			});
			return muc;
		} catch (XMPPException e) {
			e.printStackTrace();
			Log.i("MultiUserChat", "会议室【"+roomsName+"】加入失败........");
			return null;
		}
	}
	public void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	}

	private String[] msgArray = new String[] {
			"[媚眼]测试啦[媚眼]",
			"测试啦",
			"测试啦",
			"测试啦",
			"测试啦",
			"呜呜[苦逼]",
			"[惊讶]怎么啦？",
			"嘿嘿[胜利]",
			"测试啦" };

	private String[] dataArray = new String[] { "2012-12-12 12:00",
			"2012-12-12 12:10", "2012-12-12 12:11", "2012-12-12 12:20",
			"2012-12-12 12:30", "2012-12-12 12:35", "2012-12-12 12:40",
			"2012-12-12 12:50", "2012-12-12 12:50" };

	private final static int COUNT = 8;

	public void initData() {
		for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setName("张三");
				entity.setMsgType(true);
			} else {
				entity.setName("李四");
				entity.setMsgType(false);
			}

			entity.setText(msgArray[i]);
			mDataArrays.add(entity);
		}

		mAdapter = new ChatMsgAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_back:
				Intent intent = new Intent(CommunityActivity.this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				finish();
				break;
			case R.id.btn_send:
				send();
				break;
			case R.id.iv_community:
				Intent intent2 = new Intent(CommunityActivity.this,OwnerListActivity.class);
				startActivity(intent2);
				break;
			default:
				break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& ((FaceRelativeLayout) findViewById(R.id.FaceRelativeLayout))
				.hideFaceView()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			flag=1;
			try {
				muc.sendMessage(contString);
			} catch (XMPPException e) {
				e.printStackTrace();
			}
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setMsgType(false);
			entity.setText(contString);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);

		}
	}
	private void get(String msg){
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setDate(getDate());
		entity.setMsgType(true);
		entity.setText(msg);

		mDataArrays.add(entity);
		mAdapter.notifyDataSetChanged();

		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		mEditTextContent.setText(msg);
		mListView.setSelection(mListView.getCount() - 1);//移动到尾部
		Toast.makeText(CommunityActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);

		return sbBuffer.toString();
	}
//	// 重写onKeyDown
//	public  boolean  onKeyDown ( int  keyCode, KeyEvent event) {
//		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//			Intent intent = new Intent(CommunityActivity.this,MainActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			intent.putExtra("ID", "4");
//			startActivity(intent);
//			finish();
//			return true;
//		}
//		return  super .onKeyDown(keyCode, event);
//	}
}
