package ru.itis.sing_english.view.recyclerview.words

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.Word

class WordsAdapter(
    private var list: MutableList<Word>,
    private var clickLambda: (String) -> Unit
) : RecyclerView.Adapter<WordsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder =
        WordsViewHolder.create(
            parent,
            clickLambda
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onBindViewHolder(
        holder: WordsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val bundle = payloads[0] as Bundle
            holder.updateFromBundle(bundle)
        }
    }
    fun update(newList: MutableList<Word>) {
        val callback =
            WordsItemDiffCallback(
                list,
                newList
            )
        val diffResult = DiffUtil.calculateDiff(callback, true)
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(newList)
    }
}
