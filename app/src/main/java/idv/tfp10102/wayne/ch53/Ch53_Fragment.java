package idv.tfp10102.wayne.ch53;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;
public class Ch53_Fragment extends Fragment {
    private static final int ID_MY_JOB = 1;

    MainActivity activity;

    JobScheduler jobScheduler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_ch53, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // JobService可能被呼叫多次，再包一層可避免重複(ID之類的)
            ComponentName componentName = new ComponentName(activity, ch53_JobService.class);

            JobInfo jobInfo = new JobInfo.Builder(ID_MY_JOB, componentName)
                    // 所有條件都要達成才會觸發
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .build();

            jobScheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            int resultCode = jobScheduler.schedule(jobInfo);

            if (resultCode == JobScheduler.RESULT_FAILURE) {
                Toast.makeText(activity, "排程失敗", Toast.LENGTH_SHORT).show();
            }
        }
    }
}