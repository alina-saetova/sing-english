package ru.itis.sing_english.view.recyclerview.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_word.*
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Word

class WordsViewHolder(override val containerView: View,
                       private val clickLambda: (String) -> Unit)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(word: Word) {
        tv_text.text = word.text
        tv_text.text = word.translation
        itemView.setOnClickListener {
            clickLambda(word.text)
        }
    }

    fun updateFromBundle(bundle: Bundle) {
        for (key in bundle.keySet()) {
            if (key == "text") {
                tv_text.text = bundle.getString(key)
            } else if(key == "translation") {
                tv_translation.text = bundle.getString(key)
            }
        }
    }

    companion object {

        fun create(parent: ViewGroup, clickLambda: (String) -> Unit) =
            WordsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_word,
                    parent,
                    false
                ),
                clickLambda
            )
    }
}
