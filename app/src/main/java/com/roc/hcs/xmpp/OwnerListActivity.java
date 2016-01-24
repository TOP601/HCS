package com.roc.hcs.xmpp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.roc.hcs.activity.CommunityActivity;
import com.roc.hcs.activity.MainActivity;
import com.roc.hcs.xmpp.FriendsAdapter;
import com.roc.hcs.xmpp.ConnecMethod;
import com.roc.hcs.xmpp.ConnecService;
import com.roc.hcs.R;
import com.roc.hcs.xmpp.DemoApplication;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s1112001 on 2015/11/23.
 */
public class OwnerListActivity extends Activity implements View.OnClickListener {
    private String Tag = "FriendsAcy";
    private List<RosterEntry> rosterEntries = new ArrayList<RosterEntry>();
    private FriendsAdapter adap;
    private Button btn_community;
    private ImageView iv_back;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_list);
        initView();
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setColorFilter(Color.RED);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("群信息");
        iv_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(OwnerListActivity.this,CommunityActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
    private Handler insHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    adap.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    private void initView() {
        getOffLine();
        // 开启服务 监听服务器
        Intent intent = new Intent(this, ConnecService.class);
        startService(intent);

        ListView frilistview = (ListView) findViewById(R.id.lv_friends);
        adap = new FriendsAdapter(this, rosterEntries);
        frilistview.setAdapter(adap);
        frilistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View conView,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                DemoApplication.FromName = ((FriendsAdapter.ViewHolder) conView
                        .getTag()).fName.getText().toString();
                Log.d(Tag, arg2 + ";" + DemoApplication.FromName);

                Intent intent = new Intent(OwnerListActivity.this, TalkActivity.class);
                startActivity(intent);
            }
        });
        getFriends();
    }
    /**
     * 获取所有好友的信息
     */
    private void getFriends() {
        new Thread() {
            public void run() {
                rosterEntries.clear();
                // 获取所有好友，不分组
                // for (RosterEntry enty : ConnecMethod.searchAllFriends()) {
                // rosterEntries.add(enty);
                // }

                // 获取所有好友，分组
                for (RosterGroup group : ConnecMethod.searchAllGroup()) {
                    Log.d(Tag, group.getName() + "");
                    for (RosterEntry enty : ConnecMethod.getGroupFriends(group
                            .getName())) {
                        enty.setName(group.getName());
                        rosterEntries.add(enty);
                    }
                }
                insHandler.sendEmptyMessage(0);
            };
        }.start();
    }
    /**
     * 离线消息
     */
    private void getOffLine() {
        new Thread() {
            public void run() {
                List<org.jivesoftware.smack.packet.Message> msglist = ConnecMethod
                        .getOffLine();
                for (org.jivesoftware.smack.packet.Message msg : msglist) {
                    Log.d(Tag, msg.getTo() + msg.getBody() + msg.getFrom());
                }
            };
        }.start();
    }
}
