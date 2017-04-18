package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.abstractions.Cmd;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;


public abstract class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected RelativeLayout card;
    private SparseArray<Cmd> cmds = new SparseArray<>();

    public ItemHolder(View v) {
        super(v);
        card =  (RelativeLayout) v.findViewById(R.id.card);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected final void expandView(int height) {
        ValueAnimator anim = ValueAnimator.ofInt(card.getMeasuredHeightAndState(), height);
        anim.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            setHeight(val);
        });
        anim.start();
    }

    public abstract void init(Item item);

    @Override
    public final void onClick(View v) {
        cmds.get(v.getId()).exec();
    }
}
