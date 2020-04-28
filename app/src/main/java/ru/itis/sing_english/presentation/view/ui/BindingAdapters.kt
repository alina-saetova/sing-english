package ru.itis.sing_english.presentation.view.ui

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
import ru.itis.sing_english.data.model.*
import ru.itis.sing_english.presentation.view.recyclerview.BindableAdapter

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

@BindingAdapter("word")
fun onWordClick(view: TextView, flag: Boolean) {
    if (flag) {
        view.setTextColor(view.resources.getColor(R.color.colorRight))
    }
    else {
        view.setTextColor(view.resources.getColor(R.color.colorAccent))
    }
}

@BindingAdapter("wordDetailTransl")
fun setWordDetailTransl(view: TextView, list: List<Tr>?) {
    if (list == null) {
        view.visibility = View.GONE
        return
    }
    else {
        view.visibility = View.VISIBLE
    }
    val newList = mutableListOf<String>()
    for (tr in list) {
        newList.add(tr.transl)
    }
    view.text = newList.joinToString(", ")
}

@BindingAdapter("wordDetailTitle")
fun setWordDetailTitle(view: TextView, str: String?) {
    if (str == null) {
        view.visibility = View.GONE
    }
    else {
        view.visibility = View.VISIBLE
        view.text = str
    }
}

@BindingAdapter("wordDetailSynonimsTitle")
fun setWordDetailSynonimsTitle(view: TextView, list: List<Mean>?) {
    if (list == null) {
        view.visibility = View.GONE
    }
    else {
        view.visibility = View.VISIBLE
        view.text = "synonims"
    }
}

@BindingAdapter("wordDetailSynonimsLayout")
fun setWordDetailSynonimsLayout(view: View, list: List<Mean>?) {
    if (list == null) {
        view.visibility = View.GONE
    }
    else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("wordDetailSynonimsText")
fun setWordDetailSynonimsText(view: TextView, list: List<Mean>?) {
    if (list == null) {
        view.visibility = View.GONE
        return
    }
    else {
        view.visibility = View.VISIBLE
    }
    val newList = mutableListOf<String>()
    for (mean in list) {
        newList.add(mean.synonym)
    }
    view.text = newList.joinToString(", ")
}

@BindingAdapter("wordDetailExamples")
fun setWordDetailExample(view: TextView, list: List<Ex>?) {
    if (list == null) {
        view.visibility = View.GONE
    }
    else {
        view.visibility = View.VISIBLE
        view.text = "examples"
    }
}

@BindingAdapter("wordDetailExamplesLayout")
fun setWordDetailExample(view: View, list: List<Ex>?) {
    if (list == null) {
        view.visibility = View.GONE
    }
    else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("wordDetailExamplesTransl")
fun setWordDetailExampleTransl(view: TextView, list: List<Tr>?) {
    if (list == null) {
        view.visibility = View.GONE
    }
    else {
        view.visibility = View.VISIBLE
        view.text = list[0].transl
    }
}

@BindingAdapter("wordDetailExamplesText")
fun setWordDetailExampleText(view: TextView, list: List<Ex>?) {
    if (list == null) {
        view.visibility = View.GONE
    }
    else {
        view.visibility = View.VISIBLE
        view.text = list[0].example
    }
}

@BindingAdapter("loading")
fun onLoad(view: View, loadingStatus: LoadingStatus?) {
    loadingStatus?.let {
        when(it) {
            LoadingStatus.RUNNING -> view.visibility = View.INVISIBLE
            LoadingStatus.SUCCESS -> view.visibility = View.VISIBLE
            LoadingStatus.FAILED -> view.visibility = View.GONE
        }
    }
}
