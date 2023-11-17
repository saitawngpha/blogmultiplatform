package com.saitawngpha.blogmultiplatform.sections

import androidx.compose.runtime.Composable
import com.saitawngpha.blogmultiplatform.components.PostsView
import com.saitawngpha.blogmultiplatform.models.PostWithoutDetails
import com.saitawngpha.blogmultiplatform.util.Constants.PAGE_WIDTH
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.px

/**
 * Created by တွင်ႉၾႃႉ on 14/11/2023.
 */

@Composable
fun PostsSection(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>,
    title: String? = null,
    showMoveVisibility: Boolean,
    onShowMore: () -> Unit,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .margin(topBottom = 50.px)
            .maxWidth(PAGE_WIDTH.px),
        contentAlignment = Alignment.TopCenter
    ) {
        PostsView(
            breakpoint = breakpoint,
            posts = posts,
            title = title,
            showMoreVisibility = showMoveVisibility,
            onShowMore = onShowMore,
            onClick = onClick
        )
    }
}