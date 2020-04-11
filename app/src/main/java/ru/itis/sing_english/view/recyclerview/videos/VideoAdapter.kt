package ru.itis.sing_english.view.recyclerview.videos

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.view.recyclerview.BindableAdapter

class VideoAdapter(
    private var list: MutableList<Video>,
    private val videoClickListener: (String) -> Unit,
    private val likeClickListener: (Video, String) -> Unit
) : RecyclerView.Adapter<VideoViewHolder>(),
    BindableAdapter<MutableList<Video>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(list[position], videoClickListener, likeClickListener)
    }

    override fun update(data: MutableList<Video>?) {
        if (data == null) {
            return
        }
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}
