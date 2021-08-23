package com.example.posttest.repository

import com.example.posttest.api.RetrofitInstance
import com.example.posttest.model.Item2
import com.example.posttest.model.Post
import retrofit2.Response

class Repository {
    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPost()

    }

    suspend fun pushPost(post: Item2):Response<Item2>{
        return RetrofitInstance.api.pushPost(post)
    }
}