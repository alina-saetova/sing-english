package ru.itis.sing_english.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.word_detail_fragment.*
import kotlinx.coroutines.*

import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.repository.WordRepository
import ru.itis.sing_english.databinding.WordDetailFragmentBinding
import ru.itis.sing_english.di.App
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.viewmodel.BaseViewModelFactory
import ru.itis.sing_english.viewmodel.WordViewModel
import javax.inject.Inject

class WordDetailFragment : Fragment(), CoroutineScope by MainScope(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var wordText: String
    private lateinit var binding: WordDetailFragmentBinding
    private lateinit var viewModel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WordDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(WordViewModel::class.java)
        arguments?.let {
            viewModel.loadWord(it.getString(WORD_PARAM).toString())
        }
        binding.wordViewModel = viewModel
        return binding.root
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
