package com.roc.hcs.activity;

import com.roc.hcs.adapter.FragAdapter;
import com.roc.hcs.fragment.CommunityFragment;
import com.roc.hcs.fragment.NoticeFragment;
import com.roc.hcs.fragment.LiveFragment;
import com.roc.hcs.fragment.OwnerFragment;
import com.roc.hcs.fragment.PropertyFragment;
import com.roc.hcs.im.FaceConversionUtil;
import com.roc.hcs.R;
import com.roc.hcs.utils.CommonHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {
	public static MainActivity instance = null;
	private RadioButton main_rb_notice, main_rb_property, main_rb_live,
			main_rb_community, main_rb_owner;
	private NoticeFragment noticeFragment;
	private PropertyFragment propertyFragment;
	private LiveFragment liveFragment;
	private CommunityFragment communityFragment;
	private OwnerFragment ownerFragment;
	private TextView main_tip_notice,main_tip_community;
	private long exitTime = 0;
	final private int width=300,height=300;
	private int flag_check = 3;
	private ImageView i_location;
	//View集合
	public List<Fragment> viewPagerFragments = new ArrayList<Fragment>();
	//滑动View控件
	private ViewPager viewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		i_location = (ImageView)findViewById(R.id.i_location);
		i_location.setColorFilter(Color.RED);
		instance = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil.getInstace().getFileText(getApplication());
			}
		}).start();

		main_rb_notice = (RadioButton) findViewById(R.id.main_rb_notice);
		main_rb_property = (RadioButton) findViewById(R.id.main_rb_property);
		main_rb_live = (RadioButton) findViewById(R.id.main_rb_live);
		main_rb_community = (RadioButton) findViewById(R.id.main_rb_community);
		main_rb_owner = (RadioButton) findViewById(R.id.main_rb_owner);

		Resources res=getResources();
		Bitmap bmp_notice= BitmapFactory.decodeResource(res, R.drawable.i_notice);
		bmp_notice = CommonHelper.zoomImage(bmp_notice, width, height);
		Drawable drawable_notice =new BitmapDrawable(bmp_notice);
		main_rb_notice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_notice, null, null);

		Bitmap bmp_property= BitmapFactory.decodeResource(res, R.drawable.i_property);
		bmp_property = CommonHelper.zoomImage(bmp_property, width, height);
		Drawable drawable_property =new BitmapDrawable(bmp_property);
		main_rb_property.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_property, null, null);

		Bitmap bmp_live= BitmapFactory.decodeResource(res, R.drawable.i_live);
		bmp_live = CommonHelper.zoomImage(bmp_live, width, height);
		bmp_live=CommonHelper.getAlphaBitmap(bmp_live, Color.RED);
		Drawable drawable_live =new BitmapDrawable(bmp_live);
		main_rb_live.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_live, null, null);

		Bitmap bmp_community= BitmapFactory.decodeResource(res, R.drawable.i_community);
		bmp_community = CommonHelper.zoomImage(bmp_community, width, height);
		Drawable drawable_community =new BitmapDrawable(bmp_community);
		main_rb_community.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_community, null, null);

		Bitmap bmp_owner= BitmapFactory.decodeResource(res, R.drawable.i_owner);
		bmp_owner = CommonHelper.zoomImage(bmp_owner, width, height);
		Drawable drawable_owner =new BitmapDrawable(bmp_owner);
		main_rb_owner.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_owner, null, null);

		main_rb_notice.setOnClickListener(this);
		main_rb_property.setOnClickListener(this);
		main_rb_live.setOnClickListener(this);
		main_rb_community.setOnClickListener(this);
		main_rb_owner.setOnClickListener(this);

		noticeFragment = new NoticeFragment();
		propertyFragment = new PropertyFragment();
		liveFragment = new LiveFragment();
		communityFragment = new CommunityFragment();
		ownerFragment = new OwnerFragment();

		viewPagerFragments.add(noticeFragment);
		viewPagerFragments.add(propertyFragment);
		viewPagerFragments.add(liveFragment);
		viewPagerFragments.add(communityFragment);
		viewPagerFragments.add(ownerFragment);

		main_tip_notice = (TextView) findViewById(R.id.main_tip_notice);
		main_tip_notice.setVisibility(View.VISIBLE);
		main_tip_notice.setText("11");

		main_tip_community = (TextView) findViewById(R.id.main_tip_community);
		main_tip_community.setVisibility(View.VISIBLE);
		main_tip_community.setText("68");

		viewPager = (ViewPager) findViewById(R.id.vPager);
		viewPager.setOffscreenPageLimit(5);
		FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), viewPagerFragments);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(2);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageSelected(int position) {
				switch (position) {
					case 0:
						setCheck(main_rb_notice);
						break;
					case 1:
						setCheck(main_rb_property);
						break;
					case 2:
						setCheck(main_rb_live);
						break;
					case 3:
						setCheck(main_rb_community);
						break;
					case 4:
						setCheck(main_rb_owner);
						break;
				}
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {}

		});

	}

	private void setCheck(RadioButton rb)
	{
		rb.setChecked(true);
		Resources res=getResources();
		Drawable drawable=null;
		Bitmap bmp=null;
		switch(flag_check){
			case 1:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_notice);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				drawable =new BitmapDrawable(bmp);
				main_rb_notice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				break;
			case 2:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_property);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				drawable =new BitmapDrawable(bmp);
				main_rb_property.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				break;
			case 3:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_live);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				drawable =new BitmapDrawable(bmp);
				main_rb_live.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				break;
			case 4:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_community);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				drawable =new BitmapDrawable(bmp);
				main_rb_community.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				break;
			case 5:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_owner);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				drawable =new BitmapDrawable(bmp);
				main_rb_owner.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				break;
		}
		switch(rb.getId())
		{
			case R.id.main_rb_notice:
				main_tip_notice.setVisibility(View.GONE);
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_notice);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				bmp=CommonHelper.getAlphaBitmap(bmp, Color.RED);
				drawable =new BitmapDrawable(bmp);
				main_rb_notice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				flag_check=1;
				break;
			case R.id.main_rb_property:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_property);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				bmp=CommonHelper.getAlphaBitmap(bmp, Color.RED);
				drawable =new BitmapDrawable(bmp);
				main_rb_property.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				flag_check=2;
				break;
			case R.id.main_rb_live:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_live);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				bmp=CommonHelper.getAlphaBitmap(bmp, Color.RED);
				drawable =new BitmapDrawable(bmp);
				main_rb_live.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				flag_check=3;
				break;
			case R.id.main_rb_community:
				main_tip_community.setVisibility(View.GONE);
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_community);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				bmp=CommonHelper.getAlphaBitmap(bmp, Color.RED);
				drawable =new BitmapDrawable(bmp);
				main_rb_community.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				flag_check=4;
				break;
			case R.id.main_rb_owner:
				bmp= BitmapFactory.decodeResource(res, R.drawable.i_owner);
				bmp = CommonHelper.zoomImage(bmp, width, height);
				bmp=CommonHelper.getAlphaBitmap(bmp, Color.RED);
				drawable =new BitmapDrawable(bmp);
				main_rb_owner.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
				flag_check=5;
				break;
		}
	};

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.main_rb_notice) {
			viewPager.setCurrentItem(0);
		}
		if (v.getId() == R.id.main_rb_property) {
			viewPager.setCurrentItem(1);
		}
		if (v.getId() == R.id.main_rb_live) {
			viewPager.setCurrentItem(2);
		}
		if (v.getId() == R.id.main_rb_community) {
			viewPager.setCurrentItem(3);
		}
		if (v.getId() == R.id.main_rb_owner) {
			viewPager.setCurrentItem(4);
		}
	}

	// 重写onKeyDown
	public  boolean  onKeyDown ( int  keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
				if((System.currentTimeMillis()-exitTime) > 2000){
					Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					finish();
					System.exit(0);
				}
				return true;
			}
		return  super .onKeyDown(keyCode, event);
	}
}
