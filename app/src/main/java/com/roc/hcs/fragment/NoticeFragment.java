package com.roc.hcs.fragment;

import com.roc.hcs.adapter.FragAdapter;
import com.roc.hcs.R;
import com.roc.hcs.utils.CommonHelper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment implements View.OnClickListener {
	private RadioButton main_rb_unread, main_rb_read;
	private ListViewFragment noticePropertyFragment;
	private ListViewFragment noticeGovFragment;
	private ViewPager vp;
	private View notice;
	//底部滑动线条
	private View buttomView;
	//当前屏幕宽度
	private int ActivityWidth;
	//当前停留的位置
	private int currentId = 0;
	final private int width=300,height=300;
	private int flag_check = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		notice = inflater.inflate(R.layout.fragment_notice, container, false);
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		ActivityWidth = wm.getDefaultDisplay().getWidth();

		main_rb_unread = (RadioButton) notice.findViewById(R.id.main_rb_unread);
		main_rb_read = (RadioButton) notice.findViewById(R.id.main_rb_read);

		Resources res=notice.getResources();
		Bitmap bmp_unread= BitmapFactory.decodeResource(res, R.drawable.i_msg_unread);
		bmp_unread = CommonHelper.zoomImage(bmp_unread, width, height);
		bmp_unread=CommonHelper.getAlphaBitmap(bmp_unread, Color.RED);
		Drawable drawable_unread =new BitmapDrawable(bmp_unread);
		main_rb_unread.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_unread, null, null, null);

		Bitmap bmp_read= BitmapFactory.decodeResource(res, R.drawable.i_msg_read);
		bmp_read = CommonHelper.zoomImage(bmp_read, width, height);
		Drawable drawable_read =new BitmapDrawable(bmp_read);
		main_rb_read.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_read, null, null, null);

		main_rb_unread.setOnClickListener(this);
		main_rb_read.setOnClickListener(this);

		noticePropertyFragment = new ListViewFragment();
		Bundle arg_property = new Bundle();
		arg_property.putString("noticeType", "property");
		noticePropertyFragment.setArguments(arg_property);
		noticeGovFragment = new ListViewFragment();
		Bundle arg_gov = new Bundle();
		arg_gov.putString("noticeType", "gov");
		noticeGovFragment.setArguments(arg_gov);
		//构造适配器
		List<Fragment> fragments=new ArrayList<Fragment>();
		fragments.add(noticePropertyFragment);
		fragments.add(noticeGovFragment);
//		FragAdapter adapter = new FragAdapter(getActivity().getSupportFragmentManager(), fragments);
		FragAdapter adapter = new FragAdapter(getChildFragmentManager(), fragments);

		//设定适配器
		vp = (ViewPager)notice.findViewById(R.id.vPager);
		vp.setAdapter(adapter);
		vp.setCurrentItem(currentId);
		vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageSelected(int position) {
				slideView(position);
				currentId = position;
				switch (position)
				{
					case 0:
						setCheck(main_rb_unread);
						break;
					case 1:
						setCheck(main_rb_read);
						break;
				}
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			public void onPageScrollStateChanged(int arg0) {}
		});
		initButtomView();
		return notice;
	}
	private void setCheck(RadioButton rb)
	{
		rb.setChecked(true);
		Resources res=getResources();
		Drawable drawable=null;
		Bitmap bmp=null;
		switch(flag_check){
			case 1:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_msg_unread);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				drawable =new BitmapDrawable(bmp);
				main_rb_unread.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
				break;
			case 2:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_msg_read);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				drawable =new BitmapDrawable(bmp);
				main_rb_read.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
				break;
		}
		switch(rb.getId())
		{
			case R.id.main_rb_unread:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_msg_unread);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				bmp= CommonHelper.getAlphaBitmap(bmp, Color.RED);
				drawable =new BitmapDrawable(bmp);
				main_rb_unread.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
				flag_check=1;
				break;
			case R.id.main_rb_read:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_msg_read);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				bmp=CommonHelper.getAlphaBitmap(bmp, Color.RED);
				drawable =new BitmapDrawable(bmp);
				main_rb_read.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
				flag_check=2;
				break;
		}
	};
	//初始化底部滑动线条
	private void initButtomView() {
		//初始化宽高
		buttomView = (View)notice.findViewById(R.id.v_buttom);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActivityWidth / 2,10);
		buttomView.setLayoutParams(params);

		//初始化颜色
		buttomView.setBackgroundColor(Color.RED);
		slideView(currentId);
	}
	//底部滑动， positionId， 你要滑动到的位置
	private void slideView(int positionId){
		TranslateAnimation tran = null;
		tran = new TranslateAnimation(calcPosition(currentId),calcPosition(positionId),0,0);
		tran.setDuration(300);
		tran.setFillAfter(true);
		buttomView.startAnimation(tran);
	}
	//计算位置     position 需要移动到的位置
	private int calcPosition(int positionId){
		int position = ActivityWidth/2;

		int currentPosition = 0;

		for (int i = 0; i < positionId; i++) {
			currentPosition += position;
		}
		return currentPosition;
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.main_rb_property) {
			vp.setCurrentItem(0);
		}
		if (v.getId() == R.id.main_rb_notice) {
			vp.setCurrentItem(1);
		}
	}

}
