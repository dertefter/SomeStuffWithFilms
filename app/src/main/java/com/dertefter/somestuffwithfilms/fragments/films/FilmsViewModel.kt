package com.dertefter.somestuffwithfilms.fragments.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dertefter.somestuffwithfilms.data.repository.FilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FilmsViewModel(private val repository: FilmsRepository) : ViewModel() {

    init{
        loadFilms()
    }
    private val _filmsState = MutableStateFlow<FilmsState>(FilmsState.Loading)
    val filmsState: StateFlow<FilmsState> = _filmsState.asStateFlow()

    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre: StateFlow<String?> = _selectedGenre.asStateFlow()

    private val _genres = MutableStateFlow<List<String>?>(null)
    val genres: StateFlow<List<String>?> = _genres.asStateFlow()

    fun loadFilms() {
        viewModelScope.launch {
            _selectedGenre.value = null
            _filmsState.value = FilmsState.Loading
            try {
                val data = repository.getFilms()

                val genresList = mutableListOf<String>()
                for (film in data){
                    for (genre in film.genres){
                        if (!genresList.contains(genre)){
                            genresList.add(genre)
                        }
                    }
                }
                _genres.value = genresList
                _filmsState.value = FilmsState.Success(data)
            } catch (e: Exception) {
                _filmsState.value = FilmsState.Error(e)
            }
        }
    }


    fun setSelectedGenre(s: String?){
        viewModelScope.launch {
            if (_selectedGenre.value == s){
                _selectedGenre.value = null
            }else{
                _selectedGenre.value = s
            }

        }
    }

}