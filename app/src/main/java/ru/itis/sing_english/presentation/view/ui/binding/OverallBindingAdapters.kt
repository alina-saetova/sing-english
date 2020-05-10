package ru.itis.sing_english.presentation.view.ui.binding

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.presentation.view.recyclerview.BindableAdapter

@BindingAdapter("recycler")
fun <T> setSubsRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).update(data)
    }
}

@BindingAdapter(value = ["setupVisibility"])
fun ProgressBar.progressVisibility(loadingStatus: LoadingStatus?) {
    loadingStatus?.let {
        isVisible = when (it) {
            LoadingStatus.RUNNING -> true
            LoadingStatus.SUCCESS -> false
            LoadingStatus.FAILED -> false
            LoadingStatus.NOT_FOUND -> false
        }
    }
}

@BindingAdapter("loading")
fun onLoad(view: View, loadingStatus: LoadingStatus?) {
    loadingStatus?.let {
        when (it) {
            LoadingStatus.RUNNING -> view.visibility = View.INVISIBLE
            LoadingStatus.SUCCESS -> view.visibility = View.VISIBLE
            LoadingStatus.FAILED -> view.visibility = View.GONE
            LoadingStatus.NOT_FOUND -> view.visibility = View.GONE
        }
    }
}
