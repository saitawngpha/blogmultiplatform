package com.saitawngpha.blogapp.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitawngpha.blogapp.data.MongoSync
import com.saitawngpha.blogapp.models.Post
import com.saitawngpha.blogapp.utils.Constants.APP_ID
import com.saitawngpha.blogapp.utils.RequestState
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */
class HomeViewModel: ViewModel() {
   private val _allPosts : MutableState<RequestState<List<Post>>> = mutableStateOf(RequestState.Idel)
    val allPosts : State<RequestState<List<Post>>> = _allPosts
    private val _searchPosts : MutableState<RequestState<List<Post>>> = mutableStateOf(RequestState.Idel)
    val searchPosts : State<RequestState<List<Post>>> = _searchPosts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("app", "Before")
            App.create(APP_ID).login(Credentials.anonymous())
            Log.d("app", "After")
            fetchAllPosts()
        }
    }
   private fun fetchAllPosts() {
        viewModelScope.launch{
            withContext(Dispatchers.Main){
                _allPosts.value = RequestState.Loading
            }
            MongoSync.readAllPosts().collectLatest {
                _allPosts.value = it
            }
        }
    }

    fun searchPostsByTitle(query: String) {
        viewModelScope.launch{
            withContext(Dispatchers.Main){
                _searchPosts.value = RequestState.Loading
            }
            MongoSync.searchPostsByTitle(query = query).collectLatest {
                _searchPosts.value = it
            }
        }
    }

    fun resetSearchedPost(){
        _searchPosts.value = RequestState.Idel
    }
}