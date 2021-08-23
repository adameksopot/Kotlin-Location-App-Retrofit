package com.example.posttest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posttest.model.Item2
import com.example.posttest.model.Post
import com.example.posttest.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private  val repository: Repository):ViewModel() {
    val myResponse: MutableLiveData<Response<Post>> = MutableLiveData()
    val myResponse2: MutableLiveData<Response<Item2>> = MutableLiveData()

    fun pushPost(post: Item2){
        viewModelScope.launch {
            val response : Response<Item2> = repository.pushPost(post)
            myResponse2.value = response
        }


    }
    fun getPost (){
        viewModelScope.launch {
            val response :Response<Post> = repository.getPost()
            myResponse.value = response
        }
    }
}