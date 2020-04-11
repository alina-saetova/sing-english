package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.databinding.FragmentFavouritesBinding
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.utils.ListPaddingDecoration
import ru.itis.sing_english.view.recyclerview.videos.VideoAdapter
import ru.itis.sing_english.viewmodel.FavouritesViewModel
import javax.inject.Inject


class FavouritesFragment : Fragment(), CoroutineScope by MainScope(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavouritesViewModel
    lateinit var binding: FragmentFavouritesBinding

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

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FavouritesViewModel::class.java)
        binding.favViewModel = viewModel

        return binding.root
    }

    private val videoClickListener =  { id: String ->
        val bundle = Bundle()
        bundle.putString(SongFragment.ID_PARAM, id)
        findNavController().navigate(R.id.action_favourites_to_song, bundle)
    }

    private val likeClickListener = { video: Video, like: String ->
        if (like == "like") {
            viewModel.unlike(video)
        }
        else {
            viewModel.like(video)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavourites()
    }
}
