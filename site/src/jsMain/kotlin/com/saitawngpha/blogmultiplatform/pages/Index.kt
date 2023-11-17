package com.saitawngpha.blogmultiplatform.pages

import androidx.compose.runtime.*
import com.saitawngpha.blogmultiplatform.components.CategoryNavigationItems
import com.saitawngpha.blogmultiplatform.components.OverflowSidePanel
import com.saitawngpha.blogmultiplatform.models.ApiListResponse
import com.saitawngpha.blogmultiplatform.models.ApiResponse
import com.saitawngpha.blogmultiplatform.models.Constants.POST_PER_PAGE
import com.saitawngpha.blogmultiplatform.models.PostWithoutDetails
import com.saitawngpha.blogmultiplatform.navigation.Screen
import com.saitawngpha.blogmultiplatform.sections.FooterSection
import com.saitawngpha.blogmultiplatform.sections.HeaderSection
import com.saitawngpha.blogmultiplatform.sections.MainSection
import com.saitawngpha.blogmultiplatform.sections.NewsletterSection
import com.saitawngpha.blogmultiplatform.sections.PostsSection
import com.saitawngpha.blogmultiplatform.sections.SponsoredPostsSection
import com.saitawngpha.blogmultiplatform.util.fetchLatestPosts
import com.saitawngpha.blogmultiplatform.util.fetchMainPosts
import com.saitawngpha.blogmultiplatform.util.fetchPopularPosts
import com.saitawngpha.blogmultiplatform.util.fetchSponsoredPosts
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch

@Page
@Composable
fun HomePage() {
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val breakpoint = rememberBreakpoint()
    var overFlowMenuOpened by remember { mutableStateOf(false) }
    var mainPosts by remember { mutableStateOf<ApiListResponse>(ApiListResponse.Idel) }
    var latestPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    var sponsoredPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    var popularPosts = remember { mutableStateListOf<PostWithoutDetails>() }
    var latestPostsToSkip by remember { mutableStateOf(0) }
    var popularPostsToSkip by remember { mutableStateOf(0) }
    var showMoreLatest by remember { mutableStateOf(false) }
    var showMorePopular by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        fetchMainPosts(
            onSuccess = { data ->
                mainPosts = data },
            onError = {println(it)}
        )

        fetchLatestPosts(
            skip = latestPostsToSkip,
            onSuccess = {
                if(it is ApiListResponse.Success) {
                    latestPosts.addAll(it.data)
                    latestPostsToSkip += POST_PER_PAGE
                    if(it.data.size >= POST_PER_PAGE) showMoreLatest = true
                }
            },
            onError = {}
        )

        fetchSponsoredPosts(
            onSuccess = { response ->
                if(response is ApiListResponse.Success) {
                    sponsoredPosts.addAll(response.data)
                }
            },
            onError = {}
        )

        fetchPopularPosts(
            skip = popularPostsToSkip,
            onSuccess = {
                if(it is ApiListResponse.Success) {
                    popularPosts.addAll(it.data)
                    popularPostsToSkip += POST_PER_PAGE
                    if(it.data.size >= POST_PER_PAGE) showMorePopular = true
                }
            },
            onError = {}
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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

        //header
        HeaderSection(
            breakpoint = breakpoint,
            onMenuOpen = {
                overFlowMenuOpened = true
            }
        )

        //main section
        MainSection(
            breakpoint = breakpoint,
            posts = mainPosts,
            onClick = {context.router.navigateTo(Screen.PostPage.getPost(id = it))}
        )

        //Latest Posts
        PostsSection(
            breakpoint = breakpoint,
            posts = latestPosts,
            title = "Latest Posts",
            showMoveVisibility = showMoreLatest,
            onShowMore = {
                scope.launch {
                    fetchLatestPosts(
                        skip = latestPostsToSkip,
                        onSuccess = { response ->
                            if (response is ApiListResponse.Success){
                                if(response.data.isNotEmpty()){
                                    if(response.data.size > POST_PER_PAGE){
                                        showMoreLatest = false
                                    }
                                    latestPosts.addAll(response.data)
                                    latestPostsToSkip += POST_PER_PAGE
                                }else{
                                    showMoreLatest = false
                                }
                            }
                        },
                        onError = {}
                    )
                }
            },
            onClick = { context.router.navigateTo(Screen.PostPage.getPost(id = it)) }
        )

        //sponsored posts
        SponsoredPostsSection(
            breakpoint = breakpoint,
            posts = sponsoredPosts,
            onClick = { context.router.navigateTo(Screen.PostPage.getPost(id = it)) }
        )

        //Popular Posts
        PostsSection(
            breakpoint = breakpoint,
            posts = popularPosts,
            title = "Popular Posts",
            showMoveVisibility = showMorePopular,
            onShowMore = {
                scope.launch {
                    fetchPopularPosts(
                        skip = popularPostsToSkip,
                        onSuccess = { response ->
                            if (response is ApiListResponse.Success){
                                if(response.data.isNotEmpty()){
                                    if(response.data.size > POST_PER_PAGE){
                                        showMorePopular = false
                                    }
                                    popularPosts.addAll(response.data)
                                    popularPostsToSkip += POST_PER_PAGE
                                }else{
                                    showMorePopular = false
                                }
                            }
                        },
                        onError = {}
                    )
                }
            },
            onClick = { context.router.navigateTo(Screen.PostPage.getPost(id = it)) }
        )

        //Newsletter
        NewsletterSection(breakpoint = breakpoint)

        //fotter
        FooterSection()
    }
}
