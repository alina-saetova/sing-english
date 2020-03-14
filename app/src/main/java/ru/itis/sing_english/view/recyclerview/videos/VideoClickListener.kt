package ru.itis.sing_english.view.recyclerview.videos

import android.view.View
import ru.itis.sing_english.data.model.Video

interface VideoClickListener {

    fun onVideoClickListener(view: View, id: String)
    fun onLikeClickListener(view: View, video: Video)
}
