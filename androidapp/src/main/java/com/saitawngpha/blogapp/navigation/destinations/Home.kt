package com.saitawngpha.blogapp.navigation.destinations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.saitawngpha.blogapp.models.Category
import com.saitawngpha.blogapp.navigation.Screen
import com.saitawngpha.blogapp.screens.home.HomeScreen
import com.saitawngpha.blogapp.screens.home.HomeViewModel

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */

fun NavGraphBuilder.homeRoute(
    onCategorySelected: (Category) -> Unit,
    onPostClick: (String) -> Unit
) {
    composable( route = Screen.Home.route){
        val viewModel: HomeViewModel = viewModel()
        var query by remember { mutableStateOf("") }
        var searchBarOpened by remember { mutableStateOf(false) }
        var active by remember { mutableStateOf(false) }
        HomeScreen(
            posts = viewModel.allPosts.value,
            query = query,
            active = active,
            onActiveChange = { active = it},
            onQueryChange = {query = it},
            searchPosts = viewModel.searchPosts.value,
            searchBarOpened = searchBarOpened,
            onSearchBarChange = { opened ->
                searchBarOpened = opened
                if(!opened){
                    query = ""
                    active = false
                    viewModel.resetSearchedPost()
                }
            },
            onSearch = viewModel::searchPostsByTitle,
            onCategorySelect = onCategorySelected,
            onPostClick = onPostClick
        )
    }
}