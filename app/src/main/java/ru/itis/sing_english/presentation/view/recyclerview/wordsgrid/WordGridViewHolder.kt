package ru.itis.sing_english.presentation.view.recyclerview.wordsgrid

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.WordGrid
import ru.itis.sing_english.databinding.ItemWordGridBinding

class WordGridViewHolder (val binding: ItemWordGridBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        word: WordGrid,
        position: Int,
        wordClickListener: (String, Int) -> Unit
    ) {
        binding.word = word
        binding.root.setOnClickListener {
            if (word.flag) {
                binding.tvWord.setTextColor(ColorStateList.valueOf(itemView.context.getColor(R.color.colorTextWhite)))
            }
            else {
                binding.tvWord.setTextColor(ColorStateList.valueOf(itemView.context.getColor(R.color.colorAccent)))
            }
            wordClickListener(word.text, position)
        }
    }

    companion object {

        fun create(parent: ViewGroup) =
            WordGridViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_word_grid,
                    parent,
                    false
                )
            )

    }
}
