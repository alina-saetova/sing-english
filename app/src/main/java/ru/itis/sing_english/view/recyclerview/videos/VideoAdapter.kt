package ru.itis.sing_english.view.recyclerview.videos

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.VideoItem

class VideoAdapter(
    private var list: MutableList<VideoItem>,
    private var clickLambda: (String) -> Unit) : RecyclerView.Adapter<VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder.create(
            parent,
            clickLambda
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onBindViewHolder(
        holder: VideoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val bundle = payloads[0] as Bundle
            holder.updateFromBundle(bundle)
        }
    }
    fun update(newList: MutableList<VideoItem>) {
        val callback =
            VideoItemDiffCallback(
                list,
                newList
            )
        val diffResult = DiffUtil.calculateDiff(callback, true)
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(newList)
    }
}
