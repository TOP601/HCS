package com.roc.hcs.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roc.hcs.R;
import com.roc.hcs.xmpp.OwnerListActivity;

public class SetActivity extends HorizontalActivity implements View.OnClickListener {
private Button btn_logout;
    private ImageView iv_back;
    private TextView tv_title;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);

    iv_back = (ImageView)findViewById(R.id.iv_back);
    iv_back.setColorFilter(Color.RED);
    tv_title = (TextView)findViewById(R.id.tv_title);
    tv_title.setText("系统设置");
    iv_back.setOnClickListener(this);
    btn_logout = (Button)findViewById(R.id.btn_logout);
    btn_logout.setOnClickListener(this);

}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(SetActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_logout:
                Intent intent2 = new Intent(SetActivity.this, LoginActivity.class);
                //intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                MainActivity.instance.finish();
                finish();
                break;
            default:
                break;
        }
    }
}