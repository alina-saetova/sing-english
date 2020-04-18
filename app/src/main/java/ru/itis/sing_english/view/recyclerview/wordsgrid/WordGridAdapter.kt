package ru.itis.sing_english.view.recyclerview.wordsgrid

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.model.WordGrid
import ru.itis.sing_english.view.recyclerview.BindableAdapter

class WordGridAdapter(
    private var list: MutableList<WordGrid>,
    private var wordClickListener: (String, Int) -> Unit
) : RecyclerView.Adapter<WordGridViewHolder>(),
    BindableAdapter<MutableList<WordGrid>>{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordGridViewHolder =
        WordGridViewHolder.create(
            parent
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: WordGridViewHolder, position: Int) {
        holder.bind(list[position], position, wordClickListener)
        holder.binding.executePendingBindings()
    }

    override fun update(data: MutableList<WordGrid>?) {
        if (data == null) {
            return
        }
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

}
