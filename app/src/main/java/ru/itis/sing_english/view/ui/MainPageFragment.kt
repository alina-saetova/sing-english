package ru.itis.sing_english.view.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.databinding.FragmentMainpageBinding
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.utils.ListPaddingDecoration
import ru.itis.sing_english.view.recyclerview.videos.VideoAdapter
import ru.itis.sing_english.view.recyclerview.videos.VideoClickListener
import ru.itis.sing_english.viewmodel.MainPageViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainPageFragment : Fragment(), VideoClickListener, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainPageViewModel
    lateinit var binding: FragmentMainpageBinding

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainpageBinding.inflate(inflater)

        val adapter = VideoAdapter(emptyList<Video>().toMutableList(), this)
        binding.rvVideos.addItemDecoration(
                ListPaddingDecoration(context, 0, 0))
        binding.rvVideos.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainPageViewModel::class.java)
        binding.mainPageViewModel = viewModel

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search)?.actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        }
        searchView.setOnQueryTextListener(queryListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val queryListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(text: String?): Boolean {
            text?.let { viewModel.search(it) }
            return true
        }

        override fun onQueryTextChange(text: String?): Boolean {
//            text?.let { viewModel.search(it) }
//            return true
            return false
        }
    }

    override fun onVideoClickListener(view: View, id: String) {
        val bundle = Bundle()
        bundle.putString(SongFragment.ID_PARAM, id)
        findNavController().navigate(R.id.action_mainPage_to_song, bundle)
    }

    override fun onLikeClickListener(view: View, video: Video) {
        viewModel.like(video)
    }

    companion object {

        private const val ARG_SUM = "sum"

        fun newInstance(sum: Int = 0): MainPageFragment = MainPageFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SUM, sum)
            }
        }
    }
}
