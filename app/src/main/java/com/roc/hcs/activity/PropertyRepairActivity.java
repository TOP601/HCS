package com.roc.hcs.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.roc.hcs.R;

/**
 * Created by s1112001 on 2015/11/19.
 */
public class PropertyRepairActivity extends HorizontalActivity implements View.OnClickListener {
    private ImageView iv_back;
    private ImageButton imgBtn_repair_tel;
    private RadioButton repair_rb_public,repair_rb_personal;
    private TextView tv_title;
    private TextView tv_repair_price_list;
    private Spinner spn_repair_type;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_repair);

        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setColorFilter(Color.RED);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("报修");
        iv_back.setOnClickListener(this);

        repair_rb_public = (RadioButton)findViewById(R.id.repair_rb_public);
        repair_rb_personal = (RadioButton)findViewById(R.id.repair_rb_personal);
        repair_rb_public.setOnClickListener(this);
        repair_rb_personal.setOnClickListener(this);

        spn_repair_type = (Spinner) findViewById(R.id.spn_repair_type);
        //将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.publicRepairType, android.R.layout.simple_spinner_item);
        if(repair_rb_personal.isChecked())
        {
            adapter = ArrayAdapter.createFromResource(this, R.array.personalRepairType, android.R.layout.simple_spinner_item);
        }
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter2 添加到spinner中
        spn_repair_type.setAdapter(adapter);
        //spn_repair_type.setSelection(-1, true);//默认不选中

        imgBtn_repair_tel = (ImageButton)findViewById(R.id.imgBtn_repair_tel);
        imgBtn_repair_tel.setOnClickListener(this);
        tv_repair_price_list = (TextView)findViewById(R.id.tv_repair_price_list);
        tv_repair_price_list.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(PropertyRepairActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
				finish();
                break;
            case R.id.imgBtn_repair_tel:
                //用intent启动拨打电话
                String number = "02136768696";
                Intent intent3 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent3);
                break;
            case R.id.tv_repair_price_list:
                Intent intent2 = new Intent(PropertyRepairActivity.this,DetailActivity.class);
                intent2.putExtra("title", "个人维修价目表");
                intent2.putExtra("url", "http://139.196.21.14:8081/Property/RepairPriceList");
                intent2.putExtra("back", "PropertyRepair");
                startActivity(intent2);
                break;
            case R.id.repair_rb_public:
                repair_rb_public.setChecked(true);
                repair_rb_personal.setChecked(false);
                adapter = ArrayAdapter.createFromResource(this, R.array.publicRepairType, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_repair_type.setAdapter(adapter);
                break;
            case R.id.repair_rb_personal:
                repair_rb_public.setChecked(false);
                repair_rb_personal.setChecked(true);
                adapter = ArrayAdapter.createFromResource(this, R.array.personalRepairType, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_repair_type.setAdapter(adapter);
                break;
            default:
                break;
        }
    }
    // 重写onKeyDown
    public  boolean  onKeyDown ( int  keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent = new Intent(PropertyRepairActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("ID", "2");
            startActivity(intent);
            finish();
            return true;
        }
        return  super .onKeyDown(keyCode, event);
    }
}
