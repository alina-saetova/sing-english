package ru.itis.sing_english.presentation.view.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.DifficultyLevel
import ru.itis.sing_english.databinding.FragmentChooseLevelBinding
import ru.itis.sing_english.presentation.view.ui.FavouritesFragment.Companion.FROM
import ru.itis.sing_english.presentation.view.ui.SongFragment.Companion.ID_PARAM

class ChooseLevelFragment : Fragment() {

    lateinit var binding: FragmentChooseLevelBinding
    lateinit var videoId: String
    lateinit var from: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseLevelBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.let {
            videoId = it.getString(ID_PARAM).toString()
            from = it.getString(FROM).toString()
        }

        setClickListeners()
        return binding.root
    }

    private fun setClickListeners() {
        binding.btnEasy.setOnClickListener(onClickListener)
        binding.btnNormal.setOnClickListener(onClickListener)
        binding.btnHard.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        var diffLevel = DifficultyLevel.EASY
        when ((view as Button).text.toString()) {
            resources.getString(R.string.btnTitleEasy) -> diffLevel = DifficultyLevel.EASY

            resources.getString(R.string.btnTitleMedium) -> diffLevel = DifficultyLevel.MEDIUM

            resources.getString(R.string.btnTitleHard) -> diffLevel = DifficultyLevel.HARD
        }
        val bundle = Bundle()
        bundle.putString(ID_PARAM, videoId)
        bundle.putString(FROM, from)
        bundle.putSerializable(DIFF_LEVEL_PARAM, diffLevel)
        findNavController().navigate(R.id.action_chooseLevel_to_song5Rows, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }

    companion object {
        const val DIFF_LEVEL_PARAM = "diff"
    }
}
