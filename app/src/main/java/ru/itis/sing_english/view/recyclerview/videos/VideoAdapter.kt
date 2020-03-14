package ru.itis.sing_english.view.recyclerview.videos

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.view.recyclerview.songs_row.BindableAdapter

class VideoAdapter(
    private var list: MutableList<Video>,
    private var openLambda: (String) -> Unit
//    private var likeLambda: (String) -> Unit
) : RecyclerView.Adapter<VideoViewHolder>(),
    BindableAdapter<MutableList<Video>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder.create(
            parent,
            openLambda
//            likeLambda
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
    override fun update(data: MutableList<Video>?) {
        if (data.isNullOrEmpty()) return
        val callback =
            VideoItemDiffCallback(
                list,
                data
            )
        val diffResult = DiffUtil.calculateDiff(callback, true)
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(data)
    }
}
