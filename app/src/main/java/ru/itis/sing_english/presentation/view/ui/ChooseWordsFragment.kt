package ru.itis.sing_english.presentation.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.WordGrid
import ru.itis.sing_english.databinding.FragmentChooseWordsBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.recyclerview.wordsgrid.WordGridAdapter
import ru.itis.sing_english.presentation.view.ui.Song5RowsFragment.Companion.ANSWERS_PARAM
import ru.itis.sing_english.presentation.viewmodel.ChooseWordsViewModel
import javax.inject.Inject

class ChooseWordsFragment : Fragment() {

    @Inject
    lateinit var viewModel: ChooseWordsViewModel
    lateinit var binding: FragmentChooseWordsBinding
    private var words = mutableListOf<WordGrid>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusChooseWordsComponent().inject(this)
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showWarningDialog()
        }
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

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_chooseWords_to_main)
        }

        binding.viewModel = viewModel
        viewModel.loadWords(words)
        return binding.root
    }

    private val wordClickListener = { word: String, position: Int ->
        viewModel.saveWord(word, position)
    }

    private fun showWarningDialog() {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.dialogTitle))
            .setMessage(resources.getString(R.string.dialogTextStat))
            .setNegativeButton(resources.getString(R.string.dialogNegative)) { _, _ ->
                findNavController().navigate(R.id.action_chooseWords_to_main)
            }
            .setPositiveButton(resources.getString(R.string.dialogPositive)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearChooseWordsComponent()
    }
}
