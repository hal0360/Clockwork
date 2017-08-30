package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.abstractions.Cmd;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;

public abstract class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected RelativeLayout card;
    private SparseArray<Cmd> cmds = new SparseArray<>();
    protected Context context;
    private CollectionView collectionView;

    public ItemHolder(CollectionView cv, int Rid) {
        super(LayoutInflater.from(cv.tempVG.getContext()).inflate(Rid, cv.tempVG, false));
        card =  (RelativeLayout) findView(R.id.card);
        context = cv.context;
        collectionView = cv;
    }

    protected final <T extends View & Checkable> T findView(int Rid){
        return itemView.findViewById(Rid);
    }

    protected final void delete(){
        collectionView.delete(getAdapterPosition());
    }

    protected final void setHeight(int h){
        ViewGroup.LayoutParams layoutParams = card.getLayoutParams();
        layoutParams.height = h;
        card.setLayoutParams(layoutParams);
    }

    protected final void clicked(View v, Cmd cd){
        v.setOnClickListener(this);
        cmds.put(v.getId(),cd);
    }

    protected final void clicked(int id, Cmd cd){
        findView(id).setOnClickListener(this);
        cmds.put(id,cd);
    }

    protected final void expandView(int height) {
        ValueAnimator anim = ValueAnimator.ofInt(card.getMeasuredHeightAndState(), height);
        anim.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            setHeight(val);
        });
        anim.start();
    }

    public abstract void init(Item item);

    public abstract void cleanUp();

    @Override
    public final void onClick(View v) {
        cmds.get(v.getId()).exec();
    }
}
