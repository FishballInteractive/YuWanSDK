package com.rg.sdkdemo;

import java.util.UUID;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ireadygo.sdk.RgAppInfo;
import com.ireadygo.sdk.RgBuyInfo;
import com.ireadygo.sdk.RgCommplatform;
import com.ireadygo.sdk.RgCommplatform.OnInitCompleteListener;
import com.ireadygo.sdk.RgCommplatform.OnLoginProcessListener;
import com.ireadygo.sdk.RgCommplatform.OnLogoutListener;
import com.ireadygo.sdk.RgCommplatform.OnPayProcessListener;
import com.ireadygo.sdk.RgConstant;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//参数分别为AppId  AppKey  论坛ID，由鱼丸互动分配
        RgAppInfo appInfo = new RgAppInfo("10000002",
                "aeb92e964c1cf89a9ddc717b9a2b9fcc", "116");
        RgCommplatform.rgInit(this, appInfo, new OnInitCompleteListener() {

            @Override
            public void onInitComplete(int initCode) {
                if (initCode == RgConstant.INIT_SUCCESS) {
                    RgCommplatform.addOnLogoutListener(new OnLogoutListener() {

                        @Override
                        public void onLogout() {
                            // 注销帐号处理逻辑，以下代码仅为示例代码，cp可根据自身需要进行操作，如重新弹出登录界面等
                            Intent i = getPackageManager()
                                    .getLaunchIntentForPackage(getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                    });
                    RgCommplatform.rgLogin(mLoginCallback);

                    // TODO your code
                    initView();
                }
            }
        });

    }

    private void initView() {
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RgCommplatform.rgLogin(mLoginCallback);
            }
        });

        Button btn_pay100 = (Button) findViewById(R.id.btn_pay100);
        btn_pay100.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!RgCommplatform.isLogined(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "??????",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String uuid = UUID.randomUUID().toString(); // 订单id，必须唯一，必须设置
                String itemName = "昆仑天晶";// 道具名称 必须设置
                int gredit = 100;// 支付金额 必须设置，单位：元
                String note = "原样返回给游戏服务器";// 直接回传给游戏服务器的字符串，可不设置
                RgBuyInfo buyInfo = new RgBuyInfo(uuid, itemName, gredit);
                buyInfo.setNote(note);

                RgCommplatform.rgPayAsyn(buyInfo, new OnPayProcessListener() {

                    @Override
                    public void finishPayProcess(int payCode) {
                        Log.d("liu.js", "返回支付结果--payCode=" + payCode);
                        switch (payCode) {
                            case RgConstant.PAY_SUCCESS:// 支付成功
                                Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                                break;
                            case RgConstant.PAY_FAIL:// 支付失败
                                Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                                break;
                            case RgConstant.PAY_CANCEL:// 支付取消
                                Toast.makeText(MainActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        RgCommplatform.rgDestory();
        super.onDestroy();
    }

    private OnLoginProcessListener mLoginCallback = new OnLoginProcessListener() {

        @Override
        public void finishLoginProcess(int loginStatus) {
            if (RgConstant.LOGIN_SUCCESS == loginStatus) {// 登陆成功
                RgCommplatform.showFloatWindow(MainActivity.this);
            }
        }
    };

}
