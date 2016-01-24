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

public class OwnerAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    Owner owner = null;

    public OwnerAdapter(Context context, List<Map<String, Object>> data) {
        // TODO Auto-generated constructor stub
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /*
     * 组合组件，对应item中的控件
     */
    public final class Owner {
        public ImageView iv_owner;
        public TextView tv_owner;
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
            owner = new Owner();
            // 获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.item_owner, null);
            owner.iv_owner = (ImageView)convertView.findViewById(R.id.iv_owner);
            owner.tv_owner = (TextView) convertView.findViewById(R.id.tv_owner);
            convertView.setTag(owner);
        } else {
            owner = (Owner) convertView.getTag();
        }
        // 绑定数据
        String type = (String) data.get(position).get("type");
        switch(type){
            case "1":
                owner.iv_owner.setImageResource(R.drawable.app_essential);
                break;
            case "2":
                owner.iv_owner.setImageResource(R.drawable.app_close);
                break;
        }
        owner.tv_owner.setText((String) data.get(position).get("owner"));
        return convertView;
    }
}

