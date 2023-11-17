package com.saitawngpha.blogmultiplatform.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.saitawngpha.blogmultiplatform.components.CategoryNavigationItems
import com.saitawngpha.blogmultiplatform.components.SearchBar
import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.navigation.Screen
import com.saitawngpha.blogmultiplatform.styles.CategoryItemStyle
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.saitawngpha.blogmultiplatform.util.Constants.HEADER_HEIGHT
import com.saitawngpha.blogmultiplatform.util.Constants.PAGE_WIDTH
import com.saitawngpha.blogmultiplatform.util.Id
import com.saitawngpha.blogmultiplatform.util.Res
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import kotlinx.browser.document
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLInputElement

/**
 * Created by တွင်ႉၾႃႉ on 11/11/2023.
 */

@Composable
fun HeaderSection(
    breakpoint: Breakpoint,
    logo : String = Res.Image.logoHome,
    selectedCategory: Category? = null,
    onMenuOpen: () -> Unit
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Theme.Secondary.rgb),
        contentAlignment = Alignment.Center
    ){
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .backgroundColor(Theme.Secondary.rgb)
                .maxWidth(PAGE_WIDTH.px),
            contentAlignment = Alignment.TopCenter
        ){
            Headers(
                breakpoint = breakpoint,
                logo = logo,
                selectedCategory = selectedCategory,
                onMenuOpen = onMenuOpen
            )
        }
    }
}

@Composable
fun Headers(
    breakpoint: Breakpoint,
    logo : String ,
    selectedCategory: Category?,
    onMenuOpen: () -> Unit
) {
    val context = rememberPageContext()
    var fullSearchBarOpened by remember { mutableStateOf(false) }
    Row (
        modifier = Modifier
            .fillMaxWidth(
            if(breakpoint > Breakpoint.MD) 80.percent else 90.percent
        )
            .height(HEADER_HEIGHT.px),
        verticalAlignment = Alignment.CenterVertically
    ){
        if (breakpoint <= Breakpoint.MD){
            if(fullSearchBarOpened){
                FaXmark(
                    modifier = Modifier
                        .margin(right = 24.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick { fullSearchBarOpened = false },
                    size = IconSize.XL
                )
            }
            if(!fullSearchBarOpened){
                FaBars(
                    modifier = Modifier
                        .margin(right = 24.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick {
                            onMenuOpen()
                        },
                    size = IconSize.XL
                )
            }

        }
        if (!fullSearchBarOpened) {
            Image(
                modifier = Modifier
                    .margin(right = 50.px)
                    .width(if(breakpoint >= Breakpoint.SM) 120.px else 80.px)
                    .cursor(Cursor.Pointer)
                    .onClick {
                             context.router.navigateTo(Screen.HomePage.route)
                    },
                src = logo,
                desc = "Logo Home Image"
            )
        }

        //category
        if(breakpoint > Breakpoint.LG){
            CategoryNavigationItems(selectedCategory = selectedCategory)
        }

        Spacer()

        //search bar
        SearchBar(
            breakpoint = breakpoint,
            fullWidth = fullSearchBarOpened,
            darkTheme = true,
            onEnterClick = {
                val query = (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value
                context.router.navigateTo(Screen.SearchPage.searchByTitle(query = query))
            },
            onSearchIconClick = {fullSearchBarOpened = it}
        )
    }
}