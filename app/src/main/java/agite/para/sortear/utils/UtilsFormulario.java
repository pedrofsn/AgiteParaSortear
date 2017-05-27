package agite.para.sortear.utils;

import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by pedrofsn on 09/05/2017.
 */

public class UtilsFormulario {

    public static String getStringFromEditText(EditText editText) {
        return (!Utils.isNullOrEmpty(editText) && !Utils.isNullOrEmpty(editText.getEditableText()) && !Utils.isNullOrEmpty(editText.getEditableText().toString())) && !editText.getEditableText().toString().trim().isEmpty() ? editText.getEditableText().toString().trim() : Constantes.STRING_VAZIA;
    }

    public static void setText(TextView view, Object object) {
        if (!Utils.isNullOrEmpty(view)) {
            String resultado = Constantes.STRING_VAZIA;

            if (!Utils.isNullOrEmpty(object)) {
                if (object instanceof String) {
                    if (!Utils.isNullOrEmpty(object)) {
                        resultado = ((String) object).trim();
                    }

                } else if (object instanceof Character) {
                    if (!Utils.isNullOrEmpty(object)) {
                        resultado = object.toString();
                    }

                } else if (object instanceof Integer) {
                    if (!Utils.isNullOrEmpty(object)) {
                        resultado = String.valueOf(object);
                    }

                } else if (object instanceof Double) {
                    resultado = String.valueOf(object);
                }
            }

            if (Utils.isApiMin16()) {
                view.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
            }

            view.setText(resultado);
        }

    }
}
