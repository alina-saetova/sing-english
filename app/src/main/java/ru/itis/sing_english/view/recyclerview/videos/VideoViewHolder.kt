package ru.itis.sing_english.view.recyclerview.videos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_video.*
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.model.VideoItem

class VideoViewHolder (override val containerView: View,
                       private val openLambda: (String) -> Unit
//                       private val likeLambda: (String) -> Unit
)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(video: Video) {
        setTitle(video.title, video.channelTitle)

        Glide.with(containerView)
            .load(video.imgUrl)
            .into(iv_cover)
        itemView.setOnClickListener {
            openLambda(video.videoId)
        }
//        ib_like.setOnClickListener{
//            likeLambda(video.id.videoId)
//        }
    }

    fun updateFromBundle(bundle: Bundle) {
        for (key in bundle.keySet()) {
            if (key == "title") {
                setTitle(bundle.getString(key).toString(), bundle.getString("artist").toString())
            } else if(key == "cover") {
                Glide.with(containerView)
                    .load(bundle.getString(key).toString())
                    .into(iv_cover)
            }
        }
    }

    private fun setTitle(title: String, channelTitle: String) {
        val artist = if (channelTitle.contains("VEVO")) {
            channelTitle.removeRange(channelTitle.length - 4, channelTitle.length)
        } else {
            channelTitle
        }
        val sTitle = title.split(" - ")
        tv_artist.text = artist
        tv_name.text = sTitle[sTitle.size - 1]
    }

    companion object {

        fun create(parent: ViewGroup,
                   clickLambda: (String) -> Unit
//                   likeLambda: (String) -> Unit
        ) = VideoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_video,
                    parent,
                    false
                ),
                clickLambda
//                likeLambda
            )
    }
}
