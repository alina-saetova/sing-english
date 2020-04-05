package ru.itis.sing_english.view.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_vocabulary.*
import kotlinx.coroutines.*
import ru.itis.sing_english.MainActivity
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.databinding.FragmentVocabularyBinding
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.view.recyclerview.words.WordClickListener
import ru.itis.sing_english.view.recyclerview.words.WordAdapter
import ru.itis.sing_english.viewmodel.VocabularyViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class VocabularyFragment : Fragment(),
    CoroutineScope by MainScope(),
    WordClickListener, Injectable
{
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VocabularyViewModel
    lateinit var binding: FragmentVocabularyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVocabularyBinding.inflate(inflater)
        binding.btnQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_vocabulary_to_quiz)
        }

        val adapter = WordAdapter(emptyList<Word>().toMutableList(), this)
        binding.rvWords.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(VocabularyViewModel::class.java)
        binding.vocabViewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWords()
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

    private val queryListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(newText: String?): Boolean {
            newText?.let { goToWord(it) }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    private fun goToWord(query: String) {
        val bundle = Bundle()
        bundle.putString(WordDetailFragment.WORD_PARAM, query)
        findNavController().navigate(R.id.action_vocabulary_to_wordDetail, bundle)
    }

    override fun onWordDeleteListener(id: Long) {
        viewModel.deleteWord(id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).showBottomNavigation()
    }

    companion object {

        fun newInstance(param1: String = "") =
            VocabularyFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM, param1)
                }
            }

        private const val PARAM = "param"
    }
}
