package com.ozgurberat.erdemproject.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ozgurberat.erdemproject.R
import com.ozgurberat.erdemproject.adapter.GamesListRecyclerAdapter
import com.ozgurberat.erdemproject.adapter.GamesListViewPagerAdapter
import com.ozgurberat.erdemproject.model.GameModel
import com.ozgurberat.erdemproject.viewmodel.HomeViewModel

class HomeFragment : Fragment(), GamesListRecyclerAdapter.Listener,
    GamesListViewPagerAdapter.Listener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager2
    private lateinit var searchView: SearchView
    private lateinit var errorMessage: ConstraintLayout

    private val recyclerAdapter by lazy {
        GamesListRecyclerAdapter(arrayListOf(), this)
    }

    private val viewPagerAdapter by lazy {
        GamesListViewPagerAdapter(arrayListOf(), this)
    }

    private lateinit var viewModel: HomeViewModel
    /** BÜTÜN OYUNLAR */
    private lateinit var games: List<GameModel>
    /** İLK 3 OYUN */
    private val gamesListForViewPager = arrayListOf<GameModel>()
    /** GERİ KALAN TÜM OYUNLAR */
    private val gamesForRecyclerView = arrayListOf<GameModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorMessage = view.findViewById(R.id.constraintLayout)

        initRecycler(view)
        initViewPager(view)

        setupSearchView(view)

        viewModel.getListOfGames()
        observeModelData()
    }

    private fun setupSearchView(view: View) {
        searchView = view.findViewById(R.id.homeSearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! >= 3) {
                    viewPager.visibility = View.GONE
                    recyclerAdapter.updateData(games)
                    recyclerAdapter.filter.filter(newText)
                }
                else if (newText.isEmpty()) {
                    viewPager.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    viewPagerAdapter.updateData(gamesListForViewPager)
                    recyclerAdapter.updateData(gamesForRecyclerView)
                }
                return true
            }

        })
    }

    private fun observeModelData() {
        viewModel.listOfGames.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                games = it.results

                gamesListForViewPager.add(games[0])
                gamesListForViewPager.add(games[1])
                gamesListForViewPager.add(games[2])

                viewPagerAdapter.updateData(gamesListForViewPager)

                for (i in 3 until games.size) {
                    gamesForRecyclerView.add(games[i])
                }

                recyclerAdapter.updateData(gamesForRecyclerView)
            }
        })

        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            println("ERROR: ${it.toString()}")
        })
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = recyclerAdapter
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById(R.id.viewPager2)
        viewPager.offscreenPageLimit = 3
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        viewPager.adapter = viewPagerAdapter
    }

    override fun onItemClicked(game: GameModel) {
        val intent = Intent(this.activity, DetailActivity::class.java)
        intent.putExtra("id", game.id)
        startActivity(intent)
    }

    override fun onFilteredResult(length: Int) {
        if (length == 0) {
            errorMessage.visibility = View.VISIBLE
        }
        else {
            errorMessage.visibility = View.GONE
        }
    }

    override fun onItemClickedViewPager(game: GameModel) {
        val intent = Intent(this.activity, DetailActivity::class.java)
        intent.putExtra("id", game.id)
        startActivity(intent)
    }

}
















