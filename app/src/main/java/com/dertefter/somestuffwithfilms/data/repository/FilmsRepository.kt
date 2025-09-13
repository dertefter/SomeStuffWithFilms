package com.dertefter.somestuffwithfilms.data.repository

import com.dertefter.somestuffwithfilms.data.model.Film
import com.dertefter.somestuffwithfilms.data.network.Api

class FilmsRepository (private val api: Api) {

    suspend fun getFilms(): List<Film> {
        return api.fetchFilms().films
    }
}