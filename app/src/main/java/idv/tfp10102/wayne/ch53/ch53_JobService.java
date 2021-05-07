package idv.tfp10102.wayne.ch53;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ch53_JobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {

        new MyThread().start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "跑完啦", Toast.LENGTH_SHORT).show();
        
        return false;
    }

    // 要另開執行緒來執行
    private static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Log.d("aaa", i + "");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e("aaa", e.toString());
                }
            }
        }
    }
}
