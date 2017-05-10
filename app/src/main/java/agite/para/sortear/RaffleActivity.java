package agite.para.sortear;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class RaffleActivity extends Activity implements ShakeDetector.ShakeListener {

    private RelativeLayout relativeLayoutBackground;
    private TextView textViewResult;

    private int maxValue;

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteio);

        maxValue = getIntent().getIntExtra(App.TAG_VALOR_LIMITE, 1);

        relativeLayoutBackground = (RelativeLayout) findViewById(R.id.relativeLayoutBackground);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        shakeDetector.setOnShakeListener(this);
    }

    @Override
    public void onShake(int countShakes) {
        relativeLayoutBackground.setBackgroundResource(R.color.background_activity_sorteio_in_progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(400);
        }

        textViewResult.setText(String.valueOf(new Random().nextInt(maxValue + 1)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }
}
