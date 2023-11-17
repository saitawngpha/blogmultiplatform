package com.saitawngpha.blogapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.saitawngpha.blogapp.components.NavigationDrawer
import com.saitawngpha.blogapp.components.PostCardsView
import com.saitawngpha.blogapp.models.Category
import com.saitawngpha.blogapp.models.Post
import com.saitawngpha.blogapp.utils.RequestState
import kotlinx.coroutines.launch

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    posts: RequestState<List<Post>>,
    query: String,
    searchPosts: RequestState<List<Post>>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onCategorySelect: (Category) -> Unit,
    onSearchBarChange: (Boolean) -> Unit,
    searchBarOpened: Boolean,
    onPostClick: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    NavigationDrawer(
        drawerState = drawerState,
        onCategorySelect = {
            scope.launch {
                drawerState.close()
            }
            onCategorySelect(it)
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Blog")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Drawer Icon"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onSearchBarChange(true)
                            onActiveChange(true)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )

                if(searchBarOpened){
                    SearchBar(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearch = onSearch,
                        active = active,
                        onActiveChange = onActiveChange,
                        placeholder = { Text(text = "Search here...")},
                        leadingIcon = {
                            IconButton(onClick = {
                                onSearchBarChange(false)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "ArrowBack Icon",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                onQueryChange("")
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Icon",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    ) {
                        PostCardsView(
                            posts = searchPosts,
                            topMargin = 12.dp,
                            onPostClick = onPostClick
                        )
                    }
                }
            }
        ){
            PostCardsView(
                posts = posts,
                topMargin = it.calculateTopPadding(),
                hideMessage = true,
                onPostClick = onPostClick
            )
        }
    }
}