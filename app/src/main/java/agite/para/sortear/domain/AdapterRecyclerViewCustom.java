package agite.para.sortear.domain;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by pedrofsn on 13/04/16.
 */
public class AdapterRecyclerViewCustom<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
