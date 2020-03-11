package ru.itis.sing_english.view.recyclerview.songs_row

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.view.recyclerview.BindableAdapter

class SubtitleAdapter(private var list: MutableList<Subtitle>)
    : RecyclerView.Adapter<SubtitleViewHolder>(), BindableAdapter<MutableList<Subtitle>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtitleViewHolder =
        SubtitleViewHolder.create(
            parent
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SubtitleViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onBindViewHolder(
        holder: SubtitleViewHolder,
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
    override fun update(newList: MutableList<Subtitle>?) {
        if (newList.isNullOrEmpty()) return
        val callback =
            SubtitleItemDiffCallback(
                list,
                newList
            )
        val diffResult = DiffUtil.calculateDiff(callback, true)
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(newList)
    }
}
