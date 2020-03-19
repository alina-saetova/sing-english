package ru.itis.sing_english.view.recyclerview.songs_row

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.ItemSubtitleBinding

class SubtitleViewHolder (val binding: ItemSubtitleBinding)
    : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun create(parent: ViewGroup) =
            SubtitleViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_subtitle,
                    parent,
                    false
                )
            )
    }
}
