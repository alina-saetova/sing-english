package ru.itis.sing_english.view.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.MainActivity

import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.FragmentQuizBinding
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.viewmodel.QuizViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class QuizFragment : Fragment(), CoroutineScope by MainScope(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: QuizViewModel
    lateinit var binding: FragmentQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(QuizViewModel::class.java)
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
}
