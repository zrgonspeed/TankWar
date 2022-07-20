package top.cnzrg.tankwar.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import top.cnzrg.tankwar.MainActivity;
import top.cnzrg.tankwar.R;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String ACTION_MUSIC = "zrg308666";
    private static Intent intent;

    public static void startPlayMusic(MainActivity activity) {
        if (intent == null) {
            intent = new Intent(activity, MusicService.class);
            intent.setAction(MusicService.ACTION_MUSIC);
            activity.startService(intent);
        }
    }

    public static void stopService(MainActivity activity) {
        if (intent != null) {
            // 对于intentService，这一步可能是多余的
            activity.stopService(intent);
        }

    }

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
