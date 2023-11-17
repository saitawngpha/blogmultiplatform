package com.saitawngpha.blogapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saitawngpha.blogapp.models.Category
import com.saitawngpha.blogapp.navigation.destinations.categoryRoute
import com.saitawngpha.blogapp.navigation.destinations.detailsRoute
import com.saitawngpha.blogapp.navigation.destinations.homeRoute
import com.saitawngpha.blogapp.screens.category.CategoryScreen
import com.saitawngpha.blogapp.screens.category.CategoryViewModel
import com.saitawngpha.blogapp.screens.details.DetailsScreen
import com.saitawngpha.blogapp.screens.home.HomeScreen
import com.saitawngpha.blogapp.screens.home.HomeViewModel
import com.saitawngpha.blogmultiplatform.models.Constants.SHOW_SECTIONS_PARAM

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        homeRoute(
            onCategorySelected = {navController.navigate(Screen.Category.passCategory(it))},
            onPostClick = { postId ->
                navController.navigate(Screen.Details.passPostId(postId))
            }
        )

        categoryRoute(
            onPostClick = { postId ->
                navController.navigate(Screen.Details.passPostId(postId))
            },
            onBackPress = {navController.popBackStack()}
        )

        detailsRoute (
            onBackPress = { navController.popBackStack() }
        )


    }
}