package ru.itis.sing_english.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.word_detail_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.repository.WordRepository
import ru.itis.sing_english.di.App
import javax.inject.Inject

class WordDetailFragment : Fragment(), CoroutineScope by MainScope() {

    @Inject
    lateinit var repository: WordRepository
    lateinit var wordText: String
    lateinit var resp: DictionaryResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = App.component.wordRepository()
        arguments?.let {
            wordText = it.getString(WORD_PARAM).toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.word_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            resp = repository.getWord(wordText)
            tv.text = resp.toString()
        }

    }

    companion object {

        fun newInstance(param1: String) =
            WordDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(WORD_PARAM, param1)
                }
            }

        private const val WORD_PARAM = "word"
    }
}
