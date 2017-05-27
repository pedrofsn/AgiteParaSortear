package agite.para.sortear.domain;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by aluno on 27/05/17.
 */

public class ActivityGeneric extends AppCompatActivity {

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

    public Snackbar getSnackBarComAcao(String mensagem, String nomeBotao, View.OnClickListener listener) {
        final Snackbar snackbar = getSnackBar(mensagem, Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(android.R.string.ok), listener);
        snackbar.show();
        return snackbar;
    }

    public Snackbar getSnackBar(String mensagem) {
        return getSnackBar(findViewById(android.R.id.content), mensagem, Snackbar.LENGTH_SHORT);
    }

    public Snackbar getSnackBar(View view, String mensagem) {
        return Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT);
    }

    public void exibirMensagem(int mensagem) {
        exibirMensagem(getString(mensagem), Snackbar.LENGTH_SHORT);
    }

    public void exibirMensagem(String mensagem) {
        exibirMensagem(mensagem, Snackbar.LENGTH_SHORT);
    }

    public void exibirMensagem(String mensagem, int duration) {
        Snackbar.make(findViewById(android.R.id.content), mensagem, duration).show();
    }

    public Snackbar getSnackBar(String mensagem, int duration) {
        return getSnackBar(findViewById(android.R.id.content), mensagem, duration);
    }

    public Snackbar getSnackBar(View view, String mensagem, int duration) {
        return Snackbar.make(view, mensagem, duration);
    }

}
