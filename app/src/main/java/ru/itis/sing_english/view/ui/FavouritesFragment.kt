package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.repository.VideoRepository
import ru.itis.sing_english.databinding.FragmentFavouritesBinding
import ru.itis.sing_english.di.App
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.utils.ListPaddingDecoration
import ru.itis.sing_english.view.recyclerview.videos.VideoAdapter
import ru.itis.sing_english.view.recyclerview.videos.VideoClickListener
import ru.itis.sing_english.viewmodel.BaseViewModelFactory
import ru.itis.sing_english.viewmodel.FavouritesViewModel
import javax.inject.Inject


class FavouritesFragment : Fragment(), CoroutineScope by MainScope(), VideoClickListener, Injectable {

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

        val adapter = VideoAdapter(emptyList<Video>().toMutableList(), this)
        binding.rvVideos.addItemDecoration(
            ListPaddingDecoration(context, 0, 0)
        )
        binding.rvVideos.adapter = adapter

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FavouritesViewModel::class.java)
        binding.favViewModel = viewModel

        return binding.root
    }

    override fun onVideoClickListener(view: View, id: String) {
        activity?.supportFragmentManager?.also {
            it.beginTransaction().apply {
                replace(R.id.main_container, SongFragment.newInstance(id))
                addToBackStack(SongFragment::class.java.name)
                commit()
            }
        }
    }

    override fun onLikeClickListener(view: View, video: Video) {
//        viewModel.like(video)
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
