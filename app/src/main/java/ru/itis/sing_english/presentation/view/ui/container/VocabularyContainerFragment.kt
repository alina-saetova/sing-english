package ru.itis.sing_english.presentation.view.ui.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.itis.sing_english.databinding.FragmentVocabularyContainerBinding

class VocabularyContainerFragment : Fragment() {

    private lateinit var binding : FragmentVocabularyContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVocabularyContainerBinding.inflate(inflater, container, false)
        return binding.root
    }
}
