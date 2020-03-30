package ru.itis.sing_english.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.ObsoleteCoroutinesApi

import ru.itis.sing_english.R
import ru.itis.sing_english.data.repository.WordRepository
import ru.itis.sing_english.databinding.FragmentQuizBinding
import ru.itis.sing_english.di.App
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.viewmodel.BaseViewModelFactory
import ru.itis.sing_english.viewmodel.QuizViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class QuizFragment : Fragment(), CoroutineScope by MainScope(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: QuizViewModel
    lateinit var binding: FragmentQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
            activity?.supportFragmentManager?.popBackStackImmediate();
        }
        return binding.root
    }

    companion object {

        fun newInstance(param1: String = "") =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM, param1)
                }
            }

        private const val PARAM = "param"
    }
}
