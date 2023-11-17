package com.saitawngpha.blogmultiplatform.pages.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.saitawngpha.blogmultiplatform.components.CategoryNavigationItems
import com.saitawngpha.blogmultiplatform.components.LoadingIndicator
import com.saitawngpha.blogmultiplatform.components.OverflowSidePanel
import com.saitawngpha.blogmultiplatform.models.ApiListResponse
import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Constants
import com.saitawngpha.blogmultiplatform.models.Constants.CATEGORY_PARAM
import com.saitawngpha.blogmultiplatform.models.Constants.POST_PER_PAGE
import com.saitawngpha.blogmultiplatform.models.Constants.QUERY_PARAM
import com.saitawngpha.blogmultiplatform.models.PostWithoutDetails
import com.saitawngpha.blogmultiplatform.navigation.Screen
import com.saitawngpha.blogmultiplatform.sections.FooterSection
import com.saitawngpha.blogmultiplatform.sections.HeaderSection
import com.saitawngpha.blogmultiplatform.sections.PostsSection
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.saitawngpha.blogmultiplatform.util.Id
import com.saitawngpha.blogmultiplatform.util.Res
import com.saitawngpha.blogmultiplatform.util.fetchLatestPosts
import com.saitawngpha.blogmultiplatform.util.searchPostsByCategory
import com.saitawngpha.blogmultiplatform.util.searchPostsByTitle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement

/**
 * Created by တွင်ႉၾႃႉ on 15/11/2023.
 */

@Page(routeOverride = "query")
@Composable
fun SearchPage() {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    var apiResponse by remember { mutableStateOf<ApiListResponse>(ApiListResponse.Idel) }
    var overFlowMenuOpened by remember { mutableStateOf(false) }
    var postsToSkip by remember { mutableStateOf(0) }
    var showMorePosts by remember { mutableStateOf(false) }
    val searchPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    val hasCategoryParam = remember(key1 = context.route) {
        context.route.params.containsKey(CATEGORY_PARAM)
    }
    val hasQueryParam = remember(key1 = context.route) {
        context.route.params.containsKey(QUERY_PARAM)
    }
    val value = remember(key1 = context.route) {
        if(hasCategoryParam){
            context.route.params.getValue(CATEGORY_PARAM)
        } else if (hasQueryParam){
            context.route.params.getValue(QUERY_PARAM)
        } else {
            ""
        }
    }

    LaunchedEffect(key1 = context.route){
        (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value = ""
        showMorePosts = false
        postsToSkip = 0
        if(hasCategoryParam){
             searchPostsByCategory(
                 category = runCatching { Category.valueOf(value) }.getOrElse { Category.Programming } ,
                 skip = postsToSkip,
                 onSuccess = {
                     apiResponse = it
                     if(it is ApiListResponse.Success) {
                         searchPosts.clear()
                         searchPosts.addAll(it.data)
                         postsToSkip += POST_PER_PAGE
                         if(it.data.size >= POST_PER_PAGE) showMorePosts = true
                     }
                 },
                 onError = {}
             )
        }else if(hasQueryParam){
            (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value = value
            searchPostsByTitle(
                query = value ,
                skip = postsToSkip,
                onSuccess = {
                    apiResponse = it
                    if(it is ApiListResponse.Success) {
                        searchPosts.clear()
                        searchPosts.addAll(it.data)
                        postsToSkip += POST_PER_PAGE
                        if(it.data.size >= POST_PER_PAGE) showMorePosts = true
                    }
                },
                onError = {}
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(overFlowMenuOpened){
            OverflowSidePanel(onMenuClosed = {
                overFlowMenuOpened = false
            },
                content = {
                    CategoryNavigationItems(
                        selectedCategory = if(hasCategoryParam) runCatching { Category.valueOf(value) }
                            .getOrElse { Category.Programming }
                        else null,
                        vertical = true
                    )
                }
            )
        }

        HeaderSection(
            breakpoint = breakpoint,
            selectedCategory = if(hasCategoryParam) runCatching { Category.valueOf(value) }
                .getOrElse { Category.Programming }
            else null,
            logo = Res.Image.logo,
            onMenuOpen = { overFlowMenuOpened = true }
        )

        if(apiResponse is ApiListResponse.Success){
            if(hasCategoryParam){
                SpanText(
                    modifier = Modifier
                        .fontWeight(FontWeight.Medium)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(36.px)
                        .margin(top = 100.px, bottom = 40.px)
                        .textAlign(TextAlign.Center),
                    text = value.ifEmpty  { Category.Programming.name }
                )
            }

            PostsSection(
                breakpoint = breakpoint,
                posts = searchPosts,
                showMoveVisibility = showMorePosts,
                onShowMore = {
                    scope.launch {
                        if (hasCategoryParam){
                            searchPostsByCategory(
                                skip = postsToSkip,
                                category = runCatching { Category.valueOf(value) }.getOrElse { Category.Programming },
                                onSuccess = { response ->
                                    if (response is ApiListResponse.Success){
                                        if(response.data.isNotEmpty()){
                                            if(response.data.size > POST_PER_PAGE){
                                                showMorePosts = false
                                            }
                                            searchPosts.addAll(response.data)
                                            postsToSkip += POST_PER_PAGE
                                        }else{
                                            showMorePosts = false
                                        }
                                    }
                                },
                                onError = {}
                            )
                        }else if(hasQueryParam){
                            searchPostsByTitle(
                                skip = postsToSkip,
                                query = value,
                                onSuccess = { response ->
                                    if (response is ApiListResponse.Success){
                                        if(response.data.isNotEmpty()){
                                            if(response.data.size > POST_PER_PAGE){
                                                showMorePosts = false
                                            }
                                            searchPosts.addAll(response.data)
                                            postsToSkip += POST_PER_PAGE
                                        }else{
                                            showMorePosts = false
                                        }
                                    }
                                },
                                onError = {}
                            )
                        }
                    }
                },
                onClick = {
                    context.router.navigateTo(Screen.PostPage.getPost(it))
                }
            )
        }else{
            //loading
            LoadingIndicator()
        }

        FooterSection()
    }
}