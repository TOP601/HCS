package com.roc.hcs.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.roc.hcs.R;
/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class LiveGridAdapter extends BaseAdapter {
	private Context mContext;

//	public String[] img_text = { "二手", "快递", "家政", "外卖", "装修", "租房",
//			"干洗", "维修", "缴费" , "开锁" , "宠物" , "亲子" };


//	public int[] imgs = { R.drawable.app_transfer, R.drawable.app_fund,
//			R.drawable.app_phonecharge, R.drawable.app_creditcard,
//			R.drawable.app_movie, R.drawable.app_lottery,
//			R.drawable.app_facepay, R.drawable.app_close, R.drawable.app_plane
//	,R.drawable.app_aligame,R.drawable.app_citycard,R.drawable.app_appcenter};

	public String[] img_text;
	public int[] imgs;
	public LiveGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		img_text = mContext.getResources().getStringArray(R.array.live);
		TypedArray typedArray = mContext.getResources().obtainTypedArray(R.array.i_live);
		if( null != img_text ){
			int titleLength = img_text.length;
			imgs=new int[titleLength];
			for( int index = 0; index < titleLength; index++ ){
				imgs[index] = typedArray.getResourceId( index, index);
			}
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_live_item, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		iv.setBackgroundResource(imgs[position]);

		tv.setText(img_text[position]);
		return convertView;
	}

}
