package ru.itis.sing_english.view.ui.container

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.FragmentFavouritesContainerBinding

class FavouritesContainerFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

}
