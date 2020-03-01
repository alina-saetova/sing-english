package ru.itis.sing_english

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class DashboardFragment : Fragment(), CoroutineScope by MainScope(), SearchView.OnQueryTextListener {

    private lateinit var service: YoutubeService
    private var adapter: VideoAdapter? = null
    private val broadcast = ConflatedBroadcastChannel<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        service = ApiFactory.youtubeService
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
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
                            ListPaddingDecoration(
                                context,
                                0,
                                0
                            )
                        )
                        rv_videos.adapter = VideoAdapter(response.videoItems as MutableList<VideoItem>) {
                            goToVideo("aaa")
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
//        val intent = Intent(this@MainActivity, WeatherActivity::class.java)
//        intent.putExtra("id", id)
//        startActivity(intent)
    }

    companion object {

        private const val ARG_SUM = "sum"

        fun newInstance(sum: Int = 0): DashboardFragment = DashboardFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SUM, sum)
            }
        }
    }
}