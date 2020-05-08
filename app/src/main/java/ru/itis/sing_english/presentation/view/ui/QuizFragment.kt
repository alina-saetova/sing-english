package ru.itis.sing_english.presentation.view.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.FragmentQuizBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.viewmodel.QuizViewModel
import javax.inject.Inject


class QuizFragment : Fragment() {

    @Inject
    lateinit var viewModel: QuizViewModel
    lateinit var binding: FragmentQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusQuizComponent().inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater)
        (activity as MainActivity).supportActionBar?.title = QUIZ_TITLE

        binding.quizViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.quiz_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_close -> {
            findNavController().navigate(R.id.action_quiz_to_vocabulary)
            true
        }
        else -> super.onOptionsItemSelected(item)
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

    companion object {

        const val QUIZ_TITLE = "Quiz"
    }
}
