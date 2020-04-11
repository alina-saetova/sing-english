package ru.itis.sing_english.view.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import ru.itis.sing_english.MainActivity

import ru.itis.sing_english.databinding.WordDetailFragmentBinding
import ru.itis.sing_english.di.Injectable
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }

    companion object {

        const val WORD_PARAM = "word"
    }
}
