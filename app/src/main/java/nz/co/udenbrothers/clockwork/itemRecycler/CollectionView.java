package nz.co.udenbrothers.clockwork.itemRecycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

import nz.co.udenbrothers.clockwork.abstractions.Provider;
import nz.co.udenbrothers.clockwork.global.V;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.HeaderViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.ItemHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.NoticeViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.ProjectViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.SiteViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.StampViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.TopViewHolder;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.TotalViewHolder;

public class CollectionView extends RecyclerView{

    public Context context;
    private MyAdaptor myAdaptor;
    public ViewGroup tempVG;
    private SparseArray<Provider> holders = new SparseArray<>();

    private void setHolders(){
        holders.put(V.PROJECT, ProjectViewHolder::new);
        holders.put(V.TOTAL, TotalViewHolder::new);
        holders.put(V.TOP, TopViewHolder::new);
        holders.put(V.SHIFT, StampViewHolder::new);
        holders.put(V.HEADER, HeaderViewHolder::new);
        holders.put(V.NOTICE, NoticeViewHolder::new);
        holders.put(V.SITE, SiteViewHolder::new);
    }

    public CollectionView(Context context)
    {
        super(context);
        this.context = context;
    }

    public CollectionView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    public CollectionView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void init(List<Item> items){
        setHolders();
        myAdaptor = new MyAdaptor(items);
        setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(llm);
        setAdapter(myAdaptor);
    }

    public void add(int pos, Item item){
        myAdaptor.items.add(pos, item);
        myAdaptor.notifyItemInserted(pos);
    }

    public void delete(int index){
        myAdaptor.items.remove(index);
        myAdaptor.notifyItemRemoved(index);
    }

    public void refresh(List<Item> newItems){
        myAdaptor.items.clear();
        myAdaptor.items.addAll(newItems);
        myAdaptor.notifyDataSetChanged();
        scrollToPosition(0);
    }

    private CollectionView getThis(){
        return this;
    }

    private class MyAdaptor extends RecyclerView.Adapter<ItemHolder>{

        public List<Item> items;

        public MyAdaptor(List<Item> items) {
            this.items = items;
        }

        @Override
        public int getItemCount() {
            return items.size();
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
            tempVG = viewGroup;
            return holders.get(i).getHolder(getThis());
        }

        @Override
        public void onViewDetachedFromWindow(ItemHolder holder) {
            holder.cleanUp();
        }

        @Override
        public void onViewRecycled (ItemHolder holder) {
            holder.cleanUp();
        }
    }
}
