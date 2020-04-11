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
import ru.itis.sing_english.viewmodel.MainPageViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainPageFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainPageViewModel
    lateinit var binding: FragmentMainpageBinding

    private lateinit var searchView: SearchView

    private lateinit var list: List<Video>

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

        val adapter = VideoAdapter(emptyList<Video>().toMutableList(), videoClickListener, likeClickListener)
        binding.rvVideos.addItemDecoration(
                ListPaddingDecoration(context))
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
            return false
        }
    }

    private val videoClickListener =  { id: String ->
        val bundle = Bundle()
        bundle.putString(SongFragment.ID_PARAM, id)
        findNavController().navigate(R.id.action_mainPage_to_song, bundle)
    }

    private val likeClickListener = { video: Video, like: String ->
        if (like == "like") {
            viewModel.unlike(video)
        }
        else {
            viewModel.like(video)
        }
    }
}
