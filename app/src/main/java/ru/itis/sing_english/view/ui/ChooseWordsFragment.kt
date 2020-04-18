package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_choose_level.view.*
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.model.WordGrid

import ru.itis.sing_english.databinding.FragmentChooseWordsBinding
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.view.recyclerview.words.WordAdapter
import ru.itis.sing_english.view.recyclerview.wordsgrid.WordGridAdapter
import ru.itis.sing_english.view.ui.Song5RowsFragment.Companion.ANSWERS_PARAM
import ru.itis.sing_english.viewmodel.ChooseWordsViewModel
import javax.inject.Inject

class ChooseWordsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ChooseWordsViewModel
    lateinit var binding: FragmentChooseWordsBinding
    private var words = mutableListOf<WordGrid>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var answers = mutableListOf<String>()
        arguments?.let {
            answers = it.getStringArrayList(ANSWERS_PARAM) as MutableList<String>
        }
        for (a in answers) {
            words.add(WordGrid(a))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseWordsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val manager = GridLayoutManager(activity, 3)
        val adapter = WordGridAdapter(emptyList<WordGrid>().toMutableList(), wordClickListener)
        binding.rvWords.adapter = adapter
        binding.rvWords.layoutManager = manager

        binding.btnContinue.setOnClickListener{
            findNavController().navigate(R.id.action_chooseWords_to_main)
        }

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ChooseWordsViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.loadWords(words)
        return binding.root
    }

    private val wordClickListener = { word: String, position: Int ->
        viewModel.saveWord(word, position)
    }

}
