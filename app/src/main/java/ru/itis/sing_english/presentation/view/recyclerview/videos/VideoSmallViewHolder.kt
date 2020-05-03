package ru.itis.sing_english.presentation.view.recyclerview.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.databinding.ItemVideoBinding

class VideoSmallViewHolder (val binding: ItemVideoBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(video: Video,
             position: Int,
             videoClickListener: (String) -> Unit,
             likeClickListener: (Video, String, Int, String) -> Unit,
             type: String
    ) {
        binding.video = video
        binding.root.setOnClickListener {
            videoClickListener(video.videoId)
        }
        binding.ibLike.setOnClickListener {
            if (video.like) {
                likeClickListener(video, LIKE_PRESSED, position, type)
                binding.ibLike.setImageResource(R.drawable.ic_favourites_empty)
            }
            else {
                likeClickListener(video, LIKE_NOT_PRESSED, position, type)
                binding.ibLike.setImageResource(R.drawable.ic_favourites_full)
            }
        }
        binding.executePendingBindings()
    }

    companion object {
        const val LIKE_PRESSED = "like"
        const val LIKE_NOT_PRESSED = "unlike"

        fun create(parent: ViewGroup) = VideoSmallViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_video,
                    parent,
                    false
                )
            )
    }
}
