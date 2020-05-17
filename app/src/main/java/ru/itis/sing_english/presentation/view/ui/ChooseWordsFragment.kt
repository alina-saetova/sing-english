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
import ru.itis.sing_english.presentation.view.ui.FavouritesFragment.Companion.FROM
import ru.itis.sing_english.presentation.view.ui.FavouritesFragment.Companion.FROM_FAV
import ru.itis.sing_english.presentation.view.ui.SongFragment.Companion.ANSWERS_PARAM
import ru.itis.sing_english.presentation.viewmodel.ChooseWordsViewModel
import javax.inject.Inject

class ChooseWordsFragment : Fragment() {

    @Inject
    lateinit var viewModel: ChooseWordsViewModel
    lateinit var binding: FragmentChooseWordsBinding
    private var words = mutableSetOf<WordGrid>()
    private var actionId = 0
    private lateinit var from: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusChooseWordsComponent().inject(this)
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = ""

        var answers = mutableListOf<String>()
        arguments?.let {
            answers = it.getStringArrayList(ANSWERS_PARAM) as MutableList<String>
            from = it.getString(FROM).toString()
        }
        for (a in answers) {
            words.add(WordGrid(a))
        }

        setDirection()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseWordsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val manager = GridLayoutManager(activity, SPAN_COUNT)
        val adapter = WordGridAdapter(mutableListOf(), wordClickListener)
        binding.rvWords.adapter = adapter
        binding.rvWords.layoutManager = manager

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(actionId)
        }

        binding.viewModel = viewModel
        viewModel.loadWords(words.toMutableList())
        return binding.root
    }

    private val wordClickListener = { word: String, position: Int ->
        viewModel.saveWord(word, position)
    }

    private fun setDirection() {
        actionId = if (from == FROM_FAV) {
            R.id.action_back_from_chooseWords_to_favourites
        } else {
            R.id.action_back_from_chooseWords_to_main
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showWarningDialog()
        }
    }

    private fun showWarningDialog() {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.dialogTitle))
            .setMessage(resources.getString(R.string.dialogTextStat))
            .setNegativeButton(resources.getString(R.string.dialogNegative)) { _, _ ->
                findNavController().navigate(actionId)
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

    companion object {
        const val SPAN_COUNT = 3
    }
}
