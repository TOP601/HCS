package com.roc.hcs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roc.hcs.R;
import com.roc.hcs.update.ParseXmlService;
import com.roc.hcs.update.UpdateManager;
import com.roc.hcs.utils.NetworkDetector;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class WelcomeActivity extends Activity {
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;
    int isUpdate = 0;
    private LinearLayout linearLayout1;
    private Button button;
    private ImageView iv;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        setContentView(R.layout.welcome);

        chkVersion();

        linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
        button = (Button)findViewById(R.id.online_error_btn_retry);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean networkState = NetworkDetector.detect(WelcomeActivity.this);
                if (networkState) {
                linearLayout1.setVisibility(View.GONE);
                chkVersion();
                }else{
                    isUpdate = 2;
                    handler.sendEmptyMessage(1);
                }
            }
        });
        iv = (ImageView)findViewById(R.id.wordpress_logo);
    }
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (isUpdate == 1) {
                        showNoticeDialog();
                    }
                    break;
                case 1:
                    if (isUpdate == 2) {
                        linearLayout1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "请检查网络连接！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private void chkVersion(){
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();

                boolean networkState = NetworkDetector.detect(WelcomeActivity.this);
                if (networkState) {
                    iv.setVisibility(View.VISIBLE);
                    int currentVersion = getCurrentVersion();
                    TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
                    versionNumber.setText("Version " + String.valueOf(currentVersion));

                    int serviceVersion = getServiceVersion();
                    if (serviceVersion > currentVersion) {
                        isUpdate = 1;
                        handler.sendEmptyMessage(0);
                    } else {
                        gotoMainOrLogin();
                    }
                } else {
                    isUpdate = 2;
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    private int getCurrentVersion() {
        int currentVersion = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            PackageManager pm = getPackageManager();
            currentVersion = pm.getPackageInfo("com.roc.hcs", 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersion;
    }

    private int getServiceVersion() {
        // 把version.xml放到网络上，然后获取文件信息
        //InputStream inStream = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
        // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
        ParseXmlService service = new ParseXmlService();

        try {
            String path = getResources().getString(R.string.url_version_xml);//"http://139.196.21.14:8081/update/version.xml";
            URL url = new URL(path);
            int port = url.getPort();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();
            mHashMap = service.parseXml(inStream);
            return Integer.valueOf(mHashMap.get("version"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 显示软件更新对话框
     */
    public void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);// 设置点击屏幕Dialog不消失
        builder.setTitle(R.string.soft_update_title);
        builder.setMessage(R.string.soft_update_info);
        // 更新
        builder.setPositiveButton(R.string.soft_update_updatebtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UpdateManager manager = new UpdateManager(WelcomeActivity.this);
                // 显示下载对话框
                manager.showDownloadDialog(mHashMap);
            }
        });
        // 稍后更新
        builder.setNegativeButton(R.string.soft_update_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                gotoMainOrLogin();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void gotoMainOrLogin() {
        boolean mFirst = isFirstEnter(WelcomeActivity.this,WelcomeActivity.this.getClass().getName());
        if(mFirst) {
            Intent mIntent = new Intent();
            mIntent = new Intent();
            mIntent.setClass(WelcomeActivity.this, GuideActivity.class);
            WelcomeActivity.this.startActivity(mIntent);
            WelcomeActivity.this.finish();
        }
        else{
            //取值
//RECORD 同上，不然取出来的值就不是存的值了
//0 表示默认值，如没有取到值就返回这个0
            SharedPreferences mPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            int index = mPreferences.getInt("userid", 0);
            Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            if (index != 0) {
                /* Create an Intent that will start the Main WordPress Activity. */
                mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            }
            WelcomeActivity.this.startActivity(mainIntent);
            WelcomeActivity.this.finish();
        }
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                //取值
////RECORD 同上，不然取出来的值就不是存的值了
////0 表示默认值，如没有取到值就返回这个0
//                SharedPreferences mPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//                int index = mPreferences.getInt("userid", 0);
//                Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                if (index != 0) {
//                /* Create an Intent that will start the Main WordPress Activity. */
//                    mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
//                }
//                WelcomeActivity.this.startActivity(mainIntent);
//                WelcomeActivity.this.finish();
//            }
//        }, 2000); //2900 for release
    }
    //****************************************************************
    // 判断应用是否初次加载，读取SharedPreferences中的guide_activity字段
    //****************************************************************
    private static final String SHAREDPREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private boolean isFirstEnter(Context context,String className){
        if(context==null || className==null||"".equalsIgnoreCase(className))return false;
        String mResultStr = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_WORLD_READABLE)
                .getString(KEY_GUIDE_ACTIVITY, "");//取得所有类名 如 com.my.MainActivity
        if(mResultStr.equalsIgnoreCase("false"))
            return false;
        else
            return true;
    }
}
