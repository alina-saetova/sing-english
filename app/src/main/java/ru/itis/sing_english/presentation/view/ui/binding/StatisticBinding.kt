package ru.itis.sing_english.presentation.view.ui.binding

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.itis.sing_english.R

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
        StyleSpan(Typeface.BOLD),
        16, endIndex,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    view.text = spannable
}

@BindingAdapter(value = ["bind:correctness", "bind:missed", "bind:indices", "bind:row"])
fun setColorByCorrectness(
    view: TextView,
    isCorrect: MutableList<Boolean>,
    wasMissed: Boolean,
    indicesOfAnswers: MutableList<Int>,
    rowText: String
) {
    when (indicesOfAnswers.size) {
        2 -> setSpannableTwoWords(view, rowText, indicesOfAnswers, isCorrect)
        1 -> setSpannableOneWord(view, rowText, indicesOfAnswers[0], isCorrect[0])
        0 -> view.text = rowText
    }
}

private fun setSpannableOneWord(view: TextView, rowText: String, index: Int, isCorrect: Boolean) {
    val rowsWords = rowText.split(" ")
    val startIndex = rowsWords.subList(0, index).joinToString(" ").length
    val endIndex =
        rowText.length - rowsWords.subList(index + 1, rowsWords.size).joinToString(" ").length

    val spannable = SpannableString(rowText)

    val colorWord = if (isCorrect) {
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
        StyleSpan(Typeface.BOLD),
        startIndex, endIndex,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    view.text = spannable
}

private fun setSpannableTwoWords(
    view: TextView,
    rowText: String,
    indicesOfAnswers: MutableList<Int>,
    isCorrect: MutableList<Boolean>
) {
    val rowsWords = rowText.split(" ")
    val startIndexOne = rowsWords.subList(0, indicesOfAnswers[0]).joinToString(" ").length
    val endIndexOne = rowText.length - rowsWords.subList(indicesOfAnswers[0] + 1, rowsWords.size)
        .joinToString(" ").length

    val startIndexTwo = rowsWords.subList(0, indicesOfAnswers[1]).joinToString(" ").length
    val endIndexTwo = rowText.length - rowsWords.subList(indicesOfAnswers[1] + 1, rowsWords.size)
        .joinToString(" ").length

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
        StyleSpan(Typeface.BOLD),
        startIndexOne, endIndexOne,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannable.setSpan(
        ForegroundColorSpan(colorWordTwo),
        startIndexTwo, endIndexTwo,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        StyleSpan(Typeface.BOLD),
        startIndexTwo, endIndexTwo,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    view.text = spannable
}
