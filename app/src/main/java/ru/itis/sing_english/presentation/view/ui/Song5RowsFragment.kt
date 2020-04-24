package ru.itis.sing_english.presentation.view.ui

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_song5_rows.*
import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.FragmentSong5RowsBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.ui.ChooseLevelFragment.Companion.FLAG_PARAM
import ru.itis.sing_english.presentation.viewmodel.SongViewModel
import javax.inject.Inject

class Song5RowsFragment : Fragment() {

    @Inject
    lateinit var viewModel: SongViewModel
    lateinit var binding: FragmentSong5RowsBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
    lateinit var videoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusSongComponent().inject(this)
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showWarningDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSong5RowsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        var flag = false
        arguments?.let {
            videoId = it.getString(ID_PARAM).toString()
            flag = it.getBoolean(FLAG_PARAM)
        }
        viewModel.loadSong(videoId, flag, 5)
        binding.viewModel = viewModel

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
            if (state == PlayerConstants.PlayerState.ENDED) {
                goToStatistic()
            }
            super.onStateChange(youTubePlayer, state)
        }

        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            viewModel.onPlaying(second)
        }
    }

    private fun goToStatistic() {
        val bundle = Bundle()
        bundle.putParcelableArrayList(LYRIC_PARAM, ArrayList<Parcelable>(viewModel.fullLyricWithAnswers))
        bundle.putStringArrayList(ANSWERS_PARAM, ArrayList(viewModel.rightAnswers))
        findNavController().navigate(R.id.action_song5Rows_to_statistic, bundle)
    }

    private fun showWarningDialog() {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.dialogTitle))
            .setMessage(resources.getString(R.string.dialogText))
            .setNegativeButton(resources.getString(R.string.dialogNegative)) { _, _ ->
                findNavController().navigate(R.id.action_back_fromSong5Rows_to_main)
            }
            .setPositiveButton(resources.getString(R.string.dialogPositive)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearSongComponent()
    }

    companion object {
        const val START_SECOND = 0f
        const val ID_PARAM = "videoId"
        const val LYRIC_PARAM = "lyric"
        const val ANSWERS_PARAM = "answers"
    }
}
