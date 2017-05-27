package agite.para.sortear.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import agite.para.sortear.R;
import agite.para.sortear.domain.ActivityGeneric;
import agite.para.sortear.model.Sorteio;
import agite.para.sortear.utils.Constantes;
import agite.para.sortear.utils.Utils;
import agite.para.sortear.utils.UtilsFormulario;

public class ActivityLimiteSorteio extends ActivityGeneric implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private TextView textViewMessage;
    private SeekBar seekBar;
    private TextView textViewOk;
    private EditText editTextMin;
    private EditText editTextMax;

    private int maxValue;

    @Override
    public int getLayout() {
        return R.layout.activity_limite_sorteio;
    }

    @Override
    public void initView() {
        super.initView();
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        textViewOk = (TextView) findViewById(R.id.textViewOk);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        editTextMin = (EditText) findViewById(R.id.editTextMin);
        editTextMax = (EditText) findViewById(R.id.editTextMax);
    }

    @Override
    public void afterOnCreate() {
        super.afterOnCreate();
        getWindow().getAttributes().windowAnimations = R.style.AnimationFade;
        UtilsFormulario.setText(textViewOk, getString(android.R.string.ok));
    }

    @Override
    protected void onStart() {
        super.onStart();
        seekBar.setOnSeekBarChangeListener(this);
        textViewOk.setOnClickListener(this);
        firstCase();
    }

    private void firstCase() {
        textViewMessage.setText(String.format(getString(R.string.sortear_um_numero_entre), 50));
        seekBar.setProgress(50);
    }

    @Override
    protected void onStop() {
        super.onStop();
        seekBar.setOnSeekBarChangeListener(null);
        textViewOk.setOnClickListener(null);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        maxValue = progress;
        textViewMessage.setText(String.format(getString(R.string.sortear_um_numero_entre), maxValue));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewOk:
                setupRaffle();
                break;
        }
    }

    private void setupRaffle() {
        String minString = UtilsFormulario.getStringFromEditText(editTextMin);
        String maxString = UtilsFormulario.getStringFromEditText(editTextMax);

        Sorteio sorteio = new Sorteio(maxValue);

        if (isCaseLimits(minString, maxString)) {
            sorteio = new Sorteio(minString, maxString);
        }

        if (sorteio.isValido()) {
            Intent intent = new Intent(this, ActivitySorteio.class);
            intent.putExtra(Constantes.TAG_SORTEIO, sorteio);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        } else {
            Toast.makeText(this, getString(R.string.alerta), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCaseLimits(String minString, String maxString) {
        return !Utils.isNullOrEmpty(minString) && !Utils.isNullOrEmpty(maxString);
    }
}
