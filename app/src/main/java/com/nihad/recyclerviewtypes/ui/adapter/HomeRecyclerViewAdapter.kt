package com.nihad.recyclerviewtypes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nihad.recyclerviewtypes.ui.HomeRecyclerViewHolder
import com.nihad.recyclerviewtypes.ui.HomeRecyclerViewItem
import com.nihad.recyclerviewtypes.R
import com.nihad.recyclerviewtypes.databinding.ItemDirectorBinding
import com.nihad.recyclerviewtypes.databinding.ItemMovieBinding
import com.nihad.recyclerviewtypes.databinding.ItemTitleBinding

class HomeRecyclerViewAdapter : RecyclerView.Adapter<HomeRecyclerViewHolder>() {

    var list: List<HomeRecyclerViewItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return when (viewType) {

            R.layout.item_director -> {
                val view = ItemDirectorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeRecyclerViewHolder.DirectorViewHolder(view)
            }


            R.layout.item_movie -> {
                val view = ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeRecyclerViewHolder.MovieViewHolder(view)
            }


            R.layout.item_title -> {
                val view = ItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeRecyclerViewHolder.TitleViewHolder(view)
            }
            else -> throw  IllegalArgumentException("Invalid viewtype provider")
        }
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {

        when (holder) {
            is HomeRecyclerViewHolder.DirectorViewHolder -> holder.bind(list[position] as HomeRecyclerViewItem.Director)
            is HomeRecyclerViewHolder.MovieViewHolder -> holder.bind(list[position] as HomeRecyclerViewItem.Movie)
            is HomeRecyclerViewHolder.TitleViewHolder -> holder.bind(list[position] as HomeRecyclerViewItem.Title)

        }
    }

    override fun getItemCount(): Int =
        list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is HomeRecyclerViewItem.Director ->
                R.layout.item_director

            is HomeRecyclerViewItem.Movie ->
                R.layout.item_movie

            is HomeRecyclerViewItem.Title ->
                R.layout.item_title

        }
    }
}