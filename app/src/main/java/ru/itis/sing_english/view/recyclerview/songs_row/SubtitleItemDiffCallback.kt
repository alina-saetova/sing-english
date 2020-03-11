package ru.itis.sing_english.view.recyclerview.songs_row

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.itis.sing_english.data.model.Subtitle

class SubtitleItemDiffCallback(
    private var oldList: MutableList<Subtitle>,
    private var newList: MutableList<Subtitle>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        val diffBundle = Bundle()
        if (newItem.row.text != oldItem.row.text) {
            diffBundle.putString("text", newItem.row.text)
        }

        return if (diffBundle.size() == 0) null else diffBundle
    }

}
