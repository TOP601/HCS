package com.roc.hcs.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.roc.hcs.R;
import com.roc.hcs.activity.DetailActivity;
import com.roc.hcs.adapter.NoticeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by s1112001 on 2015/12/2.
 */
public class ListViewFragment extends Fragment {
    private ListView listview;
    private static final String[][] msg_property = {{"1","关于开放二胎政策"},{"2","停电通知"},{"3","停水通知"}};
    private static final String[][] msg_gov = {{"1","防空警报"},{"4","雾霾警报"},{"3","维修水管"}};
    private String[][] msg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View notice_property = inflater.inflate(R.layout.fragment_listview, container, false);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("noticeType");
            if(mParam1=="property")
            {
                msg=msg_property;
            }
            else{
                msg=msg_gov;
            }
        }

        listview = (ListView) notice_property.findViewById(R.id.listview);
        List<Map<String, Object>> listMassage = getData();
        listview.setAdapter(new NoticeAdapter(getActivity(), listMassage));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
//                if (list.get(arg2).equals("LinearLayout")) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("title", "我的资料");
                intent.putExtra("url", "http://139.196.21.14:8081/Owner/Detail/1");
                intent.putExtra("back", "Notice");
                startActivity(intent);
//                }
            }

        });
        return notice_property;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < msg.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("type", msg[i][0]);
            map.put("notice", msg[i][1]);
            list.add(map);
        }
        return list;
    }
}