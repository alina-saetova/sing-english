package ru.itis.sing_english.presentation.view.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.word_detail_fragment.*
import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.WordDetailFragmentBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.viewmodel.WordViewModel
import javax.inject.Inject


class WordDetailFragment : Fragment() {

    @Inject
    lateinit var viewModel: WordViewModel
    lateinit var binding: WordDetailFragmentBinding
    lateinit var word: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusWordDetailComponent().inject(this)
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.apply {
            title = WORD_DETAIL_TITLE
            setDisplayHomeAsUpEnabled(true)
        }
        arguments?.let {
            word = it.getString(WORD_PARAM).toString()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WordDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        setShapesInView()
        viewModel.loadWord(word)
        binding.wordViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        wordLayout.visibility = View.INVISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.action_wordDetail_to_vocabulary)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }

    override fun onDestroyView() {
        setHasOptionsMenu(false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearWordDetailComponent()
    }

    private fun setShapesInView() {
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, 30f)
            .build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        shapeDrawable.apply {
            strokeWidth = 4f
            strokeColor = context?.let { ContextCompat.getColor(it, R.color.colorAccent) }?.let {
                ColorStateList.valueOf(
                    it
                )
            }
        }
        shapeDrawable.also {
            binding.translationBlock.background = it
            binding.synonimsBlock.background = it
            binding.examplesBlock.background = it
        }
    }
    companion object {
        const val WORD_DETAIL_TITLE = "Подробнее"
        const val WORD_PARAM = "word"
    }
}
