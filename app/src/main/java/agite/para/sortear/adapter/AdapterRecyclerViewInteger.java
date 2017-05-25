package agite.para.sortear.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import agite.para.sortear.R;
import agite.para.sortear.domain.AdapterRecyclerViewCustom;

/**
 * Created by User on 18/07/2016.
 */
public class AdapterRecyclerViewInteger extends AdapterRecyclerViewCustom<ViewHolderInteger> {

    private List<Integer> lista = new ArrayList<>();

    public AdapterRecyclerViewInteger(List<Integer> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolderInteger onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_string, parent, false);
        ViewHolderInteger viewHolder = new ViewHolderInteger(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderInteger holder, final int position) {
        holder.popular(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public Integer getItem(int position) {
        if (position >= 0) {
            return lista.size() >= position ? lista.get(position) : null;
        }

        return null;
    }

}
