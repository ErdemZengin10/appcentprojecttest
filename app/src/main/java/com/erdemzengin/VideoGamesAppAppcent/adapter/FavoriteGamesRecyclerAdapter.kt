package com.erdemzengin.VideoGamesAppAppcent.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erdemzengin.VideoGamesAppAppcent.R
import com.erdemzengin.VideoGamesAppAppcent.model.GameDetail

class FavoriteGamesRecyclerAdapter(val games:ArrayList<GameDetail>, val listener: Listener):RecyclerView.Adapter<FavoriteGamesRecyclerAdapter.FavoritesViewHolder>() {

    interface Listener {
        fun onItemClicked(game: GameDetail)
    }

    class FavoritesViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val gameImage: ImageView = itemView.findViewById(R.id.cardImageView)
        val name: TextView = itemView.findViewById(R.id.cardNameTextView)
        val released: TextView = itemView.findViewById(R.id.cardRatingAndReleasedTextView)

        fun bindItems(game: GameDetail, listener: Listener) {
            Glide.with(itemView.context)
                .load(game.imageUrl)
                .into(gameImage)

            name.text = game.name
            released.text = game.released

            itemView.setOnClickListener {
                listener.onItemClicked(game)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.games_cardview, parent,false)
        return FavoritesViewHolder(view)

    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {

        holder.bindItems(games[position], listener)


    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun updateData(list: List<GameDetail>) {
        games.clear()
        games.addAll(list)
        notifyDataSetChanged()
    }


}