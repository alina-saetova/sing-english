package ru.itis.sing_english.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_song.*
import kotlinx.coroutines.*
import ru.itis.sing_english.R
import ru.itis.sing_english.factories.SubtitleApiFactory
import ru.itis.sing_english.responses.SubtitleResponse

class SongFragment : Fragment(), CoroutineScope by MainScope() {

    private var videoId: String? = null
    private lateinit var youTubePlayerView: YouTubePlayerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoId = it.getString(ID_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        youTubePlayerView = youtube_player_view
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(playerListener)

        val service = SubtitleApiFactory.subtitleService
        launch {
            var response: List<SubtitleResponse>
            var text = ""
            withContext(Dispatchers.IO) {
                response = service.subtitlesByVideoId(videoId.toString())
                Log.e("resp", response.toString())
                for (x in response) {
                    text += "\n${x.text}"
                }
            }
            launch(Dispatchers.Main) {
                tv_lyric.text = text
            }
        }
    }

    private val playerListener : AbstractYouTubePlayerListener = object: AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.cueVideo(videoId.toString(), 0f)
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
