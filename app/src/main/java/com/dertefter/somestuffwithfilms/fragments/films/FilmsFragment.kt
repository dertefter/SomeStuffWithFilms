package com.dertefter.somestuffwithfilms.fragments.films

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dertefter.somestuffwithfilms.R
import com.dertefter.somestuffwithfilms.databinding.FragmentFilmsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilmsViewModel by viewModel()
    lateinit var filmsAdapter: FilmsAdapter
    lateinit var genresAdapter: GenresAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            binding.appbar.updatePadding(top = bars.top, left = bars.left, right = bars.right)
            binding.content.updatePadding(bottom = bars.bottom, right = bars.right, left = bars.left)
            binding.errorView.updatePadding(bottom = bars.bottom, right = bars.right, left = bars.left)
            WindowInsetsCompat.CONSUMED
        }

        binding.buttonRetry.setOnClickListener {
            viewModel.loadFilms()
        }

        setupFilmsRecyclerView()
        setupGenresRecyclerView()
        collectFilms()
        collectGenres()

    }

    fun setupFilmsRecyclerView(){
        filmsAdapter = FilmsAdapter(
            onItemClick = { film ->
                val action = FilmsFragmentDirections.actionFilmsToFilmsDetail(film)
                findNavController().navigate(action)
            }
        )
        binding.filmsRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.filmsRv.addItemDecoration(GridSpacingItemDecoration(requireContext(), 2, R.dimen.space_min, R.dimen.space_mid))
        binding.filmsRv.adapter = filmsAdapter

    }

    fun setupGenresRecyclerView(){
        genresAdapter = GenresAdapter(
            onItemClick = { genre ->
                Log.e("click", genre)
                viewModel.setSelectedGenre(genre)
            }
        )
        binding.genresRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.genresRv.adapter = genresAdapter

    }

    fun collectFilms(){
        lifecycleScope.launch {
            viewModel.filmsState.collect { state ->

                binding.progressBar.isGone = state !is FilmsState.Loading
                binding.errorView.isGone = state !is FilmsState.Error
                binding.content.isGone = state !is FilmsState.Success

                if (state is FilmsState.Success){
                    filmsAdapter.submitList(state.films)
                } else {
                    filmsAdapter.submitList(emptyList())
                }
            }
        }
    }

    fun collectGenres(){
        lifecycleScope.launch {
            viewModel.selectedGenre.collect { genre ->
                Log.e("selectedGenre", genre.toString())
                filmsAdapter.setFilter(genre ?: "")
                genresAdapter.setSelectedGenre(genre ?: "")

            }

        }

        lifecycleScope.launch {
            viewModel.genres.collect { genres ->
                genresAdapter.setItems(genres ?: emptyList())
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
