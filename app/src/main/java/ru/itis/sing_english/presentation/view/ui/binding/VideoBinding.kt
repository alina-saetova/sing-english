package ru.itis.sing_english.presentation.view.ui.binding

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import ru.itis.sing_english.R

@BindingAdapter("btnlike")
fun setImage(view: ImageButton, like: Boolean) {
    if (like) {
        view.setImageResource(R.drawable.ic_favourites_full)
    } else {
        view.setImageResource(R.drawable.ic_favourites_empty)
    }
}

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}
