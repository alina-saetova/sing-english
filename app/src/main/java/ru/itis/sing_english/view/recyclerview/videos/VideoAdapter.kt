package ru.itis.sing_english.view.recyclerview.videos

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
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
        if (list[position].like) {
            holder.binding.ibLike.tag = "like"
        }
        else {
            holder.binding.ibLike.tag = "unlike"
        }
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener {
            clickListener.onVideoClickListener(list[position].videoId)
        }
        holder.binding.ibLike.setOnClickListener {
            if (holder.binding.ibLike.tag == "like") {
                clickListener.onLikeClickListener(list[position], "like")
                delete(position)
            }
            else {
                clickListener.onLikeClickListener(list[position], "unlike")
                holder.binding.ibLike.setImageResource(R.drawable.ic_favourites_full)
            }
        }
    }

    override fun update(data: MutableList<Video>?) {
        if (data == null) return
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private fun delete(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }
}
