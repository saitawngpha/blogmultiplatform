package com.saitawngpha.blogmultiplatform.data

import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Newsletter
import com.saitawngpha.blogmultiplatform.models.Post
import com.saitawngpha.blogmultiplatform.models.PostWithoutDetails
import com.saitawngpha.blogmultiplatform.models.User

interface MongoRepository {

    suspend fun addPost(post: Post): Boolean
    suspend fun updatePost(post: Post): Boolean
    suspend fun readMyPosts(skip: Int, author: String): List<PostWithoutDetails>
    suspend fun readMainPosts(): List<PostWithoutDetails>
    suspend fun readLatestPosts(skip: Int): List<PostWithoutDetails>
    suspend fun readSponsoredPosts(): List<PostWithoutDetails>
    suspend fun readPopularPosts(skip: Int): List<PostWithoutDetails>
    suspend fun deleteSelectedPosts(ids: List<String>): Boolean
    suspend fun searchPostsByTitle(query: String, skip: Int): List<PostWithoutDetails>
    suspend fun searchPostsByCategory(category: Category, skip: Int): List<PostWithoutDetails>
    suspend fun readSelectedPost(id: String): Post
    suspend fun checkUserExistence(user: User) : User?
    suspend fun checkUserId(id: String): Boolean
    suspend fun subscribe(newsletter: Newsletter): String
}