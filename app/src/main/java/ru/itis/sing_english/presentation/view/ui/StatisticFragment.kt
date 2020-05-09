package ru.itis.sing_english.presentation.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.SongRow
import ru.itis.sing_english.databinding.FragmentStatisticBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.recyclerview.songsrow.SongRowAdapter
import ru.itis.sing_english.presentation.view.ui.FavouritesFragment.Companion.FROM
import ru.itis.sing_english.presentation.view.ui.FavouritesFragment.Companion.FROM_FAV
import ru.itis.sing_english.presentation.view.ui.SongFragment.Companion.ANSWERS_PARAM
import ru.itis.sing_english.presentation.view.ui.SongFragment.Companion.LYRIC_PARAM
import ru.itis.sing_english.presentation.viewmodel.StatisticViewModel
import javax.inject.Inject

class StatisticFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var viewModel: StatisticViewModel
    lateinit var binding: FragmentStatisticBinding
    private lateinit var lyric: MutableList<SongRow>
    private lateinit var answers: List<String>
    private lateinit var from: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusStatisticComponent().inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            lyric = it.getParcelableArrayList<SongRow>(LYRIC_PARAM) as MutableList<SongRow>
            answers = it.getStringArrayList(ANSWERS_PARAM) as List<String>
            from = it.getString(FROM).toString()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showWarningDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = SongRowAdapter(emptyList<SongRow>().toMutableList())
        binding.rvLyric.adapter = adapter
        viewModel.loadStatistic(lyric, answers)

        binding.btnContinue.setOnClickListener(this)
        return binding.root
    }

    private fun showWarningDialog() {
        val actionId = if (from == FROM_FAV) {
            R.id.action_back_fromStatistic_to_favourites
        } else {
            R.id.action_back_fromStatistic_to_main
        }
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

    override fun onClick(p0: View?) {
        val bundle = Bundle()
        bundle.putString(FROM, from)
        bundle.putStringArrayList(ANSWERS_PARAM, ArrayList(answers))
        findNavController().navigate(R.id.action_statistic_to_chooseWords, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearStatisticComponent()
    }
}
