package co.id.wargamandiri.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DataItemKategori;
import co.id.wargamandiri.models.DataItemKategori;
import co.id.wargamandiri.services.OauthConstans;

public class AdapterKategori extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClick {
        void onItemEditClick(int pos, DataItemKategori dataItemBanner);

        void onItemDeleteClick(int pos, DataItemKategori dataItemBanner);
    }

    public OnItemClick onItemClick;

    private List<DataItemKategori> items;
    private Context ctx;

    public AdapterKategori(Context context) {
        this.items = new ArrayList<>();
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_bg)
        ImageView imageBg;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.btn_edit)
        ImageButton btnEdit;
        @BindView(R.id.btn_delete)
        ImageButton btnDelete;
        @BindView(R.id.lyt_parent)
        LinearLayout lytParent;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final DataItemKategori o = items.get(position);
            view.title.setText(o.getKeterangan());
            Glide.with(ctx).load(OauthConstans.UPLOAD_URL + o.getGambar()).into(view.imageBg);
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

    public void add(DataItemKategori dataItemRapatK3) {
        items.add(dataItemRapatK3);
        notifyItemInserted(items.size() + 1);
    }

    public void addAll(List<DataItemKategori> items) {
        for (DataItemKategori rapatK3 : items) {
            add(rapatK3);
        }
    }

    public void remove(DataItemKategori Memo) {
        int position = items.indexOf(Memo);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void swap(List<DataItemKategori> datas) {
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