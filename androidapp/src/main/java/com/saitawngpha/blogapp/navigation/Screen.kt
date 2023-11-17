package com.saitawngpha.blogapp.navigation

import com.saitawngpha.blogapp.utils.Constants.CATEGORY_ARGUMENT
import com.saitawngpha.blogapp.utils.Constants.POST_ID_ARGUMENT
import com.saitawngpha.blogapp.models.Category as PostCategory

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */
sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Category: Screen(route = "category_screen/{$CATEGORY_ARGUMENT}"){
        fun passCategory(category: PostCategory) = "category_screen/${category.name}"
    }
    object Details: Screen(route = "details_screen/{$POST_ID_ARGUMENT}"){
        fun passPostId(id: String) = "details_screen/${id}"
    }
}
