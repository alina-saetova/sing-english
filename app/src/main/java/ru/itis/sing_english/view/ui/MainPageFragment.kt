package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.*
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.repository.VideoRepository
import ru.itis.sing_english.databinding.FragmentMainpageBinding
import ru.itis.sing_english.di.App
import ru.itis.sing_english.utils.ListPaddingDecoration
import ru.itis.sing_english.view.recyclerview.videos.VideoAdapter
import ru.itis.sing_english.view.recyclerview.videos.VideoClickListener
import ru.itis.sing_english.viewmodel.BaseViewModelFactory
import ru.itis.sing_english.viewmodel.MainPageViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainPageFragment : Fragment(), SearchView.OnQueryTextListener, VideoClickListener {

    @Inject
    lateinit var repository: VideoRepository
    lateinit var viewModel: MainPageViewModel
    lateinit var binding: FragmentMainpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectMainPage(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainpageBinding.inflate(inflater)
        repository = App.component.videoRepository()

        binding.searchView.setOnQueryTextListener(this)

        val adapter = VideoAdapter(emptyList<Video>().toMutableList(), this)
        binding.rvVideos.addItemDecoration(
                ListPaddingDecoration(context, 0, 0))
        binding.rvVideos.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this,
            BaseViewModelFactory { MainPageViewModel(repository) } ).get(MainPageViewModel::class.java)
        binding.mainPageViewModel = viewModel

        return binding.root
    }

    private fun likeVideo(id: String) {
        println("dd")
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        text?.let { viewModel.search(it) }
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        text?.let { viewModel.search(it) }
        return true
    }

    override fun onVideoClickListener(view: View, id: String) {
        activity?.supportFragmentManager?.also {
            it.beginTransaction().apply {
                replace(R.id.container, SongFragment.newInstance(id))
                addToBackStack(SongFragment::class.java.name)
                commit()
            }
        }
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
