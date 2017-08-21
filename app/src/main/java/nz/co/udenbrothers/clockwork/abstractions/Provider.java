package nz.co.udenbrothers.clockwork.abstractions;

import android.view.ViewGroup;

import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.viewHolders.ItemHolder;

/**
 * Created by user on 07/04/2017.
 */

public interface Provider {

    public ItemHolder getHolder(CollectionView v);
}
