package ru.itis.sing_english.presentation.view.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.databinding.FragmentVocabularyBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.recyclerview.words.WordAdapter
import ru.itis.sing_english.presentation.view.ui.WordDetailFragment.Companion.WORD_PARAM
import ru.itis.sing_english.presentation.viewmodel.VocabularyViewModel
import javax.inject.Inject

class VocabularyFragment : Fragment() {

    @Inject
    lateinit var viewModel: VocabularyViewModel
    lateinit var binding: FragmentVocabularyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusVocabularyComponent().inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVocabularyBinding.inflate(inflater)
        (activity as MainActivity).supportActionBar?.title = VOCAB_TITLE

        binding.btnQuiz.setOnClickListener(onQuizButtonListener)

        val adapter =
            WordAdapter(mutableListOf(), wordClickListener, deleteClickListener)
        binding.rvWords.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vocabViewModel = viewModel

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        }
        searchView.setOnQueryTextListener(queryListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val queryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(newText: String?): Boolean {
            hideKeyboard()
            newText?.let { wordClickListener(it, false) }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private val deleteClickListener = { word: Word ->
        viewModel.deleteWord(word)
    }

    private val wordClickListener = { query: String, isSaved: Boolean ->
        val bundle = Bundle()
        bundle.putString(WORD_PARAM, query)
        bundle.putBoolean(IS_SAVED, isSaved)
        findNavController().navigate(R.id.action_vocabulary_to_wordDetail, bundle)
    }

    private val onQuizButtonListener = View.OnClickListener {
        if (viewModel.words.value?.size ?: 0 < MIN_NUM_QUIZ) {
            Toast.makeText(activity, R.string.quiz_warning, Toast.LENGTH_LONG).show()
        } else {
            findNavController().navigate(R.id.action_vocabulary_to_quiz)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWords()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).showBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearVocabularyComponent()
    }

    companion object {
        const val MIN_NUM_QUIZ = 4
        const val VOCAB_TITLE = "Vocabulary"
        const val IS_SAVED = "savedWord"
    }
}
