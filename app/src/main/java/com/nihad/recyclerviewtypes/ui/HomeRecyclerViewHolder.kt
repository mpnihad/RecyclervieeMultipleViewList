package com.nihad.recyclerviewtypes.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.nihad.recyclerviewtypes.R
import com.nihad.recyclerviewtypes.databinding.ItemDirectorBinding
import com.nihad.recyclerviewtypes.databinding.ItemMovieBinding
import com.nihad.recyclerviewtypes.databinding.ItemTitleBinding

sealed class HomeRecyclerViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {


    class TitleViewHolder(private val binding: ItemTitleBinding) : HomeRecyclerViewHolder(binding) {
        fun bind(title: HomeRecyclerViewItem.Title) {
            binding.textViewTitle.text = title.title
            binding.textViewAll.setOnClickListener {
                title.callBack()
            }
        }
    }


    class MovieViewHolder(private val binding: ItemMovieBinding) : HomeRecyclerViewHolder(binding) {
        fun bind(movie: HomeRecyclerViewItem.Movie, callBack: () -> Unit = {}) {

            movie.thumbnail?.let { thumbnail ->
                binding.imageViewMovie.loadImage(thumbnail)
            }

        }
    }

    class DirectorViewHolder(private val binding: ItemDirectorBinding) :
        HomeRecyclerViewHolder(binding) {
        fun bind(director: HomeRecyclerViewItem.Director, callBack: () -> Unit ={}) {
            director.avatar?.let { avatar ->

                binding.imageViewDirector.loadImage(avatar)
            }
            binding.textViewName.text = director.name
            binding.textViewMovies.text = binding.textViewMovies.context.getString(R.string.total_movies,director.movie_count)

        }
    }

}