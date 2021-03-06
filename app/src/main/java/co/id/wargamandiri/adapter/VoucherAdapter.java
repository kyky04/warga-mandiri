package co.id.wargamandiri.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import co.id.wargamandiri.models.DataItemProduk;
import co.id.wargamandiri.models.DataItemVoucher;
import co.id.wargamandiri.utils.Tools;

public class VoucherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    public interface OnItemClick {
        void onItemEditClick(int pos, DataItemVoucher dataItemBanner);

        void onItemDeleteClick(int pos, DataItemVoucher dataItemBanner);
    }

    public OnItemClick onItemClick;

    private List<DataItemVoucher> items;
    private Context ctx;

    public VoucherAdapter(Context context) {
        this.items = new ArrayList<>();
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_tanggal)
        TextView tvTanggal;
        @BindView(R.id.tv_jumlah_potongan)
        TextView tvJumlahPotongan;
        @BindView(R.id.tv_minimal_belanja)
        TextView tvMinimalBelanja;
        @BindView(R.id.lyt_parent)
        LinearLayout lytParent;
        @BindView(R.id.btn_edit)
        ImageButton btnEdit;
        @BindView(R.id.btn_delete)
        ImageButton btnDelete;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final DataItemVoucher o = items.get(position);
            view.title.setText(o.getKodeVoucher());
            view.tvTanggal.setText(o.getTanggal_awal()+" sampai "+ o.getTanggal_akhir());
            view.tvJumlahPotongan.setText("potongan :"+Tools.getFormattedPriceIndonesia((double) o.getJumlahPotongan()));
            view.tvMinimalBelanja.setText("minimal : "+Tools.getFormattedPriceIndonesia((double) o.getMinimalBelanja()));
            view.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemEditClick(position, o);
                }
            });
            view.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemDeleteClick(position, o);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(DataItemVoucher dataItem) {
        items.add(dataItem);
        notifyItemInserted(items.size() + 1);
    }

    public void addAll(List<DataItemVoucher> items) {
        for (DataItemVoucher rapatK3 : items) {
            add(rapatK3);
        }
    }

    public void remove(DataItemVoucher item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void swap(List<DataItemVoucher> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (items != null && items.size() > 0)
            items.clear();
        items.addAll(datas);
        notifyDataSetChanged();

    }

    public DataItemVoucher getItem(int pos) {
        return items.get(pos);
    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}