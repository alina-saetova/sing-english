package ru.itis.sing_english.presentation.view.recyclerview.words

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.itis.sing_english.data.model.Word

class WordsItemDiffCallback(
    private var oldList: MutableList<Word>,
    private var newList: MutableList<Word>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        val diffBundle = Bundle()
        if (newItem.text != oldItem.text) {
            diffBundle.putString("text", newItem.text)
        }
        if (newItem.translation != oldItem.translation) {
            diffBundle.putString("translation", newItem.translation)
        }

        return if (diffBundle.size() == 0) null else diffBundle
    }
}
