package com.dertefter.somestuffwithfilms.fragments.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dertefter.somestuffwithfilms.data.model.Film
import com.dertefter.somestuffwithfilms.databinding.ItemFilmBinding
import com.squareup.picasso.Picasso

class FilmsAdapter(
    private val onItemClick: (Film) -> Unit
) : RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {


    private var genre: String? = null
    private var allFilms: List<Film> = emptyList()
    private var displayedFilms: List<Film> = emptyList()

    inner class FilmViewHolder(val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.title.text = film.localized_name
            Picasso.get().load(film.image_url).into(binding.imageView)
            binding.root.setOnClickListener { onItemClick(film) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(displayedFilms[position])
    }

    override fun getItemCount(): Int = displayedFilms.size

    fun submitList(films: List<Film>) {
        allFilms = films
        setFilter(genre)
    }

    fun setFilter(newGenre: String?) {

        genre = newGenre

        if (genre.isNullOrEmpty()){
            updateList(allFilms.sortedBy { it.localized_name })
        }else{
            val filtered = allFilms.filter { it.genres.contains(genre) }.sortedBy { it.localized_name }
            updateList(filtered)
        }

    }

    private fun updateList(newList: List<Film>) {
        val diffCallback = FilmDiffCallback(displayedFilms, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        displayedFilms = newList
        diffResult.dispatchUpdatesTo(this)
    }

    class FilmDiffCallback(
        private val oldList: List<Film>,
        private val newList: List<Film>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
