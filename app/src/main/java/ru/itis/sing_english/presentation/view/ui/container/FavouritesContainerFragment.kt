package ru.itis.sing_english.presentation.view.ui.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
