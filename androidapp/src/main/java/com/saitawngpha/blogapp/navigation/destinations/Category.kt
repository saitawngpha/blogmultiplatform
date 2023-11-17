package com.saitawngpha.blogapp.navigation.destinations

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saitawngpha.blogapp.models.Category
import com.saitawngpha.blogapp.navigation.Screen
import com.saitawngpha.blogapp.screens.category.CategoryScreen
import com.saitawngpha.blogapp.screens.category.CategoryViewModel
import com.saitawngpha.blogapp.utils.Constants.CATEGORY_ARGUMENT

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */

fun NavGraphBuilder.categoryRoute(
    onBackPress: () -> Unit,
    onPostClick: (String) -> Unit
){
    composable(
        route = Screen.Category.route,
        arguments = listOf(navArgument(name = CATEGORY_ARGUMENT){
            type = NavType.StringType
        })
    ){
        val viewModel: CategoryViewModel = viewModel()
        val selectedCategory = it.arguments?.getString(CATEGORY_ARGUMENT) ?: Category.Programming.name
        CategoryScreen(
            posts = viewModel.categoryPosts.value,
            category = Category.valueOf(selectedCategory),
            onBackPress = onBackPress,
            onPostClick = onPostClick
        )
    }
}