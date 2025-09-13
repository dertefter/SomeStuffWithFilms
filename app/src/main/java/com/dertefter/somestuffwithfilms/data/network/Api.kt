package com.dertefter.somestuffwithfilms.data.network

import com.dertefter.somestuffwithfilms.data.model.FilmsResponse
import retrofit2.http.GET

interface Api {
    @GET("sequeniatesttask/films.json")
    suspend fun fetchFilms(): FilmsResponse

}