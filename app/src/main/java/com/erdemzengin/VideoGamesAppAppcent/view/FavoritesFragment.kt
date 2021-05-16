package com.erdemzengin.VideoGamesAppAppcent.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdemzengin.VideoGamesAppAppcent.R
import com.erdemzengin.VideoGamesAppAppcent.adapter.FavoriteGamesRecyclerAdapter
import com.erdemzengin.VideoGamesAppAppcent.local.AppDatabase
import com.erdemzengin.VideoGamesAppAppcent.model.GameDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFragment : Fragment(), FavoriteGamesRecyclerAdapter.Listener {

    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy {
        FavoriteGamesRecyclerAdapter(arrayListOf(), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initRecycler(view)


        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.invoke(requireContext()).gameDao()
            val games = dao.getAll()

            withContext(Dispatchers.Main) {
                adapter.updateData(games)
            }

        }
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.favoritesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
    }

    override fun onItemClicked(game: GameDetail) {
        val intent = Intent(this.activity, DetailActivity::class.java)
        intent.putExtra("id", game.id)
        startActivity(intent)
    }
//YENİ EKLEDİM
    override fun onResume() {
    super.onResume()
    CoroutineScope(Dispatchers.IO).launch {
        val dao = AppDatabase.invoke(requireContext()).gameDao()
        val games = dao.getAll()

        withContext(Dispatchers.Main) {
            adapter.updateData(games)
        }


    }
}

}














