package com.dertefter.somestuffwithfilms.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val id: Int,
    val localized_name: String,
    val name: String,
    val year: Int,
    val rating: Double?,
    val image_url: String?,
    val description: String?,
    val genres: List<String>
): Parcelable