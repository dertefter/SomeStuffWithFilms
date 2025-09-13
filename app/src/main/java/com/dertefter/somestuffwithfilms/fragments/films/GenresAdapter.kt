package com.dertefter.somestuffwithfilms.fragments.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dertefter.somestuffwithfilms.databinding.ItemGenreBinding
import java.util.Locale

class GenresAdapter(
    private val onItemClick: ((String) -> Unit)? = null
) : RecyclerView.Adapter<GenresAdapter.GenreViewHolder>() {

    private var items: MutableList<String> = mutableListOf()
    private var selectedGenre: String? = null

    inner class GenreViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.genre.text = text.toCapitalized()
            binding.selectedIndicator.isVisible = text.lowercase() == selectedGenre?.lowercase()

            binding.root.setOnClickListener {
                onItemClick?.invoke(text)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<String>) {
        items.clear()
        items = newItems.sorted().toMutableList()
        notifyDataSetChanged()
    }

    fun addItem(item: String) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun setSelectedGenre(g: String) {
        selectedGenre = g
        notifyDataSetChanged()
    }

    fun String.toCapitalized() = replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}
