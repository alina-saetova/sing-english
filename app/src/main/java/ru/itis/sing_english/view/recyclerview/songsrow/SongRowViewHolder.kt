package ru.itis.sing_english.view.recyclerview.songsrow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.SongRow
import ru.itis.sing_english.databinding.ItemSongRowBinding

class SongRowViewHolder (val binding: ItemSongRowBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(songRow: SongRow) {
        binding.songRow = songRow
        binding.executePendingBindings()
    }

    companion object {

        fun create(parent: ViewGroup) =
            SongRowViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_song_row,
                    parent,
                    false
                )
            )
    }
}
