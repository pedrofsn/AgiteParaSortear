package agite.para.sortear.utils;

import agite.para.sortear.App;
import agite.para.sortear.R;

/**
 * Created by pedrofsn on 09/05/2017.
 */

public class Constantes {

    public static final String APP_NAME = App.getContext().getString(R.string.app_name).toLowerCase().replaceAll(" ", "_");

    public static final String STRING_VAZIA = "";
    public static final String TAG_SORTEIO = "sorteio";
    public static final int VALOR_INVALIDO = -1;
}
