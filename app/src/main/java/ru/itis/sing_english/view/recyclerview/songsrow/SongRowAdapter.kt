package ru.itis.sing_english.view.recyclerview.songsrow

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.SongRow
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.view.recyclerview.BindableAdapter

class SongRowAdapter(private var list: MutableList<SongRow>)
    : RecyclerView.Adapter<SongRowViewHolder>(),
    BindableAdapter<MutableList<SongRow>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongRowViewHolder =
        SongRowViewHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SongRowViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun update(data: MutableList<SongRow>?) {
        if (data.isNullOrEmpty()) {
            return
        }
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}
