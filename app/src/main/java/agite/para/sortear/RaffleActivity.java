package agite.para.sortear;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RaffleActivity extends Activity implements ShakeDetector.ShakeListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private RelativeLayout relativeLayoutBackground;
    private LinearLayout linearLayoutLimites;
    private TextView textViewResult;
    private TextView textViewLimites;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private RadioButton radioButtonIncluso;
    private RadioButton radioButtonNaoIncluso;
    private CheckBox checkBox;

    private Sorteio sorteio;

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    private Vibrator vibrator;
    private boolean permitirNumerosRepetidos = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteio);

        sorteio = (Sorteio) getIntent().getSerializableExtra(Constantes.TAG_SORTEIO);

        if (Utils.isNullOrEmpty(sorteio)) {
            finish();
        }

        relativeLayoutBackground = (RelativeLayout) findViewById(R.id.relativeLayoutBackground);
        linearLayoutLimites = (LinearLayout) findViewById(R.id.linearLayoutLimites);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewLimites = (TextView) findViewById(R.id.textViewLimites);
        imageView = (ImageView) findViewById(R.id.imageView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButtonIncluso = (RadioButton) findViewById(R.id.radioButtonIncluso);
        radioButtonNaoIncluso = (RadioButton) findViewById(R.id.radioButtonNaoIncluso);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        imageView.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        checkBox.setOnCheckedChangeListener(this);

        if (sorteio.hasLimiteMinimo()) {
            UtilsFormulario.setText(textViewLimites, String.format(getString(R.string.sortear_entre_x_e_y), sorteio.getMin(), sorteio.getMax()));
            linearLayoutLimites.setVisibility(View.VISIBLE);
        } else {
            linearLayoutLimites.setVisibility(View.GONE);
        }
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
        sortear();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                abrirMenuLateral();
                break;
        }
    }

    private void abrirMenuLateral() {
        sortear();
    }

    private void sortear() {
        relativeLayoutBackground.setBackgroundResource(R.color.background_activity_sorteio_in_progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(400);
        }

        try {
            int sorteado = sorteio.getSorteado(permitirNumerosRepetidos);
            UtilsFormulario.setText(textViewResult, sorteado);

        } catch (QuantidadeMaximaException quantidadeMaximaException) {
            Toast.makeText(this, quantidadeMaximaException.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        sorteio.setLimitesExclusivos(checkedId == radioButtonNaoIncluso.getId());
        listaSorteados.clear();
        sortear();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        permitirNumerosRepetidos = isChecked;
        listaSorteados.clear();
        sortear();
    }
}
