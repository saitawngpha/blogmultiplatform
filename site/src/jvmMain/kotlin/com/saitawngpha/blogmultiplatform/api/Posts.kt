package com.saitawngpha.blogmultiplatform.api

import com.saitawngpha.blogmultiplatform.data.MongoDB
import com.saitawngpha.blogmultiplatform.models.ApiListResponse
import com.saitawngpha.blogmultiplatform.models.ApiResponse
import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Constants.AUTHOR_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.CATEGORY_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.POST_ID_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.QUERY_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.SKIP_PARAM
import com.saitawngpha.blogmultiplatform.models.Post
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.Request
import com.varabyte.kobweb.api.http.Response
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import org.litote.kmongo.id.ObjectIdGenerator

/**
 * Created by တွင်ႉၾႃႉ on 04/11/2023.
 */

@Api(routeOverride = "addpost")
suspend fun addPost(context: ApiContext){
    try {
        val post = context.req.getBody<Post>()
        val newPost = post?.copy(id = ObjectIdGenerator.newObjectId<String>().id.toHexString())
        context.res.setBody(newPost?.let { context.data.getValue<MongoDB>().addPost(it) })
    }catch (e: Exception){
        context.res.setBody(
            data = e.message
        )
    }
}
@Api(routeOverride = "updatepost")
suspend fun updatePost(context: ApiContext){
    try {
        val updatePost = context.req.getBody<Post>()
        context.res.setBody(updatePost?.let { context.data.getValue<MongoDB>().updatePost(it) })
    }catch (e: Exception){
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = "readmyposts")
suspend fun readMyPosts(context: ApiContext){
    try {
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val author = context.req.params[AUTHOR_PARAM] ?: ""
        val myPost = context.data.getValue<MongoDB>().readMyPosts(
            skip= skip,
            author = author)
        context.res.setBody(
            data = ApiListResponse.Success(data = myPost)
        )
    }catch (e: Exception){
        context.res.setBody(
            data = ApiListResponse.Error(message = e.message.toString())
        )
    }
}

@Api(routeOverride = "fetchmainposts")
suspend fun readMainPosts(context: ApiContext){
    try {
        val mainPosts = context.data.getValue<MongoDB>().readMainPosts()
        context.res.setBody(ApiListResponse.Success(data = mainPosts))
    }catch (e: Exception){
        context.res.setBody(ApiListResponse.Error(message = e.message.toString()))
    }
}
@Api(routeOverride = "readlatestposts")
suspend fun readLatestPosts(context: ApiContext){
    try {
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val latestPosts = context.data.getValue<MongoDB>().readLatestPosts(skip = skip)
        context.res.setBody(ApiListResponse.Success(data = latestPosts))
    }catch (e: Exception){
        context.res.setBody(ApiListResponse.Error(message = e.message.toString()))
    }
}

@Api(routeOverride = "readsponsoredposts")
suspend fun readSponsoredPosts(context: ApiContext){
    try {
        val sponsoredPosts = context.data.getValue<MongoDB>().readSponsoredPosts()
        context.res.setBody(ApiListResponse.Success(data = sponsoredPosts))
    }catch (e: Exception){
        context.res.setBody(ApiListResponse.Error(message = e.message.toString()))
    }
}

@Api(routeOverride = "readpopularposts")
suspend fun readPopularPosts(context: ApiContext){
    try {
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val popularPosts = context.data.getValue<MongoDB>().readPopularPosts(skip = skip)
        context.res.setBody(ApiListResponse.Success(data = popularPosts))
    }catch (e: Exception){
        context.res.setBody(ApiListResponse.Error(message = e.message.toString()))
    }
}

@Api(routeOverride = "deleteselectedposts")
suspend fun deleteSelectedPosts(context: ApiContext){
    try {
        val request = context.req.getBody<List<String>>()
        context.res.setBody(
            request?.let { context.data.getValue<MongoDB>().deleteSelectedPosts(ids = it) }
        )
    }catch (e: Exception){
        context.res.setBody(
            data = e.message.toString()
        )
    }
}

@Api(routeOverride = "searchposts")
suspend fun searchPostsByTitle(context: ApiContext){
    try {
        val query = context.req.params[QUERY_PARAM] ?: ""
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val request = context.data.getValue<MongoDB>().searchPostsByTitle(
            query = query,
            skip = skip
        )
        context.res.setBody(
            data = ApiListResponse.Success(data = request)
        )
    }catch (e: Exception) {
        context.res.setBody(
            data = ApiListResponse.Error(message = e.message.toString())
        )
    }
}

@Api(routeOverride = "searchpostsbycategory")
suspend fun searchPostsByCategory(context: ApiContext){
    try {
        val category = Category.valueOf(context.req.params[CATEGORY_PARAM] ?: Category.Programming.name)
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val request = context.data.getValue<MongoDB>().searchPostsByCategory(
            category = category,
            skip = skip
        )
        context.res.setBody(
            data = ApiListResponse.Success(data = request)
        )
    }catch (e: Exception) {
        context.res.setBody(
            data = ApiListResponse.Error(message = e.message.toString())
        )
    }
}

@Api(routeOverride = "readselectedpost")
suspend fun readSelectedPost(context: ApiContext){
    val postId = context.req.params[POST_ID_PARAM]
    if(!postId.isNullOrEmpty()){
        try {
            val selectedPost = context.data.getValue<MongoDB>().readSelectedPost(id = postId)
            context.res.setBody(
                data = ApiResponse.Success(data = selectedPost)
            )
        }catch (e: Exception){
            context.res.setBody(
                data = ApiResponse.Error(e.message.toString())
            )
        }
    }else{
        context.res.setBody(
            data = ApiResponse.Error("Selected Post does not exist!")
        )
    }
}

inline fun <reified T> Response.setBody(data: T) {
    setBodyText(Json.encodeToString(data))
}

inline fun <reified T> Request.getBody(): T? {
    return body?.decodeToString()?.let { return Json.decodeFromString(it) }
}