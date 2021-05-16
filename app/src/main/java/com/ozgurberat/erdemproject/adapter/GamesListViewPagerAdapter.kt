package com.ozgurberat.erdemproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ozgurberat.erdemproject.R
import com.ozgurberat.erdemproject.model.GameModel

class GamesListViewPagerAdapter(val games:ArrayList<GameModel>, val listener: Listener): RecyclerView.Adapter<GamesListViewPagerAdapter.GamesViewPagerViewHolder>() {

    interface Listener {
        fun onItemClickedViewPager(game: GameModel)
    }

    class GamesViewPagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val gameImage: ImageView = itemView.findViewById(R.id.viewPagerImageView)

        fun bindItems(game: GameModel, listener: Listener) {
            Glide.with(itemView.context)
                .load(game.imageUrl)
                .into(gameImage)

            itemView.setOnClickListener {
                listener.onItemClickedViewPager(game)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.games_vp_cardview, parent,false)
        return GamesViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesViewPagerViewHolder, position: Int) {
        holder.bindItems(games[position], listener)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun updateData(gamesListForViewPager: java.util.ArrayList<GameModel>) {
        games.clear()
        games.addAll(gamesListForViewPager)
        notifyDataSetChanged()
    }


}