package com.saitawngpha.blogmultiplatform.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.navigation.Screen
import com.saitawngpha.blogmultiplatform.styles.NavigationItemStyle
import com.saitawngpha.blogmultiplatform.util.Constants.COLLAPSED_PANEL_HEIGHT
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.saitawngpha.blogmultiplatform.util.Constants.SIDE_PANEL_WIDTH
import com.saitawngpha.blogmultiplatform.util.Id
import com.saitawngpha.blogmultiplatform.util.Res
import com.saitawngpha.blogmultiplatform.util.logout
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.dom.Path
import com.varabyte.kobweb.compose.dom.Svg
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

/**
 * Created by တွင်ႉၾႃႉ on 03/11/2023.
 */

@Composable
fun SidePanel(onMenuClick: () -> Unit) {
    val breakpoint = rememberBreakpoint()
    if(breakpoint > Breakpoint.MD){
        SidePanelInternal()
    }else{
        CollapsedSidePanel(onMenuClick = onMenuClick)
    }
}

@Composable
private fun SidePanelInternal() {
    Column(
        modifier = Modifier
            .padding(leftRight = 40.px, topBottom = 50.px)
            .width(SIDE_PANEL_WIDTH.px)
            .height(100.vh)
            .position(Position.Fixed )
            .backgroundColor(Theme.Secondary.rgb)
            .zIndex(9 )
          
    ) { 
        Image(
            modifier = Modifier
                .width(150.px)
                .margin(bottom = 60.px),
            src = Res.Image.logo,
            desc = "Logo"
        )

        NavigationItems()
    }
}

@Composable
fun NavigationItems() {
    val context = rememberPageContext()
    SpanText(
        modifier = Modifier
            .margin(bottom = 30.px )
            .fontFamily(FONT_FAMILY)
            .fontSize(14.px)
            .color(Theme.HalfWhite.rgb )
        ,
        text = "Dashboard"
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminHome.route,
        title = "Home",
        icon = Res.PathIcon.home,
        onClick = {
            context.router.navigateTo(Screen.AdminHome.route)
        }
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminCreate.route,
        title = "Create a Post",
        icon = Res.PathIcon.create,
        onClick = {
            context.router.navigateTo(Screen.AdminCreate.route)
        }
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminPosts .route,
        title = "All Posts",
        icon = Res.PathIcon.posts,
        onClick = {
            context.router.navigateTo(Screen.AdminPosts.route)
        }
    )
    NavigationItem(
        //modifier = Modifier.margin(bottom = 24.px),
        title = "Logout",
        icon = Res.PathIcon.logout,
        onClick = {
            logout()
            context.router.navigateTo(Screen.AdminLogin.route)
        }
    )
}

@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    title: String,
    icon: String,
    onClick:() -> Unit
) {
    Row(
         modifier = NavigationItemStyle.toModifier()
             .then(modifier)
             .cursor(Cursor.Pointer)
             .onClick { onClick()
                  },
        verticalAlignment = Alignment.CenterVertically
    ){
         VectorIcon(
              modifier = Modifier.margin(right = 10.px),
             selected = selected,
             pathData = icon,
         )
        SpanText(
            modifier = Modifier
                .id(Id.navigationText)
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .thenIf(
                    condition = selected,
                   other = Modifier.color(Theme.Primary.rgb)
                ),
            text = title,
        )
    }
}

@Composable
private fun VectorIcon(
    modifier: Modifier = Modifier,
    selected: Boolean,
    pathData: String
    ) {
     Svg(
          attrs = modifier
              .id(Id.svgParent)
              .width(24.px)
              .height(24.px)
              .toAttrs{
                  attr("viewBox", "0 0 24 24")
                  attr("fill", "none")
              }
     ){
       Path{
           attr("id", Id.vectorIcon)
           if (selected) {
               attr(name = "style", value = "stroke: ${Theme.Primary.hex}")
           }
           attr(name = "d", value = pathData)
           attr(name = "stroke-width", value = "2")
           attr(name = "stroke-linecap", value = "round")
           attr(name = "stroke-linejoin", value = "round")
       }
     }
}

@Composable
private fun CollapsedSidePanel(onMenuClick:() -> Unit) {
      Row(
          modifier = Modifier
              .fillMaxSize()
              .height(COLLAPSED_PANEL_HEIGHT.px)
              .padding(leftRight = 24.px)
              .backgroundColor(Theme.Secondary.rgb),
          verticalAlignment = Alignment.CenterVertically
      ) { 
           FaBars(
               modifier = Modifier
                   .margin(right = 24.px)
                   .color(Colors.White )
                   .cursor(Cursor.Pointer ) 
                   .onClick { onMenuClick() },
               size = IconSize.XL
           )
          
          Image(
              modifier = Modifier
                  .width(100.px )
                  ,
              src = Res.Image.logo,
              desc = "Image Logo"
          )
      }
}

@Composable
 fun OverflowSidePanel(
    onMenuClosed: () -> Unit,
    content: @Composable () -> Unit
    ) {
    val scope = rememberCoroutineScope( )
    val breakpoint = rememberBreakpoint()
    var translateX by remember { mutableStateOf((-100).percent) }
    var opacity by remember { mutableStateOf(0.percent) }
    val context = rememberPageContext()

    LaunchedEffect(key1 = breakpoint){
        translateX = 0.percent
        opacity = 100.percent
        if(breakpoint > Breakpoint.MD){
            scope.launch {
                translateX = (-100).percent
                opacity = 0.percent
                delay(500 )
                onMenuClosed()
            }
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(9 )
            .opacity(opacity )
            .transition(CSSTransition("opacity", duration = 300.ms))
            .backgroundColor(Theme.HalfBlack.rgb)
    ){
        Column(
            modifier = Modifier
                .padding(all = 24.px )
                .fillMaxSize()
                .width(if (breakpoint < Breakpoint.MD) 50.percent else 25.percent)
                .translateX(translateX )
                .transition(CSSTransition("translate", duration = 300.ms))
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .backgroundColor(Theme.Secondary.rgb )
        ) {
           Row(
               modifier = Modifier.margin(bottom = 60 .px, top = 24.px ),
               verticalAlignment = Alignment.CenterVertically
           ) {
               FaXmark(
                   modifier = Modifier
                       .margin(right = 20.px)
                       .color(Colors.White)
                       .cursor(Cursor.Pointer )
                       .onClick {
                           scope.launch {
                               translateX = (-100).percent
                               opacity = 0.percent
                               delay(500 )
                               onMenuClosed()
                           }
                       },
                   size = IconSize.LG
               )

               Image(
                   modifier = Modifier.width(100.px)
                       .onClick { context.router.navigateTo(Screen.HomePage.route) }
                       .cursor(Cursor.Pointer),
                   src = Res.Image.logo,
                   desc = "Image Logo"
               )
           }
            //NavigationItems()
            content()
        }
    }
}