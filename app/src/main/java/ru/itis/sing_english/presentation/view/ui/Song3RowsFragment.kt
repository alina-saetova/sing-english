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
import kotlinx.android.synthetic.main.fragment_song3_rows.*
import ru.itis.sing_english.R
import ru.itis.sing_english.databinding.FragmentSong3RowsBinding
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.ui.FavouritesFragment.Companion.FROM
import ru.itis.sing_english.presentation.view.ui.FavouritesFragment.Companion.FROM_FAV
import ru.itis.sing_english.presentation.viewmodel.SongViewModel
import javax.inject.Inject

class Song3RowsFragment : Fragment() {

    @Inject
    lateinit var viewModel: SongViewModel
    lateinit var binding: FragmentSong3RowsBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
    lateinit var videoId: String
    lateinit var from: String
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusSongComponent().inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoId = it.getString(ID_PARAM).toString()
            from = it.getString(FROM).toString()
            flag = it.getBoolean(ChooseLevelFragment.FLAG_PARAM)
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showWarningDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSong3RowsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loadSong(videoId, flag, ROWS_NUM)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        youTubePlayerView = youtube_player_view
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(playerListener)
    }

    private val playerListener: AbstractYouTubePlayerListener =
        object : AbstractYouTubePlayerListener() {
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
        bundle.putParcelableArrayList(
            LYRIC_PARAM,
            ArrayList<Parcelable>(viewModel.fullLyricWithAnswers)
        )
        bundle.putStringArrayList(ANSWERS_PARAM, ArrayList(viewModel.rightAnswers))
        findNavController().navigate(R.id.action_song3Rows_to_statistic, bundle)
    }

    private fun showWarningDialog() {
        val actionId = if (from == FROM_FAV) {
            R.id.action_back_fromSong3Rows_to_favourites
        } else {
            R.id.action_back_fromSong3Rows_to_main
        }
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.dialogTitle))
            .setMessage(resources.getString(R.string.dialogText))
            .setNegativeButton(resources.getString(R.string.dialogNegative)) { _, _ ->
                findNavController().navigate(actionId)
            }
            .setPositiveButton(resources.getString(R.string.dialogPositive)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).apply {
            supportActionBar?.hide()
            hideBottomNavigation()
        }
    }

    override fun onDetach() {
        (activity as MainActivity).apply {
            supportActionBar?.show()
            showBottomNavigation()
        }
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearSongComponent()
    }

    companion object {
        const val START_SECOND = 0f
        const val ROWS_NUM = 3
        const val ID_PARAM = "videoId"
        const val LYRIC_PARAM = "lyric"
        const val ANSWERS_PARAM = "answers"
    }
}
