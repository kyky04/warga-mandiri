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
import co.id.wargamandiri.models.transaksi.konfirmasi.DataItemKonfirmasi;

public class KonfirmasiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public interface OnItemClick {
        void onItemClick(int pos);

        void onApprove(int pos);

        void onCancel(int pos);
    }

    public OnItemClick onItemClick;

    private List<DataItemKonfirmasi> items;
    private Context ctx;

    public KonfirmasiAdapter(Context context) {
        this.items = new ArrayList<>();
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.pembeli)
        TextView pembeli;
        @BindView(R.id.tanggal)
        TextView tanggal;
        @BindView(R.id.status)
        TextView status;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_konfirmasi, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final DataItemKonfirmasi order = items.get(position);
            view.title.setText(order.getOrder().getKode());
            view.pembeli.setText(order.getUser().getName());
            view.tanggal.setText(order.getCreatedAt());

            int status_order = order.getStatus();
            if (status_order == 0) {
                view.status.setText("Pending");
                view.status.setBackgroundColor(ctx.getResources().getColor(R.color.amber_800));
                view.more.setVisibility(View.VISIBLE);
            } else if (status_order == 1) {
                view.status.setText("Approve");
                view.status.setBackgroundColor(ctx.getResources().getColor(R.color.colorAccent));
                view.more.setVisibility(View.GONE);
            } else if (status_order == 2) {
                view.status.setText("Cancel");
                view.status.setBackgroundColor(ctx.getResources().getColor(R.color.red_500));
                view.more.setVisibility(View.GONE);
            }
            view.lytParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onMoreButtonClick(v, position);
                    onItemClick.onItemClick(position);
                }
            });


            view.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMoreButtonClick(v, position);
//                    onItemClick.onItemClick(position);
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
                    case R.id.menu_id_approve:
                        onItemClick.onApprove(pos);
                        break;
                    case R.id.menu_id_cance:
                        onItemClick.onCancel(pos);
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_more_konfirmasi);
        popupMenu.show();
    }

    public void add(DataItemKonfirmasi dataItem) {
        items.add(dataItem);
        notifyItemInserted(items.size() + 1);
    }

    public void addAll(List<DataItemKonfirmasi> items) {
        for (DataItemKonfirmasi rapatK3 : items) {
            add(rapatK3);
        }
    }

    public void remove(DataItemKonfirmasi item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void swap(List<DataItemKonfirmasi> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (items != null && items.size() > 0)
            items.clear();
        items.addAll(datas);
        notifyDataSetChanged();

    }

    public DataItemKonfirmasi getItem(int pos) {
        return items.get(pos);
    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}