package themollo.app.mollo.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;

public class AlarmActivity extends AppUtilBasement {

    private static String TAG = "alarm";

    @BindView(R.id.wpHour)
    WheelPicker wpHour;
    @BindView(R.id.wpMinute)
    WheelPicker wpMinute;
    @BindView(R.id.btAlarm)
    Button btAlarm;

    private int hour = 0, minute = 0;

    private final int requestCode = 1000;
    private ArrayList<String> hours = new ArrayList<>();
    private ArrayList<String> minutes = new ArrayList<>();
    private Intent alarmIntent = new Intent("themollo.app.mollo.alarm.AlarmActivity");
    private Calendar calendar = Calendar.getInstance();
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        butterBind();

        setTimeData();
        setWheelPicker();
        setButtonListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toastText(getBaseContext(), hour + "시 " + minute +"분으로 알람이 설정되었습니다.");
        setCalendarTime();
        pendingIntent = PendingIntent.getBroadcast(this, requestCode,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void setAlarm(){
        toastText(getBaseContext(), hour + "시 " + minute +"분으로 알람이 설정되었습니다.");
        setCalendarTime();
        pendingIntent = PendingIntent.getBroadcast(this, requestCode,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void setButtonListener() {
        btAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setAlarm();
               logUtil(TAG, "hour : " + hour + " minute : " + minute);
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public class AlarmSoundService extends Service{

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Toast.makeText(this, "알람이 울리는 중", Toast.LENGTH_LONG).show();
            return START_NOT_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }

    public class AlarmReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent serviceIntent = new Intent(context, AlarmSoundService.class);
            context.startService(serviceIntent);
        }
    }

    public void setCalendarTime(){
        hour = Integer.parseInt(String.valueOf(wpHour.getData()
                .get(wpHour.getCurrentItemPosition())));
        minute = Integer.parseInt(String.valueOf(wpMinute.getData()
                .get(wpMinute.getCurrentItemPosition())));
        calendar.set(Calendar.HOUR_OF_DAY, hour+12);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
    }

    public void setWheelPicker(){
        wpHour.setData(hours);
        wpMinute.setData(minutes);

        wpHour.setCurved(true);
        wpHour.setAtmospheric(true);
        wpHour.setCyclic(true);
        wpHour.setItemTextSize(115);
        wpHour.setIndicator(true);
        wpHour.setIndicatorColor(R.color.appColor);
        wpHour.setVisibleItemCount(5);
//        wpHour.setSelectedItemPosition(0);
        wpHour.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                hour = Integer.parseInt(String.valueOf(picker.getData().get(picker.getCurrentItemPosition())));
            }
        });

        wpMinute.setCurved(true);
        wpMinute.setAtmospheric(true);
        wpMinute.setCyclic(true);
        wpMinute.setItemTextSize(115);
        wpMinute.setIndicator(true);
        wpMinute.setIndicatorColor(R.color.appColor);
        wpMinute.setVisibleItemCount(5);
        wpMinute.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                minute = Integer.parseInt(String.valueOf(picker.getData().get(picker.getCurrentItemPosition())));
            }
        });
    }

    public void setTimeData(){
        for(int i=0;i<60;i++){
            if(i<10) minutes.add("0"+i);
            else minutes.add(""+i);
        }

        for(int i=0;i<=23;i++){
            if(i<10) hours.add("0"+i);
            else hours.add(""+i);
        }
    }
}
