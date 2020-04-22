package ru.itis.sing_english.presentation.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.databinding.FragmentFavouritesBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.recyclerview.videos.VideoAdapter
import ru.itis.sing_english.presentation.view.ui.Song5RowsFragment.Companion.ID_PARAM
import ru.itis.sing_english.presentation.viewmodel.FavouritesViewModel
import ru.itis.sing_english.utils.ListPaddingDecoration
import javax.inject.Inject


class FavouritesFragment : Fragment() {

    @Inject
    lateinit var viewModel: FavouritesViewModel
    lateinit var binding: FragmentFavouritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusFavouritesComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = VideoAdapter(emptyList<Video>().toMutableList(), videoClickListener, likeClickListener)
        binding.rvVideos.addItemDecoration(
            ListPaddingDecoration(context)
        )
        binding.rvVideos.adapter = adapter
        binding.favViewModel = viewModel

        return binding.root
    }

    private val videoClickListener =  { id: String ->
        val bundle = Bundle()
        bundle.putString(ID_PARAM, id)
        findNavController().navigate(R.id.action_favourites_to_graph_song, bundle)
    }

    private val likeClickListener = { video: Video, like: String, position: Int ->
        if (like == "like") {
            viewModel.unlike(video, position)
        }
        else {
            viewModel.like(video, position)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavourites()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearFavouritesComponent()
    }
}
