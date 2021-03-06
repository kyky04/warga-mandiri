package co.id.wargamandiri.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DataItemPengumuman;
import co.id.wargamandiri.models.DataItemProduk;

public class PengumumanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    public interface OnItemClick {
        void onItemClick(int position);

        void onItemEdit(int position);

        void onItemDelete(int position);
    }

    public OnItemClick onItemClick;

    private List<DataItemPengumuman> items;
    private Context ctx;

    public PengumumanAdapter(Context context) {
        this.items = new ArrayList<>();
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.lyt_parent)
        LinearLayout lytParent;
        @BindView(R.id.isi)
        TextView isi;
        @BindView(R.id.more)
        ImageButton more;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengumuman, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final DataItemPengumuman o = items.get(position);
            view.title.setText(o.getJudul());
            view.isi.setText(o.getIsi());

            view.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMoreButtonClick(v, position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void onMoreButtonClick(final View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(ctx, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_id_edit:
                        onItemClick.onItemEdit(pos);
                        break;
                    case R.id.menu_id_delete:
                        onItemClick.onItemDelete(pos);
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_more);
        popupMenu.show();
    }

    public void add(DataItemPengumuman dataItem) {
        items.add(dataItem);
        notifyItemInserted(items.size() + 1);
    }

    public void addAll(List<DataItemPengumuman> items) {
        for (DataItemPengumuman rapatK3 : items) {
            add(rapatK3);
        }
    }

    public void remove(DataItemPengumuman item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void swap(List<DataItemPengumuman> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (items != null && items.size() > 0)
            items.clear();
        items.addAll(datas);
        notifyDataSetChanged();

    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public DataItemPengumuman getItem(int pos) {
        return items.get(pos);
    }
}