package ru.itis.sing_english

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.net.HttpURLConnection
import java.net.URL

class NotificationsFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("TEST", getLyrics("lana del rey", "born to die").toString())
    }

    private fun getLyrics(artist: String, song: String) : MutableList<String> {
        var tmp: MutableList<String> = emptyList<String>().toMutableList()
        launch {
            withContext(Dispatchers.IO) {
                val doc =
                    Jsoup.connect("https://www.rentanadviser.com/en/subtitles/getsubtitle.aspx?artist=${artist}&song=${song}")
                        .get()
                val html = doc.body().getElementById("ctl00_ContentPlaceHolder1_lbllyrics_simple").html()
                tmp = html.replace("&nbsp;", " ").split("<br>").toMutableList()
                Log.e("HTML", tmp.toString())
            }
        }
        return tmp
    }
}