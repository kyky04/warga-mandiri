package co.id.wargamandiri.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DataItemMember;
import co.id.wargamandiri.utils.Tools;
import de.hdodenhof.circleimageview.CircleImageView;

import static co.id.wargamandiri.data.Constans.WEB_URL;
import static co.id.wargamandiri.data.Constans.WEB_URL_STORAGE;

public class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public interface OnItemClick {
        void onChat(int pos);

        void onInfo(int pos);

        void onSwicth(int pos, boolean checked);
    }

    public OnItemClick onItemClick;

    private List<DataItemMember> items;
    private Context ctx;

    public MemberAdapter(Context context) {
        this.items = new ArrayList<>();
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.email)
        TextView email;
        @BindView(R.id.lyt_parent)
        LinearLayout lytParent;
        @BindView(R.id.btn_edit)
        ImageButton btnEdit;
        @BindView(R.id.btn_delete)
        ImageButton btnDelete;
        @BindView(R.id.sw_member)
        Switch swMember;
        @BindView(R.id.img_member)
        CircleImageView imgMember;
        @BindView(R.id.btn_info)
        ImageView btnInfo;
        @BindView(R.id.btn_chat)
        ImageView btnChat;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final DataItemMember item = items.get(position);
            view.nama.setText(item.getName());
            view.email.setText(item.getEmail());

            if (item.getStatus() == 0) {
                view.swMember.setChecked(false);
            } else {
                view.swMember.setChecked(true);
            }

//            Tools.displayImageOriginal(ctx, view.imgMember, WEB_URL_STORAGE + item.getFoto());

            view.swMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    onItemClick.onSwicth(position, b);
                }
            });
            view.btnChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onChat(position);
                }
            });
            view.btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onInfo(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(DataItemMember dataItem) {
        items.add(dataItem);
        notifyItemInserted(items.size() + 1);
    }

    public void addAll(List<DataItemMember> items) {
        for (DataItemMember rapatK3 : items) {
            add(rapatK3);
        }
    }

    public void remove(DataItemMember item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void swap(List<DataItemMember> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (items != null && items.size() > 0)
            items.clear();
        items.addAll(datas);
        notifyDataSetChanged();

    }

    public DataItemMember getItem(int pos) {
        return items.get(pos);
    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}