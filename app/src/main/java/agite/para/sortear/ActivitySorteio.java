package agite.para.sortear;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;


public class ActivitySorteio extends Activity implements IShakeListener {

    private TextView textViewResultado;
    private int valorLimite;

    private SensorManager mSensorManager;
    private Sensor acelerometro;
    private ShakeDetector shakeDetector;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sorteio);

        valorLimite = getIntent().getIntExtra("valorLimite", 1);
        textViewResultado = (TextView) findViewById(R.id.textViewResultado);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onShake(int countShakes) {
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(400);
            textViewResultado.setText(String.valueOf(new Random().nextInt(valorLimite + 1)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(shakeDetector, acelerometro, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }
}
