package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_song.*
import kotlinx.coroutines.*
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.source.repository.SubtitlesRepository
import ru.itis.sing_english.databinding.FragmentSongBinding
import ru.itis.sing_english.di.App
import ru.itis.sing_english.viewmodel.SongViewModel
import ru.itis.sing_english.viewmodel.BaseViewModelFactory
import javax.inject.Inject

class SongFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var youTubePlayerView: YouTubePlayerView
    lateinit var viewModel: SongViewModel
    @Inject
    lateinit var repository: SubtitlesRepository
    lateinit var binding: FragmentSongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater)
        repository = App.component.subtitlesRepository()
        binding.lifecycleOwner = viewLifecycleOwner
        var videoId = ""
        arguments?.let {
            videoId = it.getString(ID_PARAM).toString()
        }

        viewModel = ViewModelProvider(this,
            BaseViewModelFactory { SongViewModel(videoId, repository) } ).get(SongViewModel::class.java)

        viewModel.subs.observe(viewLifecycleOwner, Observer {newSubs ->
            binding.tvLyric.text = newSubs.toString()
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        youTubePlayerView = youtube_player_view
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(playerListener)
    }

    private val playerListener : AbstractYouTubePlayerListener = object: AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.cueVideo(viewModel.videoId, 0f)
        }
    }

    companion object {

        fun newInstance(param1: String) =
            SongFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_PARAM, param1)
                }
            }

        private const val ID_PARAM = "videoId"
    }
}
