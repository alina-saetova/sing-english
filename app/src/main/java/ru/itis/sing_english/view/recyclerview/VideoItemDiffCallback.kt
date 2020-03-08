package ru.itis.sing_english.view.recyclerview

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.itis.sing_english.data.model.VideoItem

class VideoItemDiffCallback(
    private var oldList: MutableList<VideoItem>,
    private var newList: MutableList<VideoItem>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        val diffBundle = Bundle()
        if (newItem.snippet.title != oldItem.snippet.title) {
            diffBundle.putString("title", newItem.snippet.title)
            diffBundle.putString("artist", newItem.snippet.channelTitle)
        }
        if (newItem.snippet.thumbnails.high.url != oldItem.snippet.thumbnails.high.url) {
            diffBundle.putString("cover", newItem.snippet.thumbnails.high.url)
        }

        return if (diffBundle.size() == 0) null else diffBundle
    }

}
