package com.example.posttest.api

import com.example.posttest.model.Item2
import com.example.posttest.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SimpleApi {
    @GET("adamapi")
    suspend fun getPost():Response <Post>


    @POST("adamapi")
    suspend fun pushPost(
        @Body post: Item2
    ): Response<Item2>

}