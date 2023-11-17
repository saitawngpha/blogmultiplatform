package com.saitawngpha.blogmultiplatform.sections

import androidx.compose.runtime.Composable
import com.saitawngpha.blogmultiplatform.components.PostPreview
import com.saitawngpha.blogmultiplatform.models.PostWithoutDetails
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.saitawngpha.blogmultiplatform.util.Constants.PAGE_WIDTH
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaTag
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

/**
 * Created by တွင်ႉၾႃႉ on 14/11/2023.
 */

@Composable
fun SponsoredPostsSection(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .margin(bottom = 50.px)
            .fillMaxWidth()
            .backgroundColor(Theme.LightGray.rgb),
        contentAlignment = Alignment.Center
    ){
        Box (
            modifier = Modifier
                .maxWidth(PAGE_WIDTH.px)
                .margin(topBottom = 50.px),
            contentAlignment = Alignment.TopCenter
        ){
            SponsoredPosts(
                breakpoint = breakpoint,
                posts = posts,
                onClick = onClick
            )
        }
    }
}

@Composable
fun SponsoredPosts(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(
                if (breakpoint > Breakpoint.MD) 80.percent
                else 90.percent
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.margin(bottom = 30.px),
            verticalAlignment = Alignment.CenterVertically
        ){
            FaTag(
                modifier = Modifier
                    .margin(right = 10.px)
                    .color(Theme.Sponsored.rgb)
                        ,
                size = IconSize.XL
            )

            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontSize(18.px)
                    .fontWeight(FontWeight.Medium)
                    .color(Theme.Sponsored.rgb),
                text = "Sponsored Posts"
            )
        }

        SimpleGrid(
            modifier = Modifier.fillMaxWidth(),
            numColumns = numColumns(base = 1, xl = 2)
        ){
            posts.forEach {post ->
                PostPreview(
                    modifier = Modifier.margin(right = 50.px),
                    post = post,
                    vertical = breakpoint < Breakpoint.MD,
                    titleMaxLength = 1,
                    titleColor = Theme.Sponsored.rgb,
                    thumbnailHeight = if(breakpoint >= Breakpoint.MD) 200.px else 300.px,
                    onClick = onClick

                )
            }
        }
    }
}