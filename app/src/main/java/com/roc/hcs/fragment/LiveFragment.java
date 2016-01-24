package com.roc.hcs.fragment;

import com.roc.hcs.R;
import com.roc.hcs.activity.DetailActivity;
import com.roc.hcs.adapter.LiveGridAdapter;
import com.roc.hcs.adapter.MyGridView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class LiveFragment extends Fragment {
	private MyGridView gv_live;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View live = inflater.inflate(R.layout.fragment_live, container, false);

		gv_live = (MyGridView) live.findViewById(R.id.gv_live);
		gv_live.setAdapter(new LiveGridAdapter(getActivity()));
		//添加点击事件
		gv_live.setOnItemClickListener(
				new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						gotoDetail(arg2);
					}
				}
		);

		return live;
	}
	private void gotoDetail(int arg)
	{
		String[] arr_live = getResources().getStringArray(R.array.live);
		String[] arr_live_url = getResources().getStringArray(R.array.live_url);
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra("title", arr_live[arg]);
		intent.putExtra("url", arr_live_url[arg]);
		intent.putExtra("back", "Live");
		startActivity(intent);
	}
}