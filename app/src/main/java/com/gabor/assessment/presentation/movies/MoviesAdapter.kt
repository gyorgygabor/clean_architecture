package com.gabor.assessment.presentation.movies


import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import com.gabor.assessment.R
import com.squareup.picasso.Picasso

/**
 * View adapter for showing the  movies list
 */
class MoviesAdapter internal constructor(private val listItems: List<MovieViewItem>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        holder.title.text = item.title
        holder.description.text = item.description
        Picasso.get()
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_placeholder_movie)
            .fit()
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val title: TextView = itemView.findViewById(R.id.title)
        internal val description: TextView = itemView.findViewById(R.id.description)
        internal val image: ImageView = itemView.findViewById(R.id.image)
    }
}