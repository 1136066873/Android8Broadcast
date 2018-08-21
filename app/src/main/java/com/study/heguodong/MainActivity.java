package com.study.heguodong;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_layout;
    private static Button btn_send_broadcast ;
    private static TextView tv_info ;
    private static ProgressBar pb_wait ;
    private static String WAIT_INFO = "Please wait 5 second.";
    private static String SUCCESS_INFO = "Receive other app broadcast.";
    private static String SUCCESS_END_INFO = "END";

    public static String ACTION_CLOSE_JNI="com.haier.uhomecontrol.hood.action.ACTION_CLOSE_JNI";
    public static String ACTION_OPEN_JNI="com.haier.uhomecontrol.hood.action.ACTION_OPEN_JNI";

    public static String ACTION_CLOSE_JNI_COMPLETE="com.haier.uhomecontrol.hood.action.ACTION_CLOSE_JNI_COMPLETE";
    public static String ACTION_OPEN_JNI_COMPLETE="com.haier.uhomecontrol.hood.action.ACTION_OPEN_JNI_COMPLETE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl_layout = findViewById(R.id.rl_layout);
        btn_send_broadcast = findViewById(R.id.btn_send_broadcast);
        btn_send_broadcast.setOnClickListener(this);
        tv_info = findViewById(R.id.tv_info);
        pb_wait = findViewById(R.id.pb_wait);
        pb_wait.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_broadcast:
                //Send broadcast to other app（in Android 8.0）
                Toast.makeText(this, WAIT_INFO, Toast.LENGTH_SHORT).show();
                tv_info.setText(WAIT_INFO);
                pb_wait.setVisibility(View.VISIBLE);
                btn_send_broadcast.setClickable(false);

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.study.heguodong.app2","com.study.heguodong.app2.MainActivity$MyBroadcastReceiver"));
                //intent.setPackage("com.study.heguodong.app2");//"com.android.systemui"
                intent.setAction(ACTION_OPEN_JNI);
                sendBroadcast(intent);

                break;

        }
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_OPEN_JNI_COMPLETE.contains(action)){
                //知道别人为我打开了串口
                Toast.makeText(context, SUCCESS_INFO, Toast.LENGTH_SHORT).show();
                tv_info.setText(SUCCESS_INFO);
                pb_wait.setVisibility(View.INVISIBLE);
                btn_send_broadcast.setClickable(true);

                Intent intent_success = new Intent();
                intent_success.setComponent(new ComponentName("com.study.heguodong.app2","com.study.heguodong.app2.MainActivity$MyBroadcastReceiver"));
                intent_success.setAction(ACTION_CLOSE_JNI);
                context.sendBroadcast(intent_success);
            }else if (ACTION_CLOSE_JNI_COMPLETE.contains(action)){
                //知道别人已经开始使用串口了
                Toast.makeText(context, SUCCESS_END_INFO, Toast.LENGTH_SHORT).show();
                tv_info.setText(SUCCESS_END_INFO);
            }


        }
    }
}
