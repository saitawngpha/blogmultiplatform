package com.saitawngpha.blogmultiplatform.navigation

import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Constants.CATEGORY_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.POST_ID_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.QUERY_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.UPDATED_PARAM


/**
 * Created by တွင်ႉၾႃႉ on 03/11/2023.
 */
sealed class Screen(val route: String){
    object AdminHome: Screen("/admin/")
    object AdminLogin: Screen("/admin/login")
    object AdminCreate: Screen("/admin/create"){
        fun passPostId(id: String) = "/admin/create?${POST_ID_PARAM}=$id"
    }
    object AdminPosts: Screen("/admin/myposts"){
        fun searchByTitle(query: String) = "/admin/myposts?${QUERY_PARAM}=$query"
    }
    object AdminSuccess: Screen("/admin/success"){
        fun postUpdated() = "/admin/success?${UPDATED_PARAM}=true"
    }

    object HomePage: Screen("/")

    object SearchPage: Screen("/search/query"){
        fun searchByCategory(category: Category) = "/search/query?${CATEGORY_PARAM}=${category.name}"
        fun searchByTitle(query: String) = "/search/query?${QUERY_PARAM}=${query}"
    }

    object PostPage: Screen("/posts/post"){
        fun getPost(id: String) = "/posts/post?$POST_ID_PARAM=$id"
    }
}
