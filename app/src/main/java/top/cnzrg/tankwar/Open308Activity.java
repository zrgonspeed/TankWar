package top.cnzrg.tankwar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * FileName: Open308Activity
 * Author: ZRG
 * Date: 2020/11/2 18:15
 */
public class Open308Activity extends Activity {
    // 动态跟着类名改 TAG
    private static final String TAG = Open308Activity.class.getSimpleName();
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.open308);

        Log.e(TAG, "isTaskRoot() " + isTaskRoot());


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        init();
    }

    /**
     * 跳转到主页面，并且把当前页面关闭掉
     */
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解决点击启动页面，然后快速点返回键，界面又出现的bug
        mHandler.removeCallbacksAndMessages(null);
    }

    protected void init() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 两秒后执行到这里
                // 执行在主线程中,因为该Handler在主线程new
                startMainActivity();
                Log.i(TAG, "run:当前线程名称== " + Thread.currentThread().getName());
            }
        }, 4000);


    }

}
