package agite.para.sortear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pedro.sousa on 14/10/2014.
 */
public class ActivityLimiteSorteio extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private TextView textViewValorLimite;
    private SeekBar seekBar;
    private Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_limite_sorteio);

        textViewValorLimite = (TextView) findViewById(R.id.textViewValorLimite);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        buttonOk = (Button) findViewById(R.id.buttonOk);

        seekBar.setOnSeekBarChangeListener(this);
        buttonOk.setOnClickListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        textViewValorLimite.setText(String.valueOf(progress));
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
            case R.id.buttonOk:
                int valorLimite = Integer.valueOf(textViewValorLimite.getText().toString());

                if (valorLimite <= 0) {
                    Toast.makeText(this, "Por favor, escolha um valor igual ou maior que 1.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, ActivitySorteio.class);
                    intent.putExtra("valorLimite", valorLimite);
                    startActivity(intent);
                }
                break;
        }
    }
}
