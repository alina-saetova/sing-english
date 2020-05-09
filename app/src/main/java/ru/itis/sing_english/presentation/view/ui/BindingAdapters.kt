package ru.itis.sing_english.presentation.view.ui

import android.content.res.ColorStateList
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
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
    } else {
        view.setImageResource(R.drawable.ic_favourites_empty)
    }
}

@BindingAdapter("btnColor")
fun changeColor(view: MaterialButton, state: State?) {
    val color = ColorStateList.valueOf(view.context.getColor(R.color.colorAccent))
    if (state == null) {
        view.strokeColor = color
        view.setTextColor(color)
        return
    }
    when (state) {
        State.DEFAULT -> {
            view.strokeColor = color
            view.setTextColor(color)
        }
        State.RIGHT -> {
            ColorStateList.valueOf(view.context.getColor(R.color.colorRight)).also {
                view.strokeColor = it
                view.setTextColor(it)
            }
        }
        State.WRONG -> {
            ColorStateList.valueOf(view.context.getColor(R.color.colorWrong)).also {
                view.strokeColor = it
                view.setTextColor(it)
            }
        }
    }
}

@BindingAdapter(value = ["setupVisibility"])
fun ProgressBar.progressVisibility(loadingStatus: LoadingStatus?) {
    loadingStatus?.let {
        isVisible = when (it) {
            LoadingStatus.RUNNING -> true
            LoadingStatus.SUCCESS -> false
            LoadingStatus.FAILED -> false
        }
    }
}

@BindingAdapter(value = ["bind:correctness", "bind:missed", "bind:indices", "bind:row"])
fun setColorByCorrectness(
    view: TextView,
    isCorrect:  MutableList<Boolean>,
    wasMissed: Boolean,
    indicesOfAnswers: MutableList<Int>,
    rowText: String
) {
    when(indicesOfAnswers.size) {
        2 -> setSpannableTwoWords(view, rowText, indicesOfAnswers, isCorrect)
        1 -> setSpannableOneWord(view, rowText, indicesOfAnswers[0], isCorrect[0])
        0 -> view.text = rowText
    }
}

private fun setSpannableOneWord(view: TextView, rowText: String, index: Int, isCorrect: Boolean) {
    val rowsWords = rowText.split(" ")
    val startIndex = rowsWords.subList(0, index).joinToString(" ").length
    val endIndex = rowText.length - rowsWords.subList(index + 1, rowsWords.size).joinToString(" ").length

    val spannable = SpannableString(rowText)

    val colorWord= if (isCorrect) {
        view.context.getColor(R.color.colorRight)
    } else {
        view.context.getColor(R.color.colorWrong)
    }

    spannable.setSpan(
        ForegroundColorSpan(colorWord),
        startIndex, endIndex,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        StyleSpan(BOLD),
        startIndex, endIndex,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    view.text = spannable
}

private fun setSpannableTwoWords(view: TextView, rowText: String, indicesOfAnswers: MutableList<Int>, isCorrect:  MutableList<Boolean>) {
    val rowsWords = rowText.split(" ")
    val startIndexOne = rowsWords.subList(0, indicesOfAnswers[0]).joinToString(" ").length
    val endIndexOne = rowText.length - rowsWords.subList(indicesOfAnswers[0] + 1, rowsWords.size).joinToString(" ").length

    val startIndexTwo = rowsWords.subList(0, indicesOfAnswers[1]).joinToString(" ").length
    val endIndexTwo = rowText.length - rowsWords.subList(indicesOfAnswers[1] + 1, rowsWords.size).joinToString(" ").length

    val spannable = SpannableString(rowText)

    val colorWordOne = if (isCorrect[0]) {
        view.context.getColor(R.color.colorRight)
    } else {
        view.context.getColor(R.color.colorWrong)
    }

    val colorWordTwo = if (isCorrect[1]) {
        view.context.getColor(R.color.colorRight)
    } else {
        view.context.getColor(R.color.colorWrong)
    }

    spannable.setSpan(
        ForegroundColorSpan(colorWordOne),
        startIndexOne, endIndexOne,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        StyleSpan(BOLD),
        startIndexOne, endIndexOne,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannable.setSpan(
        ForegroundColorSpan(colorWordTwo),
        startIndexTwo, endIndexTwo,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        StyleSpan(BOLD),
        startIndexTwo, endIndexTwo,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    view.text = spannable
}

@BindingAdapter(value = ["bind:numCorrect", "bind:numAll"])
fun setColorByCorrectness(view: TextView, numOfCorrect: Int, totalNum: Int) {
    val spannable = SpannableString("Correct answers: $numOfCorrect out of $totalNum")
    val endIndex = numOfCorrect.toString().length + 17
    spannable.setSpan(
        ForegroundColorSpan(view.context.getColor(R.color.colorRight)),
        16, endIndex,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    spannable.setSpan(
        StyleSpan(BOLD),
        16, endIndex,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    view.text = spannable
}

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

@BindingAdapter("loading")
fun onLoad(view: View, loadingStatus: LoadingStatus?) {
    loadingStatus?.let {
        when (it) {
            LoadingStatus.RUNNING -> view.visibility = View.INVISIBLE
            LoadingStatus.SUCCESS -> view.visibility = View.VISIBLE
            LoadingStatus.FAILED -> view.visibility = View.GONE
        }
    }
}

@BindingAdapter("question")
fun setQuestion(view: TextView, option: QuizOption?) {
    if (option == null) {
        return
    }
    if (option.isEnglish) {
        view.text = option.word.text
    } else {
        view.text = option.word.translation
    }
}

@BindingAdapter(value = ["bind:option", "bind:quest"])
fun setAnswer(view: TextView, option: QuizOption?, isEnglish: Boolean) {
    if (option == null) {
        return
    }
    if (isEnglish) {
        view.text = option.word.translation
    } else {
        view.text = option.word.text
    }
}

@BindingAdapter(value = ["bind:multiple"])
fun setVisibleMultiple(view: LinearLayout, state: QuestionState?) {
    if (state == null) {
        return
    }
    when (state) {
        QuestionState.NEXT_QUESTION -> view.visibility = View.GONE
        QuestionState.MANUAL -> view.visibility = View.GONE
        QuestionState.MULTIPLE -> view.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["bind:manual"])
fun setVisibleManual(view: LinearLayout, state: QuestionState?) {
    if (state == null) {
        return
    }
    when (state) {
        QuestionState.NEXT_QUESTION -> view.visibility = View.GONE
        QuestionState.MANUAL -> view.visibility = View.VISIBLE
        QuestionState.MULTIPLE -> view.visibility = View.GONE
    }
}

@BindingAdapter(value = ["bind:choice"])
fun setVisibleChoice(view: LinearLayout, state: QuestionState?) {
    if (state == null) {
        return
    }
    when (state) {
        QuestionState.NEXT_QUESTION -> view.visibility = View.VISIBLE
        QuestionState.MANUAL -> view.visibility = View.GONE
        QuestionState.MULTIPLE -> view.visibility = View.GONE
    }
}

@BindingAdapter(value = ["bind:stateManual"])
fun setCorrectnessManual(view: ImageView, state: State?) {
    when (state) {
        State.DEFAULT -> view.setImageResource(R.drawable.ic_unknown)
        State.WRONG -> view.setImageResource(R.drawable.ic_wrong)
        State.RIGHT -> view.setImageResource(R.drawable.ic_correct)
    }
}
