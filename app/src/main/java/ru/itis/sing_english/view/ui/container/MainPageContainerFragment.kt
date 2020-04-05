package ru.itis.sing_english.view.ui.container

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.FragmentMainPageContainerBinding

class MainPageContainerFragment : Fragment() {

    private lateinit var binding : FragmentMainPageContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

}
