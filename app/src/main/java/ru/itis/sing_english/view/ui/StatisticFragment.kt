package ru.itis.sing_english.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import ru.itis.sing_english.data.model.SongRow
import ru.itis.sing_english.databinding.FragmentStatisticBinding
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.view.recyclerview.songsrow.SongRowAdapter
import ru.itis.sing_english.view.ui.Song5RowsFragment.Companion.ANSWERS_PARAM
import ru.itis.sing_english.view.ui.Song5RowsFragment.Companion.ID_PARAM
import ru.itis.sing_english.view.ui.Song5RowsFragment.Companion.LYRIC_PARAM
import ru.itis.sing_english.viewmodel.StatisticViewModel
import javax.inject.Inject

class StatisticFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: StatisticViewModel
    lateinit var binding: FragmentStatisticBinding
    private lateinit var lyric: List<SongRow>
    private lateinit var answers: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStatisticBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this, viewModelFactory).get(StatisticViewModel::class.java)
        val adapter = SongRowAdapter(emptyList<SongRow>().toMutableList())
        binding.rvLyric.adapter = adapter
        arguments?.let {
            lyric = it.getParcelableArrayList<SongRow>(LYRIC_PARAM) as List<SongRow>
            answers = it.getStringArrayList(ANSWERS_PARAM) as List<String>
        }
        viewModel.loadStatistic(lyric, answers)
        binding.viewModel = viewModel

        return binding.root
    }

}
