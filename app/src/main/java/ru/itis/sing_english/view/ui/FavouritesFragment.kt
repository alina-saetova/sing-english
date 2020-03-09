package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.itis.sing_english.R


class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        private const val ARG_SUM = "sum"

        fun newInstance(sum: Int = 0): FavouritesFragment = FavouritesFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SUM, sum)
            }
        }
    }
}
