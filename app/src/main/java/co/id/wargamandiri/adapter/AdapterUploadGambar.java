package co.id.wargamandiri.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.UploadGambar;

/**
 * Created by Comp on 2/11/2018.
 */

public class AdapterUploadGambar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isLoadingAdded = false;

    private boolean retryPageLoad = false;

    private String errorMsg;


    List<UploadGambar> gambarList;

    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnDataNull mOndataNull;

    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onItemClick(int position);
    }

    public interface OnDataNull {
        void onDataNull(int position);
    }

    public OnDataNull getmOndataNull() {
        return mOndataNull;
    }

    public void setmOndataNull(OnDataNull mOndataNull) {
        this.mOndataNull = mOndataNull;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterUploadGambar(Context ctx, List<UploadGambar> pesertaList) {
        this.gambarList = pesertaList;
        this.ctx = ctx;
    }

    public AdapterUploadGambar(Context ctx) {
        this.ctx = ctx;
        gambarList = new ArrayList<>();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_gambar)
        ImageView imgGambar;
        @BindView(R.id.tv_path)
        TextView tvPath;
        @BindView(R.id.view_item)
        LinearLayout viewItem;
        @BindView(R.id.delete)
        FloatingActionButton delete;

        public OriginalViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            delete.setOnClickListener(this);
            viewItem.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.view_item:
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                    break;
                case R.id.delete:
                    mOnItemClickListener.onDeleteClick(getAdapterPosition());
                    break;
            }
        }
    }

    protected class InputGambarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.view_item)
        RelativeLayout viewItem;

        public InputGambarHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.view_item:
                    mOnItemClickListener.onDeleteClick(getAdapterPosition());
                    break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_gambar, parent, false);
        viewHolder = new OriginalViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        UploadGambar itemGambar = gambarList.get(position);
        OriginalViewHolder view = (OriginalViewHolder) holder;
        Glide.with(ctx).load(gambarList.get(position).getImage()).into(view.imgGambar);
        view.tvPath.setText(gambarList.get(position).getPath());

    }

    @Override
    public int getItemCount() {
        return gambarList.size();
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

    public void add(UploadGambar uploadGambar) {
        gambarList.add(uploadGambar);
        notifyItemInserted(gambarList.size());
    }

    public void addAll(List<UploadGambar> itemPesertas) {
        for (UploadGambar mc : itemPesertas) {
            add(mc);
        }
    }

    public void remove(UploadGambar uploadGambar) {
        int position = gambarList.indexOf(uploadGambar);
        if (position > -1) {
            gambarList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
//        add(new Memo());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = gambarList.size() - 1;
        UploadGambar item = getItem(position);

        if (item != null) {
            gambarList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public UploadGambar getItem(int position) {
        return gambarList.get(position);
    }

    public void resetListData() {
        this.gambarList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        gambarList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(gambarList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


}
