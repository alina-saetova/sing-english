package ru.itis.sing_english.presentation.view.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.itis.sing_english.databinding.WordDetailFragmentBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.viewmodel.WordViewModel
import javax.inject.Inject

class WordDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: WordDetailFragmentBinding
    private lateinit var viewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusWordDetailComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

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

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearWordDetailComponent()
    }
    companion object {

        const val WORD_PARAM = "word"
    }
}
