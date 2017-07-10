package nz.co.udenbrothers.clockwork.itemRecycler;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.abstractions.Provider;
import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.HeaderViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.ItemHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.NoticeViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.SiteViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.StampViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.TopViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.TotalViewHolder;


public class ItemAdaptor extends RecyclerView.Adapter<ItemHolder>{

    private ArrayList<Item> items;
    private SparseArray<Provider> holders = new SparseArray<>();

    public ItemAdaptor(ArrayList<Item> items) {
        this.items = items;

        holders.put(Type.PROJECT, v-> new SiteViewHolder(inflate(v, R.layout.site_card_layout)));
        holders.put(Type.TOTAL, v-> new TotalViewHolder(inflate(v, R.layout.total_card_layout)));
        holders.put(Type.TOP, v-> new TopViewHolder(inflate(v, R.layout.top_card_layout)));
        holders.put(Type.SHIFT, v-> new StampViewHolder(inflate(v, R.layout.stamp_card_layout)));
        holders.put(Type.HEADER, v-> new HeaderViewHolder(inflate(v, R.layout.header_card_layout)));
        holders.put(Type.NOTICE, v-> new NoticeViewHolder(inflate(v, R.layout.notice_card_layout)));
        holders.put(Type.NOTICE, v-> new NoticeViewHolder(inflate(v, R.layout.site_card_layout)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(int pos, Item item){
        items.add(pos, item);
        notifyItemInserted(pos);
    }

    private View inflate(ViewGroup viewGroup, int id){
        return  LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
    }

    public void delete(int index){
        items.remove(index);
        notifyItemRemoved(index);
    }

    public void update(ArrayList<Item> newItems){
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int i) {
        holder.init(items.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return holders.get(i).getHolder(viewGroup);
    }
}
