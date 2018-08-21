package com.study.heguodong.app2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.heguodong.app2.R;

public class MainActivity extends AppCompatActivity {

    private static TextView tv_info;

    public static String ACTION_CLOSE_JNI="com.haier.uhomecontrol.hood.action.ACTION_CLOSE_JNI";
    public static String ACTION_OPEN_JNI="com.haier.uhomecontrol.hood.action.ACTION_OPEN_JNI";

    public static String ACTION_CLOSE_JNI_COMPLETE="com.haier.uhomecontrol.hood.action.ACTION_CLOSE_JNI_COMPLETE";
    public static String ACTION_OPEN_JNI_COMPLETE="com.haier.uhomecontrol.hood.action.ACTION_OPEN_JNI_COMPLETE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_info = findViewById(R.id.tv_info);
    }



    public static class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_OPEN_JNI.contains(action)){
                tv_info.setText("别人开始使用串口了，我要把串口给别人使用。");
                Log.e("heguodong2","别人开始使用串口了，我要把串口给别人使用。");

                //TODO:假装打开串口
                //

                //打开串口结束，给别人说已经给你打开串口了
                Intent intent_1 = new Intent();
                intent_1.setComponent(new ComponentName("com.study.heguodong","com.study.heguodong.MainActivity$MyBroadcastReceiver"));
                intent_1.setAction(ACTION_OPEN_JNI_COMPLETE);
                context.sendBroadcast(intent_1);

            }else if (ACTION_CLOSE_JNI.contains(action)){

                tv_info.setText("别人使用串口结束了，我继续使用串口。");
                Log.e("heguodong2","别人使用串口结束了，我继续使用串口。");

                //TODO:假装自己继续使用串口
                //

                //继续使用串口，给别人说我已经继续使用串口了
                Intent intent_2 = new Intent();
                intent_2.setComponent(new ComponentName("com.study.heguodong","com.study.heguodong.MainActivity$MyBroadcastReceiver"));
                intent_2.setAction(ACTION_CLOSE_JNI_COMPLETE);
                context.sendBroadcast(intent_2);

            }

        }

    }

}
