package idv.tfp10102.wayne.ch51_1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import idv.tfp10102.wayne.R;

public class Ch51_1_MyService extends Service {
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    MediaPlayer mediaPlayer;
    Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("aaa", "onCreate()");

        // 啟動不休眠鎖定
        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Ch51:Ch51_1_MyService");
        wakeLock.acquire(10*60*1000L /*10 minutes*/);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 啟動相關處理
        Log.d("aaa", "onStartCommand()");

        // 另起執行緒來播放
        thread = new MusicThread();
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 釋放資源
        Log.d("aaa", "onDestroy()");

        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();;
            mediaPlayer = null;
        }

        thread = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 另一種處理service的方式，這便沒用到，回傳null即可
        throw null;
    }

    private class MusicThread extends Thread {
        @Override
        public void run() {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(Ch51_1_MyService.this, R.raw.ff7);
                mediaPlayer.start();
            }
        }
    }
}