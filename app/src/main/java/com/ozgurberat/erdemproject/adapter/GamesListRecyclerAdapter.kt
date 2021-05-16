package com.ozgurberat.erdemproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ozgurberat.erdemproject.R
import com.ozgurberat.erdemproject.model.GameModel

class GamesListRecyclerAdapter(val games:ArrayList<GameModel>, val listener: Listener):
    RecyclerView.Adapter<GamesListRecyclerAdapter.GamesViewHolder>(), Filterable {

    private var gamesListFull = games

    interface Listener {
        fun onItemClicked(game: GameModel)
        fun onFilteredResult(length: Int)
    }

    class GamesViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val gameImage: ImageView = itemView.findViewById(R.id.cardImageView)
        val name: TextView = itemView.findViewById(R.id.cardNameTextView)
        val ratingAndReleased: TextView = itemView.findViewById(R.id.cardRatingAndReleasedTextView)


        fun bindItems(game: GameModel, listener: Listener) {
            Glide.with(itemView.context)
                .load(game.imageUrl)
                .into(gameImage)

            name.text = game.name
            val str = game.rating.toString() + " - " + game.released
            ratingAndReleased.text = str

            itemView.setOnClickListener {
                listener.onItemClicked(game)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.games_cardview, parent,false)
        return GamesViewHolder(view)

    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {

        holder.bindItems(games[position], listener)


    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun updateData(list: List<GameModel>) {
        games.clear()
        games.addAll(list)
        notifyDataSetChanged()
    }

    @ExperimentalStdlibApi
    override fun getFilter(): Filter {
        return gameFilter
    }

    @ExperimentalStdlibApi
    private val gameFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = arrayListOf<GameModel>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(gamesListFull)
            }
            else {
                val filterPattern = constraint.toString().lowercase().trim()
                for (item in gamesListFull) {
                    if (item.name.lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val result = results?.values as List<GameModel>
            listener.onFilteredResult(result.size)
            games.clear()
            games.addAll(result)
            notifyDataSetChanged()
        }
    }
}

























