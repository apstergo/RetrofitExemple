package ru.apstegor.retrofitexemple.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.apstegor.retrofitexemple.data.model.PostDto

interface ApiExemple {

    @GET("/posts/{id}")
    suspend fun getPost(
        @Path("id") id:Int
    ): PostDto
}