package ru.itis.sing_english.presentation.view.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.databinding.FragmentMainpageBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.recyclerview.videos.VideoBigAdapter
import ru.itis.sing_english.presentation.view.recyclerview.videos.VideoSmallAdapter
import ru.itis.sing_english.presentation.view.ui.SongFragment.Companion.ID_PARAM
import ru.itis.sing_english.presentation.viewmodel.MainPageViewModel
import javax.inject.Inject

class MainPageFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainPageViewModel
    lateinit var binding: FragmentMainpageBinding

    private lateinit var searchView: SearchView

    private lateinit var list: List<Video>

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusMainPageComponent().inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainpageBinding.inflate(inflater)
        binding.rvVideos.visibility = View.GONE
        (activity as MainActivity).supportActionBar?.title = MAIN_TITLE

        setAdapters()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainPageViewModel = viewModel
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search)?.actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            setOnQueryTextListener(queryListener)
        }
        val menuItem = menu.findItem(R.id.search)
        menuItem.setOnActionExpandListener(onActionExpandListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val queryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(text: String?): Boolean {
            text?.let { viewModel.search(it) }
            return true
        }

        override fun onQueryTextChange(text: String?): Boolean {
            return false
        }
    }

    private val onActionExpandListener = object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            binding.recyclerViews.visibility = View.GONE
            binding.rvVideos.visibility = View.VISIBLE
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            viewModel.cleanVideos()
            binding.recyclerViews.visibility = View.VISIBLE
            binding.rvVideos.visibility = View.GONE
            return true
        }

    }

    private val videoClickListener = { id: String ->
        val bundle = Bundle()
        bundle.putString(ID_PARAM, id)
        bundle.putString(FROM, FROM_MAIN)
        findNavController().navigate(R.id.action_mainPage_to_song_graph, bundle)
    }

    private val likeClickListener = { video: Video, like: String, position: Int, type: String ->
        if (like == "like") {
            viewModel.unlike(video, position, type)
        } else {
            viewModel.like(video, position, type)
        }
    }

    private fun setAdapters() {
        val adapterAll =
            VideoSmallAdapter(
                emptyList<Video>().toMutableList(),
                TYPE_VIDEO_ALL,
                videoClickListener,
                likeClickListener
            )
        binding.rvAllVideos.adapter = adapterAll

        val adapterPop =
            VideoSmallAdapter(
                emptyList<Video>().toMutableList(),
                TYPE_VIDEO_POP,
                videoClickListener,
                likeClickListener
            )
        binding.rvPopVideos.adapter = adapterPop

        val adapterHipHop =
            VideoSmallAdapter(
                emptyList<Video>().toMutableList(),
                TYPE_VIDEO_HIPHOP,
                videoClickListener,
                likeClickListener
            )
        binding.rvHipHopideos.adapter = adapterHipHop

        val adapterRock =
            VideoSmallAdapter(
                emptyList<Video>().toMutableList(),
                TYPE_VIDEO_ROCK,
                videoClickListener,
                likeClickListener
            )
        binding.rvRockVideos.adapter = adapterRock

        val adapterSearch =
            VideoBigAdapter(
                emptyList<Video>().toMutableList(),
                TYPE_VIDEO_SEARCH,
                videoClickListener,
                likeClickListener
            )
        binding.rvVideos.adapter = adapterSearch
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearMainPageComponent()
    }

    companion object {
        const val MAIN_TITLE = "SingEnglish!"
        const val FROM_MAIN = "main"
        const val FROM = "from"
        const val TYPE_VIDEO_ALL = "all"
        const val TYPE_VIDEO_POP = "pop"
        const val TYPE_VIDEO_HIPHOP = "hiphop"
        const val TYPE_VIDEO_ROCK = "rock"
        const val TYPE_VIDEO_SEARCH = "search"
    }
}
