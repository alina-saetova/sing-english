package ru.itis.sing_english.view.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_song.*
import kotlinx.coroutines.*
import ru.itis.sing_english.MainActivity
import ru.itis.sing_english.databinding.FragmentSongBinding
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.viewmodel.SongViewModel
import javax.inject.Inject

class SongFragment : Fragment(), CoroutineScope by MainScope(), Injectable {

    private lateinit var youTubePlayerView: YouTubePlayerView
    lateinit var viewModel: SongViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: FragmentSongBinding
    lateinit var videoId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater)
//        val adapter = SubtitleAdapter(emptyList<Subtitle>().toMutableList())
//        binding.rvSubs.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this, viewModelFactory).get(SongViewModel::class.java)
        arguments?.let {
            videoId = it.getString(ID_PARAM).toString()
        }
        viewModel.loadSong(videoId, true)
        binding.songViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        youTubePlayerView = youtube_player_view
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(playerListener)
    }

    private val playerListener : AbstractYouTubePlayerListener = object: AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.cueVideo(videoId, START_SECOND)
        }

        var flag = false
        override fun onStateChange(
            youTubePlayer: YouTubePlayer,
            state: PlayerConstants.PlayerState
        ) {
            if (state == PlayerConstants.PlayerState.PLAYING && !flag) {
                viewModel.start()
                flag = true
            }
            super.onStateChange(youTubePlayer, state)
        }

        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            viewModel.onPlaying(second)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }

    companion object {
        const val START_SECOND = 0f
        const val ID_PARAM = "videoId"
    }
}
