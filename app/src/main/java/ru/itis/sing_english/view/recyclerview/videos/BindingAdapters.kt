package ru.itis.sing_english.view.recyclerview.videos

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.itis.sing_english.view.recyclerview.BindableAdapter

@BindingAdapter("subs")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).update(data)
    }
}
