package com.dertefter.somestuffwithfilms.fragments.films

import com.dertefter.somestuffwithfilms.data.model.Film

sealed class FilmsState {
    data object Loading : FilmsState()
    data class Success(val films: List<Film>) : FilmsState()
    data class Error(val exception: Throwable) : FilmsState()
}