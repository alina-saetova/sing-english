package ru.itis.sing_english.presentation.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.SongRow
import ru.itis.sing_english.databinding.FragmentStatisticBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.recyclerview.songsrow.SongRowAdapter
import ru.itis.sing_english.presentation.view.ui.Song5RowsFragment.Companion.ANSWERS_PARAM
import ru.itis.sing_english.presentation.view.ui.Song5RowsFragment.Companion.LYRIC_PARAM
import ru.itis.sing_english.presentation.viewmodel.StatisticViewModel
import javax.inject.Inject

class StatisticFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: StatisticViewModel
    lateinit var binding: FragmentStatisticBinding
    private lateinit var lyric: List<SongRow>
    private lateinit var answers: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusStatisticComponent().inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            lyric = it.getParcelableArrayList<SongRow>(LYRIC_PARAM) as List<SongRow>
            answers = it.getStringArrayList(ANSWERS_PARAM) as List<String>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this, viewModelFactory).get(StatisticViewModel::class.java)
        binding.viewModel = viewModel

        val adapter = SongRowAdapter(emptyList<SongRow>().toMutableList())
        binding.rvLyric.adapter = adapter
        viewModel.loadStatistic(lyric, answers)

        binding.btnContinue.setOnClickListener {
            val bundle = Bundle()
            bundle.putStringArrayList(ANSWERS_PARAM, ArrayList(answers))
            findNavController().navigate(R.id.action_statistic_to_chooseWords, bundle)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearStatisticComponent()
    }
}
