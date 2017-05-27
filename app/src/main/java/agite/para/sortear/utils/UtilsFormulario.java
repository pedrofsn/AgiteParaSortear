package agite.para.sortear.utils;

import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by pedrofsn on 09/05/2017.
 */

public class UtilsFormulario {

    public static String getStringFromEditText(EditText editText) {
        return (!Utils.isNullOrEmpty(editText) && !Utils.isNullOrEmpty(editText.getEditableText()) && !Utils.isNullOrEmpty(editText.getEditableText().toString())) && !editText.getEditableText().toString().trim().isEmpty() ? editText.getEditableText().toString().trim() : Constantes.STRING_VAZIA;
    }

    public static void setTextOrHide(TextView textView, Object object) {
        if (Utils.isNullOrEmpty(object) && !Utils.isNullOrEmpty(textView)) {
            textView.setVisibility(View.GONE);
        } else {
            setText(textView, object);
        }
    }

    public static void setTextOrHide(TextView textView, Object string, View view) {
        if (Utils.isNullOrEmpty(string) && !Utils.isNullOrEmpty(view)) {
            view.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        } else {
            setText(textView, string);
        }
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
