package ru.itis.sing_english.view.recyclerview.videos

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.model.VideoItem

class VideoItemDiffCallback(
    private var oldList: MutableList<Video>,
    private var newList: MutableList<Video>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        val diffBundle = Bundle()
        if (newItem.title != oldItem.title) {
            diffBundle.putString("title", newItem.title)
            diffBundle.putString("artist", newItem.channelTitle)
        }
        if (newItem.imgUrl != oldItem.imgUrl) {
            diffBundle.putString("cover", newItem.imgUrl)
        }

        return if (diffBundle.size() == 0) null else diffBundle
    }

}
