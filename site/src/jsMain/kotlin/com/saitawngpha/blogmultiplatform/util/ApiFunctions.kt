package com.saitawngpha.blogmultiplatform.util

import com.saitawngpha.blogmultiplatform.models.ApiListResponse
import com.saitawngpha.blogmultiplatform.models.ApiResponse
import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Constants.AUTHOR_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.CATEGORY_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.POST_ID_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.QUERY_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.SKIP_PARAM
import com.saitawngpha.blogmultiplatform.models.Newsletter
import com.saitawngpha.blogmultiplatform.models.Post
import com.saitawngpha.blogmultiplatform.models.RandomJoke
import com.saitawngpha.blogmultiplatform.models.User
import com.saitawngpha.blogmultiplatform.models.UserWithoutPass
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.http.http
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

/**
 * Created by တွင်ႉၾႃႉ on 02/11/2023.
 */

suspend fun checkUserExistence(user: User): UserWithoutPass? {
    return try {
        window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )?.decodeToString().parseData()
    } catch (e: Exception) {
        println(e.message)
        null
    }
}

suspend fun checkUserId(id: String): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "checkuserid",
            body = Json.encodeToString(id).encodeToByteArray()
        )?.decodeToString().parseData()
    } catch (e: Exception) {
        println(e.message.toString())
        false
    }
}

suspend fun fetchRandomJoke(onComplete: (RandomJoke) -> Unit) {
    val date = localStorage["date"]
    if (date != null) {
        val different = (Date.now() - date.toDouble())
        val dateHasPassed = different >= 86400000
        if (dateHasPassed) {
            try {
                //get request to get random joke
                val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
                onComplete(result.parseData())
                //save joke to local storage
                localStorage["date"] = Date.now().toString()
                localStorage["joke"] = result
            } catch (e: Exception) {
                onComplete(RandomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }

        } else {
            try {
                localStorage["joke"]?.parseData<RandomJoke>()
                    ?.let { onComplete(it) }
            } catch (e: Exception) {
                onComplete(RandomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }
        }
    } else {
        //crate new date
        //localStorage["date"] = Date.now().toString()
        try {
            //get request to get random joke
            val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
            onComplete(result.parseData())
            //save joke to local storage
            localStorage["date"] = Date.now().toString()
            localStorage["joke"] = result
        } catch (e: Exception) {
            onComplete(RandomJoke(id = -1, joke = e.message.toString()))
            println(e.message)
        }
    }
}

suspend fun addPost(post: Post): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "addpost",
            body = Json.encodeToString(post).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}

suspend fun updatePost(post: Post) : Boolean{
   return try {
        window.api.tryPost(
            apiPath = "updatepost",
            body = Json.encodeToString(post).encodeToByteArray()
        )?.decodeToString().toBoolean()
    }catch (e: Exception){
        println(e.message)
       false
    }
}

suspend fun fetchMyPosts(
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "readmyposts?${SKIP_PARAM}=$skip&${AUTHOR_PARAM}=${localStorage["username"]}"
        )?.decodeToString()
        onSuccess(
            result.parseData()
        )

    } catch (e: Exception) {
        println(e)
        onError(e)
    }
}

suspend fun fetchMainPosts(
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try{
        val result = window.api.tryGet(
            apiPath = "fetchmainposts"
        )?.decodeToString()
        println(result)
        onSuccess(result.parseData())
    }catch (e: Exception){
        println(e.message)
        onError(e)
    }
}

suspend fun fetchLatestPosts(
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try{
        val result = window.api.tryGet(
            apiPath = "readlatestposts?skip=$skip"
        )?.decodeToString()
        onSuccess(result.parseData())
    }catch (e: Exception){
        println(e.message)
        onError(e)
    }
}

suspend fun fetchSponsoredPosts(
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try{
        val result = window.api.tryGet(
            apiPath = "readsponsoredposts"
        )?.decodeToString()
        onSuccess(result.parseData())
    }catch (e: Exception){
        println(e.message)
        onError(e)
    }
}

suspend fun fetchPopularPosts(
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try{
        val result = window.api.tryGet(
            apiPath = "readpopularposts?skip=$skip"
        )?.decodeToString()
        onSuccess(result.parseData())
    }catch (e: Exception){
        println(e.message)
        onError(e)
    }
}

suspend fun deleteSelectedPosts(ids: List<String>): Boolean {
    return try {
        val result = window.api.tryPost(
            apiPath = "deleteselectedposts",
            body = Json.encodeToString(ids).encodeToByteArray()
        )?.decodeToString()

        result.toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}

suspend fun searchPostsByTitle(
    query: String,
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "searchposts?${QUERY_PARAM}=$query&${SKIP_PARAM}=$skip"
        )?.decodeToString()
        onSuccess(
            result.parseData()
        )
    } catch (e: Exception) {
        println(e.message)
        onError(e)
    }
}

suspend fun searchPostsByCategory(
    category: Category,
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "searchpostsbycategory?${CATEGORY_PARAM}=${category.name}&${SKIP_PARAM}=$skip"
        )?.decodeToString()
        onSuccess(
            result.parseData()
        )
    } catch (e: Exception) {
        println(e.message)
        onError(e)
    }
}

suspend fun fetchSelectedPost(id: String): ApiResponse {
    return try {
        val result = window.api.tryGet(
            apiPath = "readselectedpost?${POST_ID_PARAM}=$id"
        )?.decodeToString()
        result?.let {
            Json.decodeFromString<ApiResponse>(it)
        } ?: ApiResponse.Error("Result is null!")

    } catch (e: Exception) {
        println(e)
        ApiResponse.Error(e.message.toString())
    }
}

suspend fun subscribeToNewsletter(newsletter: Newsletter): String {
    return window.api.tryPost(
        apiPath = "subscribe",
        body = Json.encodeToString(newsletter).encodeToByteArray()
    )?.decodeToString().toString().replace("\"", "")
}

inline fun <reified T> String?.parseData(): T {
    return Json.decodeFromString(this.toString())
}