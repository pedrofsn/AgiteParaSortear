package agite.para.sortear.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import agite.para.sortear.R;
import agite.para.sortear.adapter.AdapterRecyclerViewInteger;
import agite.para.sortear.domain.ActivityGeneric;
import agite.para.sortear.domain.OnSorteiroRealizado;
import agite.para.sortear.domain.ShakeDetector;
import agite.para.sortear.model.Sorteio;
import agite.para.sortear.utils.Constantes;
import agite.para.sortear.utils.Utils;
import agite.para.sortear.utils.UtilsFormulario;

public class ActivitySorteio extends ActivityGeneric implements ShakeDetector.ShakeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, OnSorteiroRealizado {

    private RelativeLayout relativeLayoutBackground;
    private TextView textViewResult;
    private TextView textViewLimites;
    private ImageView imageView;
    private CheckBox checkBox;
    private LinearLayout linearLayoutLimites;
    private RecyclerView recyclerView;

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
        } else {
            sorteio.setCallback(this);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        relativeLayoutBackground = (RelativeLayout) findViewById(R.id.relativeLayoutBackground);
        linearLayoutLimites = (LinearLayout) findViewById(R.id.linearLayoutLimites);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewLimites = (TextView) findViewById(R.id.textViewLimites);
        imageView = (ImageView) findViewById(R.id.imageView);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        imageView.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);

        if (sorteio.hasLimitesMinMax()) {
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

        Integer sorteado = sorteio.getSorteado(permitirNumerosRepetidos);
        if (!Utils.isNullOrEmpty(sorteado)) {
            UtilsFormulario.setText(textViewResult, sorteado);
        } else {
            if (sorteio.isTodasPossibilidadesAtingidas()) {
                getSnackBarComOk(getString(R.string.todos_os_numeros_possiveis_foram_sorteados));
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        permitirNumerosRepetidos = isChecked;
        sorteio.getListaSorteados().clear();
        sortear();
    }

    @Override
    public void onNumeroSorteado() {
        AdapterRecyclerViewInteger adapter = new AdapterRecyclerViewInteger(sorteio.getListaSorteados());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }
}
