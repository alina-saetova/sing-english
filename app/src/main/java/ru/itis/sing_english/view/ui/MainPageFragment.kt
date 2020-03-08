package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_mainpage.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import retrofit2.HttpException
import ru.itis.sing_english.*
import ru.itis.sing_english.data.model.VideoItem
import ru.itis.sing_english.data.source.remote.YoutubeVideoService
import ru.itis.sing_english.di.App
import ru.itis.sing_english.utils.ListPaddingDecoration
import ru.itis.sing_english.view.recyclerview.VideoAdapter
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainPageFragment : Fragment(), CoroutineScope by MainScope(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var service: YoutubeVideoService
    private var adapter: VideoAdapter? = null
    private val broadcast = ConflatedBroadcastChannel<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectMainPage(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mainpage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchView.setOnQueryTextListener(this)

        launch {
            var lastTimeout: Job? = null
            broadcast.consumeEach {
                lastTimeout?.cancel()
                lastTimeout = launch {
                    delay(1000)
                    try {
                        val response = withContext(Dispatchers.IO) {
                            service.videosByName(it)
                        }
                        rv_videos.addItemDecoration(
                            ListPaddingDecoration(context, 0, 0)
                        )
                        rv_videos.adapter =
                            VideoAdapter(
                                response.videoItems as MutableList<VideoItem>
                            ) {
                                goToVideo(it)
                            }
                    } catch (e: HttpException) {
                        Log.e("EXC_HANDLER", "$e")
                    }
                }
            }
            lastTimeout?.join()
        }
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        broadcast.offer(newText ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        broadcast.offer(newText ?: "")
        return true
    }

    private fun goToVideo(id: String) {
        activity?.supportFragmentManager?.also {
            it.beginTransaction().apply {
                replace(R.id.container, SongFragment.newInstance(id))
                addToBackStack(SongFragment::class.java.name)
                commit()
            }
        }
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