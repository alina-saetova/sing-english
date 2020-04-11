package ru.itis.sing_english.view.recyclerview.songsrow

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.view.recyclerview.BindableAdapter

class SubtitleAdapter(private var list: MutableList<Subtitle>)
    : RecyclerView.Adapter<SubtitleViewHolder>(),
    BindableAdapter<MutableList<Subtitle>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtitleViewHolder =
        SubtitleViewHolder.create(
            parent
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SubtitleViewHolder, position: Int) {
        holder.binding.subtitle = list[position]
        holder.binding.executePendingBindings()
    }

    override fun update(data: MutableList<Subtitle>?) {
        if (data.isNullOrEmpty()) {
            return
        }
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}
