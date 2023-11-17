package com.saitawngpha.blogapp.data

import com.saitawngpha.blogapp.models.Category
import com.saitawngpha.blogapp.models.Post
import com.saitawngpha.blogapp.utils.RequestState
import kotlinx.coroutines.flow.Flow

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */
interface MongoSyncRepository {

    fun configureTheRealm()
    fun readAllPosts(): Flow<RequestState<List<Post>>>
    fun searchPostsByTitle(query: String): Flow<RequestState<List<Post>>>
    fun searchPostsByCategory(category: Category): Flow<RequestState<List<Post>>>
}