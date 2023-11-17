package com.saitawngpha.blogapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saitawngpha.blogapp.navigation.Screen
import com.saitawngpha.blogapp.screens.details.DetailsScreen
import com.saitawngpha.blogapp.utils.Constants.POST_ID_ARGUMENT
import com.saitawngpha.blogmultiplatform.models.Constants

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */

fun NavGraphBuilder.detailsRoute(
    onBackPress: () -> Unit
){
    composable(
        route = Screen.Details.route,
        arguments = listOf(navArgument(name = POST_ID_ARGUMENT){
            type = NavType.StringType
        })
    ){
        val postId = it.arguments?.getString(POST_ID_ARGUMENT)
        DetailsScreen(
            url = "http://192.168.8.143:8080/posts/post?postId=$postId&${Constants.SHOW_SECTIONS_PARAM}=false",
            onBackPress = onBackPress
        )
    }
}
