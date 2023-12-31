package com.saitawngpha.blogmultiplatform.sections

import androidx.compose.runtime.Composable
import com.saitawngpha.blogmultiplatform.components.PostPreview
import com.saitawngpha.blogmultiplatform.models.ApiListResponse
import com.saitawngpha.blogmultiplatform.models.PostWithoutDetails
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.util.Constants.PAGE_WIDTH
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

/**
 * Created by တွင်ႉၾႃႉ on 11/11/2023.
 */

@Composable
fun MainSection(
    breakpoint: Breakpoint,
    posts: ApiListResponse,
    onClick: (String) -> Unit
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Theme.Secondary.rgb),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(PAGE_WIDTH.px),
            contentAlignment = Alignment.Center
        ){
            when(posts){
                is ApiListResponse.Idel -> {}
                is ApiListResponse.Success -> {
                    MainPosts(
                        breakpoint = breakpoint,
                        posts = posts.data,
                        onClick = onClick
                    )
                }
                is ApiListResponse.Error -> {}
            }
        }
    }
}

@Composable
fun MainPosts(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>,
    onClick: (String) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(
                if(breakpoint > Breakpoint.MD) 80.percent
                else 90.percent
            )
            .margin(topBottom = 50.px),

    ){
        if(breakpoint == Breakpoint.XL){
            PostPreview(
                post = posts.first(),
                darkTheme = true,
                thumbnailHeight = 640.px,
                onClick = {onClick(posts.first().id)}
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(80.percent)
                    .margin(left = 20.px )
            ){
                posts.drop(1).forEach { postWithoutDetails ->
                    PostPreview(
                        post = postWithoutDetails,
                        darkTheme = true,
                        vertical = false,
                        thumbnailHeight = 200.px,
                        titleMaxLength = 1,
                        onClick = {onClick(postWithoutDetails.id)}
                    )
                }
            }
        }else if (breakpoint >= Breakpoint.LG){
            Box(
                modifier = Modifier.margin(right = 10.px)
            ) {
                PostPreview(
                    post = posts.first(),
                    darkTheme = true,
                    onClick = {onClick(posts.first().id)}
                )
            }

            Box(modifier = Modifier.margin(left = 10.px)){
                PostPreview(
                    post = posts[1],
                    darkTheme = true,
                    onClick = {onClick(posts[1].id)}
                )
            }

        }else {
            PostPreview(
                post = posts.first(),
                darkTheme = true,
                thumbnailHeight = 640.px,
                onClick = {onClick(posts.first().id)}
            )
        }
    }
}