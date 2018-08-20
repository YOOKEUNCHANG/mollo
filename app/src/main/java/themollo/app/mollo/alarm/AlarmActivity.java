package themollo.app.mollo.alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.SketchBook;

public class AlarmActivity extends AppUtilBasement implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {

    private AlarmManager alarmManager;
    private GregorianCalendar gregorianCalendar;
    //    private DatePicker datePicker;
//    private TimePicker timePicker;
    private NotificationManager notificationManager;

    @BindView(R.id.date_picker)
    DatePicker datePicker;
    @BindView(R.id.time_picker)
    TimePicker timePicker;
    @BindView(R.id.llBack)
    LinearLayout llBack;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        butterBind();
        setButtonListener();
        setUI();

    }

    public void setUI() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        gregorianCalendar = new GregorianCalendar();
        Log.i("HelloAlarmActivity", gregorianCalendar.getTime().toString());

        setContentView(R.layout.activity_alarm);

        datePicker = (DatePicker) findViewById(R.id.date_picker);
        datePicker.init(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH), this);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        timePicker.setCurrentHour(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(gregorianCalendar.get(Calendar.MINUTE));
        timePicker.setOnTimeChangedListener(this);

    }

    //알람의 설정
    private void setAlarm() {
        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), pendingIntent());
        Log.i("HelloAlarmActivity", gregorianCalendar.getTime().toString());
    }

    //알람의 해제
    private void resetAlarm() {
        alarmManager.cancel(pendingIntent());
    }

    //알람의 설정 시각에 발생하는 인텐트 작성
    private PendingIntent pendingIntent() {
        Intent i = new Intent(getApplicationContext(), SketchBook.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        return pi;
    }

    //일자 설정 클래스의 상태변화 리스너
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        gregorianCalendar.set(year, monthOfYear, dayOfMonth, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        Log.i("HelloAlarmActivity", gregorianCalendar.getTime().toString());
    }

    //시각 설정 클래스의 상태변화 리스너
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        gregorianCalendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), hourOfDay, minute);
        Log.i("HelloAlarmActivity", gregorianCalendar.getTime().toString());
    }

    @Override
    public void setButtonListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
