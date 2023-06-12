package com.vaibhavmojidra.androidjavademo2workmanagerperiodicworkrequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import android.os.Bundle;
import android.widget.Toast;

import com.vaibhavmojidra.androidjavademo2workmanagerperiodicworkrequest.databinding.ActivityMainBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.Builder(MyPeriodicWorker.class,16, TimeUnit.MINUTES).build();

        WorkManager workManager=WorkManager.getInstance(this);

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.getId()).observe(this, workInfo -> {
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedTime = timeFormat.format(currentTime);
            binding.periodicRequestLogsTextView.append("Periodic Work Request: State: "+workInfo.getState().name()+" Time: "+formattedTime+"\n\n");
        });

        binding.startPeriodicButton.setOnClickListener(view -> {
            workManager.enqueue(periodicWorkRequest);
        });
    }
}