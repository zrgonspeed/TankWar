package top.cnzrg.tankwar.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.IBinder;

import top.cnzrg.tankwar.R;
import top.cnzrg.tankwar.data.GameData;

public class MusicService extends Service {

    // 声明MediaPlayer对象
    private MediaPlayer mediaPlayer;

    // action声明
    public static final String ACTION_MUSIC = "zrg308666";

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer == null) {
            // 根据音乐资源文件创建MediaPlayer对象 设置循环播放属性 开始播放
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.stop();

            //关键语句
            mediaPlayer.reset();

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }

}
