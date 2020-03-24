package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.repository.WordRepository
import ru.itis.sing_english.databinding.FragmentVocabularyBinding
import ru.itis.sing_english.di.App
import ru.itis.sing_english.view.recyclerview.words.WordClickListener
import ru.itis.sing_english.view.recyclerview.words.WordAdapter
import ru.itis.sing_english.viewmodel.BaseViewModelFactory
import ru.itis.sing_english.viewmodel.VocabularyViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class VocabularyFragment : Fragment(),
    CoroutineScope by MainScope(),
    SearchView.OnQueryTextListener,
    WordClickListener
{
    @Inject
    lateinit var repository: WordRepository
    lateinit var viewModel: VocabularyViewModel
    lateinit var binding: FragmentVocabularyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVocabularyBinding.inflate(inflater)
        binding.svWords.setOnQueryTextListener(this)

        val adapter = WordAdapter(emptyList<Word>().toMutableList(), this)
        binding.rvWords.adapter = adapter
        repository = App.component.wordRepository()
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this,
            BaseViewModelFactory { VocabularyViewModel(repository) } )
            .get(VocabularyViewModel::class.java)
        binding.vocabViewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWords()
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        newText?.let { goToWord(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun goToWord(query: String) {
        activity?.supportFragmentManager?.also {
            it.beginTransaction().apply {
                replace(R.id.container, WordDetailFragment.newInstance(query))
                addToBackStack(WordDetailFragment::class.java.name)
                commit()
            }
        }
    }

    override fun onWordDeleteListener(id: Long) {
        viewModel.deleteWord(id)
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
