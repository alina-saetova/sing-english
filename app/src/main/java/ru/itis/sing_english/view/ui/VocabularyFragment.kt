package ru.itis.sing_english.view.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
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
    Injectable
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
        binding.btnQuiz.setOnClickListener(onQuizButtonListener)

        val adapter = WordAdapter(emptyList<Word>().toMutableList(), wordClickListener, deleteClickListener)
        binding.rvWords.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(VocabularyViewModel::class.java)
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

    private val queryListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(newText: String?): Boolean {
            newText?.let { wordClickListener(it) }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    private val deleteClickListener = { id: Long ->
        viewModel.deleteWord(id)
    }

    private val wordClickListener = { query: String ->
        val bundle = Bundle()
        bundle.putString(WordDetailFragment.WORD_PARAM, query)
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

    companion object {
        const val MIN_NUM_QUIZ = 4
    }
}
