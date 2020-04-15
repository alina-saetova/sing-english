package ru.itis.sing_english.view.ui

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.State
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.view.recyclerview.BindableAdapter

@BindingAdapter("recycler")
fun <T> setSubsRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).update(data)
    }
}

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}

@BindingAdapter("btnlike")
fun setImage(view: ImageButton, like: Boolean) {
    if (like) {
        view.setImageResource(R.drawable.ic_favourites_full)
    }
    else {
        view.setImageResource(R.drawable.ic_favourites_empty)
    }
}

@BindingAdapter("btnColor")
fun changeColor(view: View, state: State?) {
    Log.e("btnColor", state.toString())
    if (state == null) {
        view.setBackgroundResource(R.color.colorDefault)
        return
    }
    when (state) {
        State.DEFAULT -> view.setBackgroundResource(R.color.colorDefault)
        State.RIGHT -> view.setBackgroundResource(R.color.colorRight)
        State.WRONG -> view.setBackgroundResource(R.color.colorWrong)
    }
}

@BindingAdapter(value = ["setupVisibility"])
fun ProgressBar.progressVisibility(loadingStatus: LoadingStatus?) {
    loadingStatus?.let {
        isVisible = when(it) {
            LoadingStatus.RUNNING -> true
            LoadingStatus.SUCCESS -> false
            LoadingStatus.FAILED -> false
        }
    }
}

@BindingAdapter(value = ["bind:correctness", "bind:missed"])
fun setColor(view: View, isCorrect: Boolean, wasMissed: Boolean) {
    if (wasMissed) {
        if (isCorrect) {
            (view as TextView).setBackgroundResource(R.color.colorRight)
        }
        else {
            (view as TextView).setBackgroundResource(R.color.colorWrong)
        }
    }
}
