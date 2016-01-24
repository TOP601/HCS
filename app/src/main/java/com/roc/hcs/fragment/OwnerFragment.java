package com.roc.hcs.fragment;

import com.roc.hcs.activity.DetailActivity;
import com.roc.hcs.activity.LoginActivity;
import com.roc.hcs.activity.MainActivity;
import com.roc.hcs.activity.SetActivity;
import com.roc.hcs.adapter.OwnerAdapter;
import com.roc.hcs.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnerFragment extends Fragment {
	private ListView lv_owner;
	private ImageButton imageButton;
	private ProgressDialog myDialog;
	final CharSequence strDialogTitle = "提示";
	final CharSequence strDialogBody = "加载中...";
	private static final String[][] str_message = {
			{"1","我的标签"},
			{"2","APP分享"}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View owner = inflater.inflate(R.layout.fragment_owner, container, false);
		lv_owner = (ListView) owner.findViewById(R.id.lv_owner);
		imageButton = (ImageButton)owner.findViewById(R.id.imageButton);
		imageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				setProgress();
				Intent intent = new Intent(getActivity(), SetActivity.class);
				startActivity(intent);
			}
		});
		List<Map<String, Object>> listMassage = getData();
		lv_owner.setAdapter(new OwnerAdapter(getActivity(), listMassage));
		lv_owner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
//                if (list.get(arg2).equals("LinearLayout")) {
//				setProgress();
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				intent.putExtra("title", "我的资料");
				intent.putExtra("url", "http://139.196.21.14:8081/Owner/Detail/1");
				intent.putExtra("back", "Owner");
				startActivity(intent);
//                }
			}

		});
		return owner;
	}
	private void setProgress()
	{
		myDialog = ProgressDialog.show(getActivity(),strDialogTitle, strDialogBody, true);
		new Thread(){
			public void run(){
				try{
					sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					myDialog.dismiss();
				}
			}
		}.start();
	}
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < str_message.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", str_message[i][0]);
			map.put("owner", str_message[i][1]);
			list.add(map);
		}
		return list;
	}
}

