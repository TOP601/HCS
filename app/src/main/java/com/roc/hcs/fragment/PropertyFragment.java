package com.roc.hcs.fragment;

import com.roc.hcs.activity.PropertyRepairActivity;
import com.roc.hcs.R;
import com.roc.hcs.adapter.MyGridView;
import com.roc.hcs.adapter.PropertyGridAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class PropertyFragment extends Fragment {
	private MyGridView gv_property;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View property = inflater.inflate(R.layout.fragment_property, container, false);
		gv_property = (MyGridView) property.findViewById(R.id.gv_property);
		gv_property.setAdapter(new PropertyGridAdapter(getActivity()));
		//添加点击事件
		gv_property.setOnItemClickListener(
				new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						gotoDetail(arg2);
					}
				}
		);
		return property;
	}
	private void gotoDetail(int arg)
	{
		if(arg==0){
		Intent intent = new Intent(getActivity(), PropertyRepairActivity.class);
		startActivity(intent);
		}
	}
}