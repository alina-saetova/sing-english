package ru.itis.sing_english.view.recyclerview.songs_row

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_subtitle.*
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Subtitle

class SubtitleViewHolder (override val containerView: View)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(sub: Subtitle) {
        tv_row.text = sub.row.text
    }

    fun updateFromBundle(bundle: Bundle) {
        for (key in bundle.keySet()) {
            if (key == "text") {
                tv_row.text = bundle.getString(key)
            }
        }
    }

    companion object {

        fun create(parent: ViewGroup) =
            SubtitleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_subtitle,
                    parent,
                    false
                )
            )
    }
}
