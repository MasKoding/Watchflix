package com.example.watchflixmovie.domain

data class Movies(
    val backdrop_path: String?,
    val id: Int?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_count: Int?,
    val vote_average: Double?
)