package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String mark = "";
    public int getIpAddress() {


        ArrayList<String> listStringIp = new ArrayList<String>();

        int result = 0;


        try {

            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
//                        return inetAddress.getHostAddress().toString();
//                        listStringIp.add(inetAddress.getHostAddress().toString());
                        result++;
                        if (result > 1){
                            mark += (inetAddress.getHostAddress().toString() + "  ");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            return e.toString();
        }
//        return null;
        return result;
    }

    private class RecordEntity{
        String name;
        String number;
        int type;
        long lDate;
        long duration;
        int _new;
        @Override
        public String toString() {
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(lDate);
            String time = sfd.format(date);
            return "RecordEntity [toString()=" + name+"," + number+"," + type+"," +time+"," + duration+"," + _new+"," + "]";
        }
    }


    String func(){

        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = null;
        String ret = "";
        try {
            cursor = contentResolver.query(
                    CallLog.Calls.CONTENT_URI, null, null, null,
                    CallLog.Calls.DATE + " desc");
            if (cursor == null)
                return null;
            List<RecordEntity> mRecordList = new ArrayList<RecordEntity>();
            while (cursor.moveToNext()) {
                RecordEntity record = new RecordEntity();
                record.name = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));
                record.number = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                record.type = cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.TYPE));
                record.lDate = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DATE));
                record.duration = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DURATION));
                record._new = cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.NEW));
                Log.e("jjj", record.toString());
//						int photoIdIndex = cursor.getColumnIndex(CACHED_PHOTO_ID);
//						if (photoIdIndex >= 0) {
//							record.cachePhotoId = cursor.getLong(photoIdIndex);
//						}

                mRecordList.add(record);
            }
            if(mRecordList.size()>0) {
                ret = mRecordList.get(0).toString();

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return  ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(getApplicationContext(), func(), Toast.LENGTH_SHORT).show();
            }
        });


//       final android.widget.ScrollView scrollView = new android.widget.ScrollView(this);
//       setContentView(scrollView);
//       final android.widget.TextView text = new android.widget.TextView(this);
//       scrollView.addView(text);
//       text.setBackgroundColor(android.graphics.Color.argb(255, 0, 0, 0));
//       text.setTextColor(android.graphics.Color.argb(255, 255, 255, 255));


//       final Activity activityThis = this;
//       final java.util.Timer timer = new java.util.Timer();
//       timer.schedule(new java.util.TimerTask() {
//           private android.os.Handler handler = new android.os.Handler() {
//               public void handleMessage(android.os.Message msg) {
//                   text.setText("");
//                   text.setText(mark + "  mark\n");

//                   android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager)
//                           activityThis.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);


//                   if(getIpAddress() > 1){
//                       mark += " yes";
//                   }

//               }
//           };

//           public void run() {
//               handler.sendEmptyMessage(0);
//           }
//       }, 1000, 10);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
