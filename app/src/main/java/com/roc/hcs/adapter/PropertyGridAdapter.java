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
public class PropertyGridAdapter extends BaseAdapter {
    private Context mContext;

//    public String[] img_text = { "报修", "投诉", "停车", "物业费"};
//    public int[] imgs = { R.drawable.app_transfer, R.drawable.app_fund,
//            R.drawable.app_phonecharge, R.drawable.app_creditcard,
//            R.drawable.app_movie, R.drawable.app_lottery,
//            R.drawable.app_facepay, R.drawable.app_close, R.drawable.app_plane
//            ,R.drawable.app_aligame,R.drawable.app_citycard,R.drawable.app_appcenter};
//
//    public PropertyGridAdapter(Context mContext) {
//        super();
//        this.mContext = mContext;
//    }
public String[] img_text;
    public int[] imgs;
    public PropertyGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        img_text = mContext.getResources().getStringArray(R.array.property);
        TypedArray typedArray = mContext.getResources().obtainTypedArray(R.array.i_property);
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
                    R.layout.grid_property_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }

}
