package com.saitawngpha.blogapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */

@Composable
fun EmptyUi(
    loading: Boolean = false,
    hideMessage: Boolean = false,
    message: String = "No post to show."
) {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(loading){
            CircularProgressIndicator()
        }else{
            if(!hideMessage){
                Text(text = message)
            }
        }
    }
}