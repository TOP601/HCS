package com.roc.hcs.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roc.hcs.R;

/**
 * Created by Nacy on 2015/12/4.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button btn_city;
    private TextView tv_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btn_city = (Button)findViewById(R.id.btn_city);
        tv_city = (TextView)findViewById(R.id.tv_city);
        btn_city.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_city) {
            Intent intent = new Intent(this,CitiesActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //取出字符串
        Bundle bundle = data.getExtras();
        String str = bundle.getString("str");
        tv_city.setText(str);
    }
}