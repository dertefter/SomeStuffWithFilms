package com.dertefter.somestuffwithfilms.fragments.film_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dertefter.somestuffwithfilms.databinding.FragmentFilmDetailBinding
import com.squareup.picasso.Picasso

class FilmsDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!

    private val args: FilmsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
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
            WindowInsetsCompat.CONSUMED
        }



        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val film = args.film
        binding.materialToolbar.title = film.name
        binding.title.text = film.localized_name

        if (film.genres.isEmpty()){
            binding.yearGenre.text = "${film.year} год"
        }else{
            binding.yearGenre.text = "${film.genres.first()}, ${film.year} год"
        }

        Picasso.get().load(film.image_url).into(binding.imageView)

        binding.value.text = String.format("%.1f", film.rating)
        binding.text.text = film.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
