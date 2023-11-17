package com.saitawngpha.blogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.saitawngpha.blogapp.navigation.SetupNavGraph
import com.saitawngpha.blogapp.ui.theme.BlogMultiplatformTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlogMultiplatformTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
