package ru.itis.sing_english.presentation.view.ui.binding

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Ex
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Mean
import ru.itis.sing_english.data.model.Tr

@BindingAdapter("word")
fun onWordClick(view: TextView, flag: Boolean) {
    if (flag) {
        view.setTextColor(ColorStateList.valueOf(view.context.getColor(R.color.colorAccent)))
    } else {
        view.setTextColor(ColorStateList.valueOf(view.context.getColor(R.color.colorTextWhite)))
    }
}

@BindingAdapter("wordDetailTransl")
fun setWordDetailTransl(view: TextView, list: List<Tr>?) {
    if (list == null) {
        view.visibility = View.GONE
        return
    } else {
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
    } else {
        view.visibility = View.VISIBLE
        view.text = str
    }
}

@BindingAdapter("wordDetailSynonimsTitle")
fun setWordDetailSynonimsTitle(view: TextView, list: List<Mean>?) {
    if (list == null) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        view.text = "synonims"
    }
}

@BindingAdapter("wordDetailSynonimsLayout")
fun setWordDetailSynonimsLayout(view: View, list: List<Mean>?) {
    if (list == null) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("wordDetailSynonimsText")
fun setWordDetailSynonimsText(view: TextView, list: List<Mean>?) {
    if (list == null) {
        view.visibility = View.GONE
        return
    } else {
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
    } else {
        view.visibility = View.VISIBLE
        view.text = "examples"
    }
}

@BindingAdapter("wordDetailExamplesLayout")
fun setWordDetailExample(view: View, list: List<Ex>?) {
    if (list == null) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("wordDetailExamplesTransl")
fun setWordDetailExampleTransl(view: TextView, list: List<Tr>?) {
    if (list == null) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        view.text = list[0].transl
    }
}

@BindingAdapter("wordDetailExamplesText")
fun setWordDetailExampleText(view: TextView, list: List<Ex>?) {
    if (list == null) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        view.text = list[0].example
    }
}

@BindingAdapter("layoutError")
fun setLayoutError(layout: LinearLayout, status: LoadingStatus?) {
    if (status == null) {
        return
    }

    when (status) {
        LoadingStatus.RUNNING -> layout.visibility = View.GONE
        LoadingStatus.SUCCESS -> layout.visibility = View.GONE
        LoadingStatus.FAILED -> layout.visibility = View.VISIBLE
        LoadingStatus.NOT_FOUND -> layout.visibility = View.VISIBLE
    }
}

@BindingAdapter("imageError")
fun setImageError(view: ImageView, status: LoadingStatus?) {
    if (status == null) {
        return
    }

    when (status) {
        LoadingStatus.RUNNING -> return
        LoadingStatus.SUCCESS -> return
        LoadingStatus.FAILED -> view.setImageResource(R.drawable.ic_no_internet)
        LoadingStatus.NOT_FOUND -> view.setImageResource(R.drawable.ic_nothing_found)
    }
}

@BindingAdapter("textError")
fun setTextError(view: TextView, status: LoadingStatus?) {
    if (status == null) {
        return
    }

    when (status) {
        LoadingStatus.RUNNING -> return
        LoadingStatus.SUCCESS -> return
        LoadingStatus.FAILED -> view.text = view.resources.getString(R.string.tv_no_internet)
        LoadingStatus.NOT_FOUND -> view.text = view.resources.getString(R.string.tv_nothing_found)
    }
}
