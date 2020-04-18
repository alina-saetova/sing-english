package ru.itis.sing_english.view.recyclerview.words

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.view.recyclerview.BindableAdapter

class WordAdapter(
    private var list: MutableList<Word>,
    private var wordClickListener: (String) -> Unit,
    private var deleteClickListener: (Word) -> Unit
) : RecyclerView.Adapter<WordsViewHolder>(),
    BindableAdapter<MutableList<Word>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder =
        WordsViewHolder.create(
            parent
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(list[position], wordClickListener)
        holder.binding.ibDelete.setOnClickListener {
            deleteClickListener(list[position])
            delete(position)
        }
        holder.binding.executePendingBindings()
    }

    override fun update(data: MutableList<Word>?) {
        if (data == null) {
            return
        }
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private fun delete(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }
}
