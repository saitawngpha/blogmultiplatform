package com.saitawngpha.blogapp.screens.category

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.saitawngpha.blogapp.components.PostCardsView
import com.saitawngpha.blogapp.models.Category
import com.saitawngpha.blogapp.models.Post
import com.saitawngpha.blogapp.utils.RequestState

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    posts: RequestState<List<Post>>,
    category: Category,
    onBackPress: () -> Unit,
    onPostClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = category.name) },
                navigationIcon = {
                   IconButton(onClick = { onBackPress() }) {
                       Icon(
                           imageVector = Icons.Default.ArrowBack,
                           contentDescription = "Backarrow Icon"
                       )
                   }
                }
                )
        }
    ){
        PostCardsView(
            posts = posts,
            topMargin = it.calculateTopPadding(),
            onPostClick = onPostClick
        )
    }
}