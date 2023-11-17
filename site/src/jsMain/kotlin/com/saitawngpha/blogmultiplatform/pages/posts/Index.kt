package com.saitawngpha.blogmultiplatform.pages.posts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.saitawngpha.blogmultiplatform.components.CategoryNavigationItems
import com.saitawngpha.blogmultiplatform.components.ErrorView
import com.saitawngpha.blogmultiplatform.components.LoadingIndicator
import com.saitawngpha.blogmultiplatform.components.OverflowSidePanel
import com.saitawngpha.blogmultiplatform.models.ApiResponse
import com.saitawngpha.blogmultiplatform.models.Constants
import com.saitawngpha.blogmultiplatform.models.Constants.POST_ID_PARAM
import com.saitawngpha.blogmultiplatform.models.Post
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.sections.FooterSection
import com.saitawngpha.blogmultiplatform.sections.HeaderSection
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.saitawngpha.blogmultiplatform.util.Id
import com.saitawngpha.blogmultiplatform.util.Res
import com.saitawngpha.blogmultiplatform.util.fetchSelectedPost
import com.saitawngpha.blogmultiplatform.util.parseDateString
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextOverflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textOverflow
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get
import com.saitawngpha.blogmultiplatform.models.Constants.SHOW_SECTIONS_PARAM

/**
 * Created by တွင်ႉၾႃႉ on 15/11/2023.
 */

@Page(routeOverride = "post")
@Composable
fun PostPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    var overFlowMenuOpened by remember { mutableStateOf(false) }
    var showSections by remember { mutableStateOf(true) }
    val context = rememberPageContext()
    var apiResponse by remember { mutableStateOf<ApiResponse>(ApiResponse.Idel) }
    val hasPostIdParam = remember(key1 = context.route) {
        context.route.params.containsKey(POST_ID_PARAM)
    }
    LaunchedEffect(key1 = context.route){
       showSections = if(context.route.params.containsKey(SHOW_SECTIONS_PARAM)){
            context.route.params.getValue(SHOW_SECTIONS_PARAM).split("=")
                .last().toBoolean()
        }else true

        if(hasPostIdParam){
            val postId = context.route.params.getValue(POST_ID_PARAM)
            apiResponse = fetchSelectedPost(id = postId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if(overFlowMenuOpened){
            OverflowSidePanel(onMenuClosed = {
                overFlowMenuOpened = false
            },
                content = {
                    CategoryNavigationItems(vertical = true)
                }
            )
        }

        if(showSections){
            HeaderSection(
                breakpoint = breakpoint,
                logo = Res.Image.logo,
                onMenuOpen = {
                    overFlowMenuOpened = true
                }
            )
        }

        when(apiResponse){
            is ApiResponse.Idel -> {
                LoadingIndicator()
            }
            is ApiResponse.Success -> {
                PostContent(
                    post = (apiResponse as ApiResponse.Success).data,
                    breakpoint = breakpoint
                )
                scope.launch {
                    delay(50)
                    try {
                        js("hljs.highlightAll()") as Unit
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }
            }
            is ApiResponse.Error -> {
                ErrorView(message = (apiResponse as ApiResponse.Error).message)
            }
        }

        if (showSections){
            FooterSection()
        }
    }

}

@Composable
fun PostContent(
    post: Post,
    breakpoint: Breakpoint) {

    LaunchedEffect(post){
        (document.getElementById(Id.postContent) as HTMLDivElement).innerHTML = post.content
    }

    Column(
        modifier = Modifier
            .margin(top = 50.px, bottom = 200.px)
            .padding(leftRight = 24.px)
            .fillMaxWidth()
            .maxWidth(800.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpanText(
            modifier = Modifier
                .fillMaxWidth()
                .color(Theme.HalfBlack.rgb)
                .fontFamily(FONT_FAMILY)
                .fontSize(14.px)
                    ,
            text = "Author: ${localStorage["username"]}"
        )
        SpanText(
            modifier = Modifier
                .fillMaxWidth()
                .color(Theme.HalfBlack.rgb)
                .fontFamily(FONT_FAMILY)
                .fontSize(14.px)
            ,
            text = "Updated: ${post.date.toLong().parseDateString()}"
        )

        SpanText(
            modifier = Modifier
                .fillMaxWidth()
                .margin(bottom = 20.px)
                .color(Colors.Black)
                .fontFamily(FONT_FAMILY)
                .fontSize(40.px)
                .fontWeight(FontWeight.Bold)
                .overflow(Overflow.Hidden)
                .textOverflow(TextOverflow.Ellipsis)
                .styleModifier {
                    property("display", "-webkit-box")
                    property("-webkit-line-clamp", "2")
                    property("line-clamp", "2")
                    property("-webkit-box-orient", "vertical")
                }
            ,
            text = post.title
        )

        Image(
            modifier = Modifier
                .margin(bottom = 40.px)
                .fillMaxWidth()
                .height(
                    if(breakpoint <= Breakpoint.SM) 250.px
                    else if (breakpoint <= Breakpoint.MD) 400.px
                    else 600.px),
            src = post.thumbnail
        )

        Div(
            attrs = Modifier
                .id(Id.postContent)
                .fontFamily(FONT_FAMILY)
                .fillMaxWidth()
                .toAttrs()
        )
    }
}