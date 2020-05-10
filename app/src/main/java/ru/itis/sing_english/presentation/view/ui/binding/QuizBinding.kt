package ru.itis.sing_english.presentation.view.ui.binding

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.QuestionState
import ru.itis.sing_english.data.model.QuizOption
import ru.itis.sing_english.data.model.State

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

@BindingAdapter(value = ["bind:stateManual"])
fun setCorrectnessManual(view: ImageView, state: State?) {
    when (state) {
        State.DEFAULT -> view.setImageResource(R.drawable.ic_unknown)
        State.WRONG -> view.setImageResource(R.drawable.ic_wrong)
        State.RIGHT -> view.setImageResource(R.drawable.ic_correct)
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
