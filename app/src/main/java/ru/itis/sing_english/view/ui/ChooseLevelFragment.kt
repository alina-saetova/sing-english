package ru.itis.sing_english.view.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.itis.sing_english.MainActivity

import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.FragmentChooseLevelBinding
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.view.ui.Song5RowsFragment.Companion.ID_PARAM

class ChooseLevelFragment : Fragment(), Injectable {

    lateinit var binding: FragmentChooseLevelBinding
    lateinit var videoId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseLevelBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.let {
            videoId = it.getString(ID_PARAM).toString()
        }

        setClickListeners()
        return binding.root
    }

    private fun setClickListeners() {
        binding.btnEasy.setOnClickListener(onClickListener)
        binding.btnNormal.setOnClickListener(onClickListener)
        binding.btnHard.setOnClickListener(onClickListener)
        binding.btnChallenging.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        var flag = false
        var action = 0
        when ((view as Button).text.toString()) {
            resources.getString(R.string.btnTitleEasy) -> {
                flag = false
                action = R.id.action_chooseLevel_to_song5Rows
            }
            resources.getString(R.string.btnTitleNormal) -> {
                flag = false
                action = R.id.action_chooseLevel_to_song3Rows
            }
            resources.getString(R.string.btnTitleHard) -> {
                flag = true
                action = R.id.action_chooseLevel_to_song5Rows
            }
            resources.getString(R.string.btnTitleChallenging) -> {
                flag = true
                action = R.id.action_chooseLevel_to_song3Rows
            }
        }
        val bundle = Bundle()
        bundle.putString(ID_PARAM, videoId)
        bundle.putBoolean(FLAG_PARAM, flag)
        findNavController().navigate(action, bundle)
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
        const val FLAG_PARAM = "flag"
    }
}
