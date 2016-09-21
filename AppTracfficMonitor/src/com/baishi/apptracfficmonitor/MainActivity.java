package com.baishi.apptracfficmonitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final String mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
            + "%s.txt";
    private String mPackageName = "";
    private ApplicationInfo mApplicationInfo;
    private float mUidReckbps = 0;
    private float mUidSndkbps = 0;
    private long mPreviousTime = 0;
    private long mPreviousRec = 0;
    private long mPreviousSnd = 0;
    private boolean mLoop = false;
    private Button mButton;
    private Spinner mSpinner;
    private TextView mRecView;
    private TextView mSndView;
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            while (mLoop) {
                try {
                    getTracffic();
                } catch (SecurityException e1) {
                    e1.printStackTrace();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                runOnUiThread(display);
                String out = "%s,%s,%6.2fkbps,%6.2fkbps\r\n";
                writeText(
                        String.format(out, dateFormat.format(date), timeFormat.format(date), mUidReckbps, mUidSndkbps),
                        String.format(mFilePath, mPackageName), true);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    };
    private Thread mThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mRecView = (TextView) findViewById(R.id.rec);
        mSndView = (TextView) findViewById(R.id.snd);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mThread != null && mThread.isAlive()) {
            mButton.setText("停止");
            mSpinner.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread();
    }

    private void getTracffic() throws SecurityException, RemoteException {
        long currentTime = System.currentTimeMillis();
        if (mPreviousTime != 0) {
            float interval = (currentTime - mPreviousTime) / 1000f;
            long uidRec = TrafficStats.getUidRxBytes(mApplicationInfo.uid) * 8;
            long uidSnd = TrafficStats.getUidTxBytes(mApplicationInfo.uid) * 8;
            mUidReckbps = (uidRec - mPreviousRec) / interval / 1024;
            mUidSndkbps = (uidSnd - mPreviousSnd) / interval / 1024;
            mPreviousRec = uidRec;
            mPreviousSnd = uidSnd;
        }
        mPreviousTime = currentTime;
    }

    private Runnable display = new Runnable() {

        @Override
        public void run() {
            mRecView.setText(String.format("%6.2fkbps", mUidReckbps));
            mSndView.setText(String.format("%6.2fkbps", mUidSndkbps));
        }
    };

    public void onStartButton(View view) throws NameNotFoundException {
        if (mThread == null || !mThread.isAlive()) {
            mPackageName = mSpinner.getSelectedItem().toString();
            mApplicationInfo = getPackageManager().getApplicationInfo(mPackageName, PackageManager.GET_ACTIVITIES);
            startThread();
        } else {
            stopThread();
        }
    }

    private void startThread() {
        mButton.setText("停止");
        mSpinner.setEnabled(false);
        mPreviousTime = 0;
        mLoop = true;
        mThread = new Thread(mRunnable);
        mThread.start();
    }

    private void stopThread() {
        mButton.setText("开始");
        mSpinner.setEnabled(true);
        mLoop = false;
    }

    private void writeText(String text, String filePath, boolean append) {
        byte[] buffer = null;
        try {
            buffer = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writeFile(buffer, filePath, 1, append);
    }

    private void writeFile(byte[] buffer, String filePath, long num, boolean append) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, append);
            while (num-- > 0) {
                fos.write(buffer);
            }
            fos.close();
        } catch (IOException e) {
        }
    }
}
