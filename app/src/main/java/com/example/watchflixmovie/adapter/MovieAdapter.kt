package com.example.watchflixmovie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.watchflixmovie.R
import com.example.watchflixmovie.activity.DetailActivity
import com.example.watchflixmovie.domain.MovieList
import com.example.watchflixmovie.domain.Movies

class MovieAdapter(val movieList:ArrayList<Movies>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val  picture:ImageView = view.findViewById(R.id.pic)
        val title:TextView = view.findViewById(R.id.titleFilm)
        val scoreFilm:TextView = view.findViewById(R.id.scoreFilm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_film,parent,false)
        return  MovieViewHolder(view)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList.get(position)
        val URL_IMAGE = "https://image.tmdb.org/t/p/w500/"
        holder.title.text = item.original_title
        Glide.with(holder.itemView.context)
            .load(URL_IMAGE+item.poster_path)
            .into(holder.picture)
        holder.scoreFilm.text = item.popularity.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            intent.putExtra("id",item.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movieList.size
}