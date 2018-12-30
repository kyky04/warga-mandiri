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
import co.id.wargamandiri.models.DataItemBank;

public class BankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    public interface OnItemClick {
        void onItemClick(int pos);

        void onEditClick(int pos);

        void onDeleteClick(int pos);
    }

    public OnItemClick onItemClick;

    private List<DataItemBank> items;
    private Context ctx;

    public BankAdapter(Context context) {
        this.items = new ArrayList<>();
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.no_rek)
        TextView noRek;
        @BindView(R.id.atas_nama)
        TextView atasNama;
        @BindView(R.id.nama_bank)
        TextView namaBank;
        @BindView(R.id.lyt_parent)
        LinearLayout lytParent;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final DataItemBank o = items.get(position);
            view.noRek.setText(o.getNoRekening().replace("", " ").trim());
            view.atasNama.setText(o.getAtasNama());
            view.namaBank.setText(o.getNamaBank());

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
                        onItemClick.onEditClick(pos);
                        break;
                    case R.id.menu_id_delete:
                        onItemClick.onDeleteClick(pos);
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_more);
        popupMenu.show();
    }

    public void add(DataItemBank dataItem) {
        items.add(dataItem);
        notifyItemInserted(items.size() + 1);
    }

    public void addAll(List<DataItemBank> items) {
        for (DataItemBank rapatK3 : items) {
            add(rapatK3);
        }
    }

    public void remove(DataItemBank item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void swap(List<DataItemBank> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (items != null && items.size() > 0)
            items.clear();
        items.addAll(datas);
        notifyDataSetChanged();

    }

    public DataItemBank getItem(int pos) {
        return items.get(pos);
    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}