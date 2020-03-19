package ru.itis.sing_english.view.recyclerview.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.ItemWordBinding

class WordsViewHolder (val binding: ItemWordBinding)
    : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun create(parent: ViewGroup) =
            WordsViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_word,
                    parent,
                    false
                )
            )
    }
}
