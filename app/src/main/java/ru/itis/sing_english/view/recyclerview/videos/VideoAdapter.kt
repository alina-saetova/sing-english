package ru.itis.sing_english.view.recyclerview.videos

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.view.recyclerview.BindableAdapter

class VideoAdapter(
    private var list: MutableList<Video>,
    private var clickListener: VideoClickListener
) : RecyclerView.Adapter<VideoViewHolder>(),
    BindableAdapter<MutableList<Video>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder.create(
            parent
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.binding.video = list[position]
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener {
            clickListener.onVideoClickListener(holder.binding.ibLike, list[position].videoId)
        }
        holder.binding.ibLike.setOnClickListener {
            clickListener.onLikeClickListener(holder.binding.ibLike, list[position])
        }
    }

    override fun update(data: MutableList<Video>?) {
        if (data == null) return
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}
