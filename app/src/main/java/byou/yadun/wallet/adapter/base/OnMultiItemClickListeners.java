package byou.yadun.wallet.adapter.base;

/**
 *
 */
public interface OnMultiItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position, int viewType);
}
