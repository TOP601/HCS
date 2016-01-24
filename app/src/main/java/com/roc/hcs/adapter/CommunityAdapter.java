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

public class CommunityAdapter extends BaseAdapter{

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    Community community = null;

    public CommunityAdapter(Context context, List<Map<String, Object>> data) {
        // TODO Auto-generated constructor stub
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /*
     * 组合组件，对应item中的控件
     */
    public final class Community {
        public ImageView iv_community;
        public TextView tv_community;
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
            community = new Community();
            // 获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.item_community, null);
            community.iv_community = (ImageView)convertView.findViewById(R.id.iv_community);
            community.tv_community = (TextView) convertView.findViewById(R.id.tv_community);
            convertView.setTag(community);
        } else {
            community = (Community) convertView.getTag();
        }
        // 绑定数据
        String type = (String) data.get(position).get("type");
        switch(type){
            case "1":
                community.iv_community.setImageResource(R.drawable.app_appcenter);
                break;
            case "2":
                community.iv_community.setImageResource(R.drawable.app_exchange);
                break;
            case "3":
                community.iv_community.setImageResource(R.drawable.community);
                break;
        }
//        community.iv_community.setColorFilter(Color.RED);
        community.tv_community.setText((String) data.get(position).get("community"));
        return convertView;
    }
}
