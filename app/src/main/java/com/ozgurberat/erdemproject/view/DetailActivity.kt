package com.ozgurberat.erdemproject.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ozgurberat.erdemproject.R
import com.ozgurberat.erdemproject.model.GameDetail
import com.ozgurberat.erdemproject.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var name: TextView
    private lateinit var release: TextView
    private lateinit var metacritic: TextView
    private lateinit var description: TextView
    private lateinit var fab: FloatingActionButton

    private lateinit var viewModel: DetailViewModel
    private var isFavorite = false
    private lateinit var currentGame: GameDetail
    private val myContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        initViewsAndSetListeners()

        val id = intent.getStringExtra("id")
        viewModel.getGame(id!!)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.game.observe(this, Observer {
            if (it != null) {
                currentGame = it

                val sharedPrefs = myContext.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
                val isGameInFavorites = sharedPrefs.getBoolean(currentGame.name, false)
                if (isGameInFavorites) {
                    fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                    isFavorite = true
                }
                else {
                    fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    isFavorite = false
                }

                Glide.with(this)
                    .load(it.imageUrl)
                    .into(image)
                name.text = it.name
                release.text = it.released
                metacritic.text = it.metacritic
                description.text = it.description
            }
        })
    }

    private fun initViewsAndSetListeners() {
        image = findViewById(R.id.detailImageView)
        name = findViewById(R.id.detailName)
        release = findViewById(R.id.detailRelease)
        metacritic = findViewById(R.id.detailMetacritic)
        description = findViewById(R.id.detailDescription)
        fab = findViewById(R.id.detailFavButton)

        fab.setOnClickListener {
            if (!isFavorite) {
                fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                isFavorite = true

                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.putGameIntoDatabase(myContext, currentGame)
                    val sharedPrefs = myContext.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
                    sharedPrefs.edit().putBoolean(currentGame.name, true).apply()
                }

            }
            else {
                fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                isFavorite = false

                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.deleteGameFromDatabase(myContext, currentGame)
                    val sharedPrefs = myContext.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
                    sharedPrefs.edit().remove(currentGame.name).apply()
                }

            }
        }
    }

}

















