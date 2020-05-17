package ru.itis.sing_english.presentation.view.recyclerview.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.databinding.ItemWordBinding

class WordsViewHolder (val binding: ItemWordBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(word: Word,
             wordClickListener: (String, Boolean) -> Unit
    ) {
        binding.word = word
        binding.root.setOnClickListener {
            wordClickListener(word.text, true)
        }
    }

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
