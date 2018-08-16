package co.id.wargamandiri.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DataItemProduk;
import co.id.wargamandiri.models.DataItemProduk;
import co.id.wargamandiri.services.OauthConstans;
import co.id.wargamandiri.utils.Tools;

public class AdapterProduk extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClick {
        void onItemEditClick(int pos, DataItemProduk dataItemBanner);

        void onItemDeleteClick(int pos, DataItemProduk dataItemBanner);
    }

    public OnItemClick onItemClick;

    private List<DataItemProduk> items;
    private Context ctx;

    public AdapterProduk(Context context) {
        this.items = new ArrayList<>();
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.price_strike)
        TextView priceStrike;
        @BindView(R.id.name)
        TextView name;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final DataItemProduk o = items.get(position);
            if(o.getHargaDiskon() > 0){
                view.price.setText(Tools.getFormattedPrice((double) o.getHargaDiskon(), ctx));
                view.priceStrike.setText(Tools.getFormattedPrice((double) o.getHargaDiskon(), ctx));
                view.priceStrike.setPaintFlags(view.priceStrike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                view.priceStrike.setVisibility(View.VISIBLE);
            } else {
                view.price.setText(Tools.getFormattedPrice((double) o.getHarga(), ctx));
                view.priceStrike.setVisibility(View.GONE);
            }
            view.name.setText(o.getNama());

            Glide.with(ctx).load(OauthConstans.UPLOAD_URL + o.getGambarUtama()).into(view.image);
//            view.btnEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClick.onItemEditClick(position, o);
//                }
//            });
//            view.btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClick.onItemDeleteClick(position, o);
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(DataItemProduk dataItemRapatK3) {
        items.add(dataItemRapatK3);
        notifyItemInserted(items.size() + 1);
    }

    public void addAll(List<DataItemProduk> items) {
        for (DataItemProduk rapatK3 : items) {
            add(rapatK3);
        }
    }

    public void remove(DataItemProduk Memo) {
        int position = items.indexOf(Memo);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void swap(List<DataItemProduk> datas) {
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
}