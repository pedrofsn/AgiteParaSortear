package agite.para.sortear.domain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by aluno on 27/05/17.
 */

public abstract class ActivityGeneric extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        afterOnCreate();
    }

    public abstract int getLayout();

    public void initView() {
    }

    public void afterOnCreate() {
    }

    public Snackbar getSnackBarComOk(String mensagem) {
        final Snackbar snackbar = getSnackBar(mensagem, Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(android.R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
        return snackbar;
    }

    public Snackbar getSnackBar(String mensagem, int duration) {
        return getSnackBar(findViewById(android.R.id.content), mensagem, duration);
    }

    public Snackbar getSnackBar(View view, String mensagem, int duration) {
        return Snackbar.make(view, mensagem, duration);
    }

}
