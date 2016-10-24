package fi.zalando.core.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import fi.zalando.core.ui.adapter.viewholder.BaseViewHolder;
import fi.zalando.core.ui.callback.DiffUtilCallback;

/**
 * Abstract activity that holds common methods usable by all the {@link RecyclerView.Adapter} on the
 * app.
 *
 * Created by jduran on 12/01/16.
 */
public abstract class BaseAdapter<T, U extends BaseViewHolder<T>> extends RecyclerView.Adapter<U> {

    /**
     * Mod count used by UI tests to check for changes in this adapter.
     */
    public transient int modCountUITest = 0;
    protected List<T> items;

    /**
     * Constructor
     *
     * @param items {@link List} of {@link T} to render in the list
     */
    protected BaseAdapter(@NonNull List<T> items) {
        modCountUITest = 0;
        this.items = items;
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.bindData(items.get(position));
    }

    /**
     * Removes the item in the given position and updates the UI animating the removal.
     *
     * @param index item's index
     */
    public void removeItem(int index) {
        modCountUITest++;
        items.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * Inserts the given item in the given location and updates the UI with animation.
     *
     * @param item     Item to add
     * @param location Position to add the item in
     */
    public void addItem(T item, int location) {
        modCountUITest++;
        items.add(location, item);
        notifyItemInserted(location);
    }

    /**
     * Adds the given items and updates the UI with animation.
     *
     * @param itemsToAdd Items to add
     */
    public void addItems(List<T> itemsToAdd) {
        modCountUITest++;
        int count = itemsToAdd.size();
        int originalSize = items.size();
        items.addAll(itemsToAdd);
        notifyItemRangeInserted(originalSize, count);
    }

    /**
     * Clears and updates the adapters item list with the given items, and calls
     * notifyDataSetChanged to refresh the UI.
     *
     * @param items List of items to be added to the adapter.
     */
    public void setItems(@Nullable final List<T> items) {
        modCountUITest++;

        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }

    /**
     * Swaps current items on the list with the new items provided animating them properly using
     * notifyDatasetInserted, changed, etc. as expected according to the comparison
     *
     * @param newItems   {@link List} with the new items
     * @param comparator {@link DiffUtilCallback.DiffUtilComparator} to compare items using
     *                   DiffUtils
     */
    public void swapItems(final List<T> newItems,
                          DiffUtilCallback.DiffUtilComparator<T> comparator) {

        final DiffUtilCallback<T> diffCallback =
                new DiffUtilCallback<T>(items, newItems, comparator);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        items.clear();
        items.addAll(newItems);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public abstract U onCreateViewHolder(ViewGroup parent, int viewType);

}
