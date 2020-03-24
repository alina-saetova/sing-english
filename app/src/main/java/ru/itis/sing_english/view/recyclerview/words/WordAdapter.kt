package ru.itis.sing_english.view.recyclerview.words

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.view.recyclerview.BindableAdapter

class WordAdapter(
    private var list: MutableList<Word>,
    private var clickListener: WordClickListener
) : RecyclerView.Adapter<WordsViewHolder>(),
    BindableAdapter<MutableList<Word>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder =
        WordsViewHolder.create(
            parent
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.binding.word = list[position]
        holder.binding.ibDelete.setOnClickListener {
            clickListener.onWordDeleteListener(list[position].id)
        }
    }

    override fun update(data: MutableList<Word>?) {
        if (data == null) return
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}
