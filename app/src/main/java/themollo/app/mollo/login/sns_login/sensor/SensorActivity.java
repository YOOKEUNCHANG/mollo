package themollo.app.mollo.login.sns_login.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;

/**
 * Sensor Manager 사용 방법
 * 1. 센서 매니저 얻기
 * 2. 센서 선언
 * 3. 리스너 구성하기
 * 4. 리스너 등록하기
 * 5. 리스너 해제하기
 */

public class SensorActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    @BindView(R.id.tvAccel)
    TextView tvAccel;
    @BindView(R.id.tvGravity)
    TextView tvGravity;
    @BindView(R.id.tvGyro)
    TextView tvGyro;
    @BindView(R.id.tvLight)
    TextView tvLight;
    @BindView(R.id.tvPressure)
    TextView tvPressure;
    @BindView(R.id.tvProxy)
    TextView tvProxy;

    private Sensor lightSensor, accelSensor,
            gravitySensor, gyroscopeSensor,
            pressureSensor, proximitySensor;

    private float[] lightArr, accelArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        ButterKnife.bind(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    public void registerSensors() {
        sensorManager.registerListener( this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void unregisterSensors() {
        sensorManager.unregisterListener((SensorEventListener) this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensors();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensors();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();

        if (sensorType == Sensor.TYPE_LIGHT) {
            lightArr = sensorEvent.values;
            float lux = (float) (Math.round(lightArr[0] * 100) / 100);
            tvLight.setText(lux + " lux");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
