package com.saitawngpha.blogmultiplatform.components

import androidx.compose.runtime.Composable
import com.saitawngpha.blogmultiplatform.models.PostWithoutDetails
import com.saitawngpha.blogmultiplatform.util.Constants
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.visibility
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
fun PostsView(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>,
    selectableMode: Boolean = false,
    title: String? = null,
    onSelect: (String) -> Unit = {},
    onDeSelect: (String) -> Unit = {},
    showMoreVisibility: Boolean,
    onShowMore: () -> Unit,
    onClick: (String) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxWidth(if(breakpoint > Breakpoint.MD)80.percent else 90.percent),
        verticalArrangement = Arrangement.Center
    ){
        if (title != null){
            SpanText(
                modifier = Modifier
                    .margin(bottom = 24.px)
                    .fontWeight(FontWeight.Bold)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(18.px),
                text = title
            )
        }
        SimpleGrid(
            modifier = Modifier.fillMaxWidth(),
            numColumns = numColumns(base = 1, sm = 2, md = 3, lg = 4)
        ){
            posts.forEach {
                PostPreview(
                    post = it,
                    selectableMode = selectableMode,
                    onSelect = onSelect,
                    onDeSelect = onDeSelect,
                    onClick = onClick
                )
            }
        }
        SpanText(
            modifier = Modifier
                .fillMaxWidth()
                .margin(topBottom = 50.px)
                .textAlign(TextAlign.Center)
                .fontFamily(Constants.FONT_FAMILY)
                .fontSize(16.px)
                .fontWeight(FontWeight.Medium)
                .cursor(Cursor.Pointer)
                .onClick { onShowMore() }
                .visibility(if(showMoreVisibility) Visibility.Visible else Visibility.Hidden)
            ,
            text = "Show more"
        )
    }
}