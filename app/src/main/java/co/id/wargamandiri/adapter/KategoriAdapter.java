package co.id.wargamandiri.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import co.id.wargamandiri.data.Constans;
import co.id.wargamandiri.models.DataItemKategori;


/**
 * Created by Comp on 2/11/2018.
 */

public class KategoriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DataItemKategori> listItem;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemEdit(int position);

        void onItemDelete(int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public KategoriAdapter(Context ctx) {
        this.ctx = ctx;
        listItem = new ArrayList<>();
    }

    public KategoriAdapter(Context ctx, List<DataItemKategori> listItem) {
        this.listItem = listItem;
        this.ctx = ctx;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_kategori)
        ImageView imgKategori;
        @BindView(R.id.tv_kategori)
        TextView tvKategori;
        @BindView(R.id.lay_item_click)
        LinearLayout layItemClick;
        @BindView(R.id.more)
        ImageButton more;


        public OriginalViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            DataItemKategori item = listItem.get(position);
            Glide.with(ctx).load(Constans.WEB_URL_STORAGE + item.getGambar()).into(view.imgKategori);
            view.tvKategori.setText(item.getNama());

            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
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
        return listItem.size();
    }

    private void onMoreButtonClick(final View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(ctx, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_id_edit:
                        mOnItemClickListener.onItemEdit(pos);
                        break;
                    case R.id.menu_id_delete:
                        mOnItemClickListener.onItemDelete(pos);
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_more);
        popupMenu.show();
    }

    public void add(DataItemKategori item) {
        listItem.add(item);
        notifyItemInserted(listItem.size() + 1);
    }

    public void addAll(List<DataItemKategori> listItem) {
        for (DataItemKategori item : listItem) {
            add(item);
        }
    }

    public void removeAll() {
        listItem.clear();
        notifyDataSetChanged();
    }

    public void swap(List<DataItemKategori> datas) {
        if (datas == null || datas.size() == 0) listItem.clear();
        if (listItem != null && listItem.size() > 0)
            listItem.clear();
        listItem.addAll(datas);
        notifyDataSetChanged();

    }

    public DataItemKategori getItem(int pos) {
        return listItem.get(pos);
    }

    public void setFilter(List<DataItemKategori> list) {
        listItem = new ArrayList<>();
        listItem.addAll(list);
        notifyDataSetChanged();
    }

    public List<DataItemKategori> getListItem() {
        return listItem;
    }


}
