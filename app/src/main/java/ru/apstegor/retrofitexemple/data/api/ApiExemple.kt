package ru.apstegor.retrofitexemple.data.api

import retrofit2.http.GET
import ru.apstegor.retrofitexemple.data.model.PostDto

interface ApiExemple {

    @GET("/posts/1")
    suspend fun getPost(): PostDto
}