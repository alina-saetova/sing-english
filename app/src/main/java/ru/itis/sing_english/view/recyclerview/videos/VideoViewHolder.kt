package ru.itis.sing_english.view.recyclerview.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.databinding.ItemVideoBinding

class VideoViewHolder (val binding: ItemVideoBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(video: Video,
             videoClickListener: (String) -> Unit,
             likeClickListener: (Video, String) -> Unit
    ) {
        binding.video = video
        if (video.like) {
            binding.ibLike.tag = LIKE_PRESSED
        }
        else {
            binding.ibLike.tag = LIKE_NOT_PRESSED
        }
        binding.executePendingBindings()
        binding.root.setOnClickListener {
            videoClickListener(video.videoId)
        }
        binding.ibLike.setOnClickListener {
            if (binding.ibLike.tag == LIKE_PRESSED) {
                likeClickListener(video, LIKE_PRESSED)
                binding.ibLike.setImageResource(R.drawable.ic_favourites_empty)
            }
            else {
                likeClickListener(video, LIKE_NOT_PRESSED)
                binding.ibLike.setImageResource(R.drawable.ic_favourites_full)
            }
        }
        binding.executePendingBindings()
    }

    companion object {
        const val LIKE_PRESSED = "like"
        const val LIKE_NOT_PRESSED = "unlike"

        fun create(parent: ViewGroup) = VideoViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_video,
                    parent,
                    false
                )
            )
    }
}
