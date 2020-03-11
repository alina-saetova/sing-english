package ru.itis.sing_english.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_vocabulary.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import retrofit2.HttpException
import ru.itis.sing_english.R
import ru.itis.sing_english.data.source.repository.WordsRepository
import ru.itis.sing_english.di.App
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class VocabularyFragment : Fragment(), CoroutineScope by MainScope(), SearchView.OnQueryTextListener {

    private val broadcast = ConflatedBroadcastChannel<String>()
    @Inject
    lateinit var repository: WordsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repository = App.component.wordsRepository()
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sv_words.setOnQueryTextListener(this)

        launch {
            var lastTimeout: Job? = null
            broadcast.consumeEach {
                lastTimeout?.cancel()
                lastTimeout = launch {
                    delay(1000)
                    try {
                        val response = repository.getWord(it)
                        Log.e("WORD", response.toString())
                    } catch (e: HttpException) {
                        Log.e("EXC_HANDLER", "$e")
                    }
                }
            }
            lastTimeout?.join()
        }
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
//        broadcast.offer(newText ?: "")
        activity?.supportFragmentManager?.also {
            it.beginTransaction().apply {
                replace(R.id.container, WordDetailFragment.newInstance(newText.toString()))
                addToBackStack(WordDetailFragment::class.java.name)
                commit()
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        broadcast.offer(newText ?: "")
        return true
    }

    companion object {

        fun newInstance(param1: String = "") =
            VocabularyFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM, param1)
                }
            }

        private const val PARAM = "param"
    }
}
