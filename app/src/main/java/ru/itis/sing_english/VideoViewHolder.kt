package ru.itis.sing_english

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_video.*

class VideoViewHolder (override val containerView: View,
                       private val clickLambda: (String) -> Unit)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(video: VideoItem) {
        setTitle(video.snippet.title)

//      TODO: глайдом или чем-то скачивать картинки
//        iv_cover.setImageResource()

        itemView.setOnClickListener {
            clickLambda(video.id.videoId)
        }
    }

    fun updateFromBundle(bundle: Bundle) {
        for (key in bundle.keySet()) {
            if (key == "title") {
                setTitle(bundle.getString(key).toString())
            } else if (key == "name") {
                tv_name.text = bundle.getDouble(key).toString()
            }
        }
    }

    private fun setTitle(title: String) {
        val sTitle = title.split(" - ")
        tv_artist.text = sTitle[0]
        tv_name.text = sTitle[1]
    }

    companion object {

        fun create(parent: ViewGroup, clickLambda: (String) -> Unit) =
            VideoViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false),
                clickLambda
            )
    }

}
