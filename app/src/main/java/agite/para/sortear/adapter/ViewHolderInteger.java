package agite.para.sortear.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import agite.para.sortear.R;
import agite.para.sortear.utils.UtilsFormulario;


/**
 * Created by User on 18/07/2016.
 */
public class ViewHolderInteger extends RecyclerView.ViewHolder {

    private TextView textView;

    public ViewHolderInteger(View view) {
        super(view);
        textView = (TextView) view.findViewById(R.id.textView);
    }

    public void popular(Integer obj) {
        UtilsFormulario.setText(textView, obj);
    }
}