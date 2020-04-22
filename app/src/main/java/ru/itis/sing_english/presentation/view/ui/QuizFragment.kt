package ru.itis.sing_english.presentation.view.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.FragmentQuizBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.viewmodel.QuizViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class QuizFragment : Fragment() {

    @Inject
    lateinit var viewModel: QuizViewModel
    lateinit var binding: FragmentQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusQuizComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater)
        binding.quizViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.ibQuit.setOnClickListener {
            findNavController().navigate(R.id.action_quiz_to_vocabulary)
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearQuizComponent()
    }
}
