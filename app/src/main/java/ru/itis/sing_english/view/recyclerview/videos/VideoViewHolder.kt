package ru.itis.sing_english.view.recyclerview.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.ItemVideoBinding

class VideoViewHolder (val binding: ItemVideoBinding)
    : RecyclerView.ViewHolder(binding.root) {

    companion object {
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
