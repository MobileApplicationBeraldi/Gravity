package macc.example.com.gravity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm = null;
    private List<Sensor> deviceSensors = null;
    private TextView tv = null;
    private Sensor sensor = null;

    static float gx=0;
    static float gy=0;
    static float gz=0;

    static float a=0.15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float[] G = sensorEvent.values.clone();

        float x = G[0];
        float y = G[1];
        float z = G[2];

        //Apply Low Pass Filter
        gx = gx + a*(x-gx);
        gy = gy + a*(y-gy);
        gz = gz + a*(z-gz);
        String module = String.format( "%.4f", Math.sqrt(gx*gx+gy*gy+gz*gz));
        tv.setText(module);
        String compx = String.format( "%.4f", gx);
        String compy = String.format( "%.4f", gy);
        String compz = String.format( "%.4f", gz);
        String comp = "\nX: "+compx+"\nY: "+compy+"\nZ: "+compz;

        tv.setText(tv.getText()+comp);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
