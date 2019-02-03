package com.madeprojects.andjar.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDownload;
    public static final String ACTION_DOWNLOAD_STATUS = "download_status";
    public static final String VAR_TEST = "test variable";
    private String varTest;

    private BroadcastReceiver downloadReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Incoming Message");
        btnDownload = (Button)findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);

        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                varTest = intent.getStringExtra(VAR_TEST);
                Toast.makeText(context, varTest, Toast.LENGTH_SHORT).show();

            }
        };

        IntentFilter downloadIntentFilter = new IntentFilter(ACTION_DOWNLOAD_STATUS);
        registerReceiver(downloadReceiver, downloadIntentFilter);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_download){
            Intent downloadServiceIntent = new Intent(this, DownloadService.class);
            startService(downloadServiceIntent);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downloadReceiver != null){
            unregisterReceiver(downloadReceiver);
        }
    }
}
