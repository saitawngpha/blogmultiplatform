package com.saitawngpha.blogapp.screens.category

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitawngpha.blogapp.data.MongoSync
import com.saitawngpha.blogapp.models.Category
import com.saitawngpha.blogapp.models.Post
import com.saitawngpha.blogapp.utils.Constants.CATEGORY_ARGUMENT
import com.saitawngpha.blogapp.utils.RequestState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */
class CategoryViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _categoryPosts : MutableState<RequestState<List<Post>>> = mutableStateOf(RequestState.Idel)
    val categoryPosts : State<RequestState<List<Post>>> = _categoryPosts

    init {
        _categoryPosts.value = RequestState.Loading
        val selectedCategory = savedStateHandle.get<String>(CATEGORY_ARGUMENT)
        if (selectedCategory != null){
            viewModelScope.launch {
                MongoSync.searchPostsByCategory(category = Category.valueOf(selectedCategory))
                    .collectLatest { _categoryPosts.value = it }
            }
        }
    }
}