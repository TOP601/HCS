package com.roc.hcs.adapter;

import java.util.List;
import java.util.Map;

import com.roc.hcs.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    Notice notice = null;

    public NoticeAdapter(Context context, List<Map<String, Object>> data) {
        // TODO Auto-generated constructor stub
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /*
     * 组合组件，对应item中的控件
     */
    public final class Notice {
        public ImageView iv_notice;
        public TextView tv_notice;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            notice = new Notice();
            // 获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.item_notice, null);
            notice.iv_notice = (ImageView)convertView.findViewById(R.id.iv_notice);
            notice.tv_notice = (TextView) convertView.findViewById(R.id.tv_notice);
            convertView.setTag(notice);
        } else {
            notice = (Notice) convertView.getTag();
        }
        // 绑定数据
        String type = (String) data.get(position).get("type");
        switch(type){
            case "1":
                notice.iv_notice.setImageResource(R.drawable.government);
                break;
            case "2":
                notice.iv_notice.setImageResource(R.drawable.bulb);
                break;
            case "3":
                notice.iv_notice.setImageResource(R.drawable.i_water);
                break;
            case "4":
                notice.iv_notice.setImageResource(R.drawable.app_appcenter);
                break;
        }
        notice.tv_notice.setText((String) data.get(position).get("notice"));
        return convertView;
    }
}

