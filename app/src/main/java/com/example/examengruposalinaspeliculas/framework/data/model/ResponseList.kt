package com.example.examengruposalinaspeliculas.framework.data.model

data class ResponseList(
    val average_rating: Double,
    val backdrop_path: Any,
    //val comments: Comments,
    val created_by: CreatedBy,
    val description: String,
    val id: Int,
    val iso_3166_1: String,
    val iso_639_1: String,
    val name: String,
    //val object_ids: ObjectIds,
    val page: Int,
    val poster_path: Any,
    val `public`: Boolean,
    val results: List<Result>,
    val revenue: Long,
    val runtime: Int,
    val sort_by: String,
    val total_pages: Int,
    val total_results: Int
)